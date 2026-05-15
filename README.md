<<<<<<< HEAD
# BetterTNTMinecart

A client-side Fabric mod for Minecraft 1.21+ that gives you full visual control over TNT minecarts.

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
| Minecraft | 1.21.x |
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

## Building from source

```bash
git clone https://github.com/solanaflipper/bettertntminecart.git
cd bettertntminecart
./gradlew build
```

The output jar will be in `build/libs/`.

## Compatibility

Client-side only. Works on any server without server-side installation.

>>>>>>> dc2cec554f8f31423ebefbec514ef6c3041e7eba
