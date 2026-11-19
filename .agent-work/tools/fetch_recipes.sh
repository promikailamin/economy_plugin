#!/usr/bin/env bash
# Fetch vanilla 1.21.4 recipe/tag JSON used by the price validation tools.
set -euo pipefail
cd "$(dirname "$0")/.."
mkdir -p tmp
url='https://codeload.github.com/misode/mcmeta/tar.gz/refs/tags/1.21.4-data-json'
archive='tmp/mcmeta-1.21.4-data-json.tar.gz'
if [[ ! -f "$archive" ]]; then
    curl -fL -o "$archive" "$url"
fi
rm -rf tmp/mcmeta
mkdir -p tmp/mcmeta
tar -xzf "$archive" -C tmp/mcmeta --strip-components=1
echo "Recipes available in .agent-work/tmp/mcmeta/data/minecraft/recipe"
