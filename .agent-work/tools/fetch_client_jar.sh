#!/usr/bin/env bash
# Download a Minecraft client jar into .agent-work/tmp/mc/client.jar.
#
# The jar is large and must never be committed. Its only purpose is to let
# generate_price_docs.py extract the 16x16 icon textures used by docs/PRICE.md.
#
# Usage:
#   .agent-work/tools/fetch_client_jar.sh [version]
#
# version defaults to 26.3 (the assets version used to generate docs/PRICE.md).
set -euo pipefail
cd "$(dirname "$0")/../.."
work=".agent-work/tmp/mc"
version="${1:-26.3}"
mkdir -p "$work"

manifest="$work/version_manifest.json"
if [[ ! -s "$manifest" ]]; then
    curl -fsSL --retry 3 -o "$manifest" \
        https://piston-meta.mojang.com/mc/game/version_manifest_v2.json
fi

meta_url="$(python3 - "$manifest" "$version" <<'PY'
import json, sys
manifest, wanted = sys.argv[1], sys.argv[2]
versions = json.load(open(manifest, encoding="utf-8"))["versions"]
for entry in versions:
    if entry["id"] == wanted:
        print(entry["url"])
        break
else:
    raise SystemExit(f"version {wanted!r} not found in Mojang manifest")
PY
)"

meta="$work/$version.json"
if [[ ! -s "$meta" ]]; then
    curl -fsSL --retry 3 -o "$meta" "$meta_url"
fi

client_url="$(python3 - "$meta" <<'PY'
import json, sys
print(json.load(open(sys.argv[1], encoding="utf-8"))["downloads"]["client"]["url"])
PY
)"

client="$work/client.jar"
version_file="$work/client.version"
if [[ -f "$client" && -f "$version_file" && "$(cat "$version_file")" == "$version" ]]; then
    echo "Using cached $client ($version)"
else
    echo "Downloading Minecraft $version client jar..."
    tmp="$client.part"
    curl -fL --retry 3 -o "$tmp" "$client_url"
    mv "$tmp" "$client"
    printf '%s\n' "$version" > "$version_file"
fi

echo "Client jar ready: $client ($(du -h "$client" | cut -f1))"
