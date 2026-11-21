#!/usr/bin/env bash
# Build the plugin and run the SellAxeHarness integration test on a temporary Paper server.
# Usage: .agent-work/tools/test-sellaxe.sh [paper-version] [paper-build]
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
VERSION="${1:-1.21.4}"
BUILD="${2:-232}"
WORK="$ROOT/.agent-work/tmp/paper-test"
HARNESS="$ROOT/.agent-work/tests/sellaxe-harness"
OUT="$ROOT/.agent-work/outputs"
LOG="$ROOT/.agent-work/logs/issue11-paper-test.log"

SERVER_JAR="$WORK/paper-$VERSION-$BUILD.jar"
if [[ ! -f "$SERVER_JAR" ]]; then
    mkdir -p "$WORK"
    META="$(curl -fsSL --max-time 60 "https://fill.papermc.io/v3/projects/paper/versions/$VERSION/builds/latest")"
    URL="$(python3 -c 'import json,sys; print(json.load(sys.stdin)["downloads"]["server:default"]["url"])' <<<"$META")"
    echo "Downloading $URL"
    curl -fsSL --retry 3 --max-time 300 "$URL" -o "$SERVER_JAR"
fi

cd "$ROOT"
./gradlew --no-daemon build >/dev/null

rm -rf "$WORK/sellaxe-server" "$WORK/sellaxe-out"
mkdir -p "$WORK/sellaxe-server/plugins" "$WORK/sellaxe-out"
cp build/libs/Economy-*.jar "$WORK/sellaxe-server/plugins/"
printf 'eula=true\n' > "$WORK/sellaxe-server/eula.txt"
cat > "$WORK/sellaxe-server/server.properties" <<'PROP'
online-mode=false
level-type=flat
spawn-protection=0
max-players=1
view-distance=2
simulation-distance=2
PROP

javac --release 21 -cp "build/classes/java/main:libs/*" -d "$WORK/sellaxe-out" \
    "$HARNESS/sellaxeharness/SellAxeHarness.java"
cp "$HARNESS/plugin.yml" "$WORK/sellaxe-out/"
jar cf "$WORK/sellaxe-server/plugins/SellAxeHarness.jar" -C "$WORK/sellaxe-out" .

set +e
(cd "$WORK/sellaxe-server" && timeout 180 java -Xms512M -Xmx2G -jar "$SERVER_JAR" --nogui) 2>&1 | tee "$LOG"
STATUS=${PIPESTATUS[0]}
set -e

REPORT="$WORK/sellaxe-server/plugins/SellAxeHarness/report.txt"
mkdir -p "$OUT"
if [[ -f "$REPORT" ]]; then
    cp "$REPORT" "$OUT/issue11-sellaxe-report.txt"
    cat "$REPORT"
else
    echo "Harness report missing; server exited with $STATUS" >&2
    exit 1
fi
if grep -q '^failures=0$' "$REPORT"; then
    echo "SellAxeHarness: PASS"
else
    echo "SellAxeHarness: FAIL" >&2
    exit 1
fi
