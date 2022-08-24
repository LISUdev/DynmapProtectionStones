# DynmapProtectionStones
Simple Minecraft plugin to show ProtectionStones cuboids in dynmap.

### Depend plugins:
- [ProtectionStones](https://github.com/espidev/ProtectionStones) v2.10.3 or later
- [dynmap](https://github.com/webbukkit/dynmap) v3.2.1 or later

### Example cuboid view in dynmap using this plugin:
![Example image](https://i.imgur.com/PaR8WYu.png)

### Download:
[Release page](https://github.com/LISUdev/DynmapProtectionStones/releases) or [Spigot](https://www.spigotmc.org/resources/dynmapprotectionstones.104807/)

### Contact:
If you have any problems or questions, contact with me using Discord - **LISU#0001**.

### Config file:

```yml
blacklist:
    blocks: # cuboid blocks in Protection Stones plugin to ignoring by this plugin
        - BEDROCK
        - OBSIDIAN
    worlds: # minecraft worlds to ignoring by this plugin
        - world1
        - world2

colors:
    lineColor: "#00cc00"
    lineOpacity: 0.9
    fillColor: "#00cc00"
    fillOpacity: 0.15

update: # in seconds
    mainUpdate: 5
    additionalUpdate: 15 # for merge etc.

messages:
    cuboids: "Cuboids"
    cuboidDescription: "<b><u>Cuboid</u></b><br><br> <b>Owners:</b><br>%owners_list%<br><br> <b>Members:</b><br>%members_list%"
```
