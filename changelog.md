# Changelog

## Fixes

- Fixed seated players sitting a quarter block too high on 1.18.2, 1.19.2 and 1.20.1. The sit position now matches the newer versions.
- Fixed z-fighting on cushions placed inside or against a block when viewed from a distance.

## Additions

- Added support for Minecraft 1.21.11 on Fabric and NeoForge.
- Added a config file at config/cushionbackport.json with preventCushionToCushionMovement. Set it to true to require getting up before sitting on another cushion. Defaults to false.

## Compatibility

- Declared the Fabric API dependency in the mod metadata on every version, so the loader warns you instead of crashing when it is missing. 1.18.2 depends on "fabric", 1.19.2 and up depend on "fabric-api".
