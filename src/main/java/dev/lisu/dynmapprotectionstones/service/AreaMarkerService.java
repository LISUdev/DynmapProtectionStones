package dev.lisu.dynmapprotectionstones.service;

import dev.espi.protectionstones.PSRegion;
import dev.lisu.dynmapprotectionstones.DynmapProtectionStones;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerSet;

import java.util.StringJoiner;
import java.util.UUID;

public class AreaMarkerService {
    private final DynmapProtectionStones plugin;
    private MarkerSet markerSet;

    public AreaMarkerService(DynmapProtectionStones plugin) {
        this.plugin = plugin;

        init();
    }

    private void init() {
        MarkerSet marker = plugin.getDynmap().getMarkerAPI().getMarkerSet("cuboids");
        if(marker != null) {
            marker.deleteMarkerSet();
        }
        marker = plugin.getDynmap().getMarkerAPI().createMarkerSet("cuboids", plugin.getConfig().getString("messages.cuboids"), null, false);
        this.markerSet = marker;
    }

    public void createCuboidArea(PSRegion psRegion) {
        AreaMarker am = markerSet.findAreaMarker("cuboids_" + psRegion.getId());

        if(am == null) am = markerSet.createAreaMarker("cuboids_" + psRegion.getId(), psRegion.getId(), false, psRegion.getWorld().getName(), new double[0], new double[0], false);

        for (int i = 0; i < am.getCornerCount(); i++) {
            am.deleteCorner(i);
        }

        for (int i = 0; i < psRegion.getPoints().size(); i++) {
            am.setCornerLocation(i, psRegion.getPoints().get(i).getX(), psRegion.getPoints().get(i).getZ());
        }

        StringJoiner owners = new StringJoiner(", ");
        for(UUID memberUUID : psRegion.getOwners()) {
            var offlinePlayer = plugin.getServer().getOfflinePlayer(memberUUID);
            owners.add(offlinePlayer.getName());
        }

        StringJoiner members = new StringJoiner(", ");
        for(UUID memberUUID : psRegion.getMembers()) {
            var offlinePlayer = plugin.getServer().getOfflinePlayer(memberUUID);
            members.add(offlinePlayer.getName());
        }

        int fillColor = 0xFF0000;
        int lineColor = 0xFF0000;
        try {
            fillColor = Integer.parseInt(plugin.getConfig().getString("colors.fillColor").substring(1), 16);
            lineColor = Integer.parseInt(plugin.getConfig().getString("colors.fillColor").substring(1), 16);
        } catch (NumberFormatException nfx) {
            plugin.getServer().getLogger().warning("Invalid color in config!");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }

        am.setFillStyle(plugin.getConfig().getDouble("colors.fillOpacity"), fillColor);
        am.setLineStyle(1, plugin.getConfig().getDouble("colors.lineOpacity"), lineColor);
        am.setDescription(plugin.getConfig().getString("messages.cuboidDescription").replace("%owners_list%", owners.toString()).replace("%members_list%", members.toString()));
    }

    public void deleteCuboidArea(String psRegionId) {
        AreaMarker am = markerSet.findAreaMarker("cuboids_" + psRegionId);
        if(am != null) {
            am.deleteMarker();
        }
    }
}
