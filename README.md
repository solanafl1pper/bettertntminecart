# BetterTNTMinecart

A client-side Fabric mod for Minecraft (1.21.x – 26.3) that gives you full visual control over TNT minecarts.

## Supported Versions

| Minecraft Version | Branch | Java Version | Toolchain |
|---|---|---|---|
| **26.3** (26.1 – 26.3) | `main` | Java 25 | Loom 1.19.2 (Unobfuscated) |
| **1.21.x** (1.21 – 1.21.11) | `1.21.x` (`master`) | Java 21 | Loom 1.16/1.17 (Remapped) |

## Features

**Colored Hitbox**

Draws a colored outline box around every TNT minecart in your view. Always visible without needing F3+B. You can pick any color, adjust opacity, change line thickness, and enable Rainbow RGB mode that smoothly cycles through the full color spectrum.

**Custom Label**

Each TNT minecart displays a floating name tag above it. The text is fully customizable. Supports MiniMessage gradient format from [Birdflop](https://rgb.birdflop.com) for multi-color gradient text. Just design your gradient on the site, select MiniMessage as output format, and paste the result into the Label Text field.

**In-Game Config**

All settings are available through a Cloth Config screen. Open it via the ModMenu gear icon or assign a keybind in Options > Controls > Miscellaneous.

## Requirements

| Dependency | Version |
|---|---|
| Minecraft | 1.21.x – 26.3 |
| Fabric Loader | 0.19+ |
| Fabric API | any |
| Cloth Config | any |
| ModMenu | optional |

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/)
2. Download [Fabric API](https://modrinth.com/mod/fabric-api)
3. Download [Cloth Config](https://modrinth.com/mod/cloth-config)
4. Download [ModMenu](https://modrinth.com/mod/modmenu) (optional)
5. Drop all jars into your `.minecraft/mods` folder

## Compatibility

Client-side only. Works on any server without server-side installation.
