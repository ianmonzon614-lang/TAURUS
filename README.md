# Base Finder Client — source core 1.1.0

Target configuration: Minecraft 26.2 / Fabric / Java 25.

## Included detection catalog

### Chunk Finder
Counts registered base-related block signals per chunk, based only on loaded chunks
and block data provided by the client adapter:
- Storage: chests, trapped chests, barrels, hoppers, furnaces, smokers,
  blast furnaces, ender chests and all 16 colored shulker boxes.
- Redstone: redstone dust/wire, observers, pistons, sticky pistons,
  redstone blocks/torches, repeaters, comparators, levers, targets,
  dispensers, droppers, daylight detectors, tripwire hooks/wire and sculk sensors.
- Automation: crafters and the relevant redstone automation blocks above.

### Stash Finder
Tracks storage, automation and redstone signals, and additionally tags these
as contextual concealment clues:
- Tinted glass
- Amethyst block
- Budding amethyst
- Amethyst cluster and small/medium/large buds

Tinted glass and amethyst are clues only; they do not prove that a stash or base
is present. Results include dimension, coordinates, registry ID and category.

## Important status

This is an updated source/core release. It is **not a compiled .jar** and is not
yet a complete in-game mod: the Minecraft 26.2 adapter, keybinds, HUD, config
screen and renderer still need to be connected and tested against the exact
26.2 Fabric API. It cannot scan unloaded chunks or data hidden from the client.

## Build prerequisites

Use JDK 25 and the matching Gradle/Fabric Loom/Fabric API versions for Minecraft
26.2. The Gradle files are a starting configuration and must be verified against
the official Fabric 26.2 template before producing a distributable mod.

Build after the adapter and version-specific integration are complete:
```bash
./gradlew build
```
The mod jar should then appear in `build/libs/`.


## TAURUS graphical menu (new source addition)

The mod ID and display name are now `taurus` / `TAURUS`.
A centered dark-crimson module screen is included in the client source.
Press **Right Shift** in a world to open/close it. Its four buttons toggle the
module states in the registry.

The screen and key mapping use the Fabric/Minecraft 26.2 naming scheme
(`KeyMappingHelper`, `KeyMapping`, `Gui.setScreen`, `Screen.extractRenderState`,
`GuiGraphicsExtractor`). This source has not been compiled in this environment:
the available JDK is 21, while the configured target requires JDK 25, and Gradle
is not installed here. Treat it as an integration source update pending a real
26.2 Gradle build and runtime test.
