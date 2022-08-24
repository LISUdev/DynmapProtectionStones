package dev.lisu.dynmapprotectionstones.service;

import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import dev.espi.protectionstones.PSRegion;
import dev.espi.protectionstones.utils.WGUtils;
import dev.lisu.dynmapprotectionstones.DynmapProtectionStones;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

public class CuboidsService {
    private final DynmapProtectionStones plugin;
    private final HashMap<String, PSRegion> cuboidsHashMap = new HashMap<>();

    public CuboidsService(DynmapProtectionStones plugin) {
        this.plugin = plugin;

        Bukkit.getScheduler().runTask(plugin, this::init);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::updateAreaMarkers, 0, plugin.getConfig().getInt("update.mainUpdate") * 20L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::init, 0, plugin.getConfig().getInt("update.additionalUpdate") * 20L);
    }

    private void init() {
        cuboidsHashMap.clear();
        for(Map.Entry<World, RegionManager> regionManager : WGUtils.getAllRegionManagers().entrySet()) {
            for(Map.Entry<String, ProtectedRegion> reg : regionManager.getValue().getRegions().entrySet()) {
                PSRegion psRegion = PSRegion.fromWGRegion(regionManager.getKey(), reg.getValue());
                if(psRegion != null) {
                    addCuboidToCache(psRegion);
                }
            }
        }
    }

    private void updateAreaMarkers() {
        for(Map.Entry<String, PSRegion> psRegion : cuboidsHashMap.entrySet()) {
            plugin.getAreaMarkerService().createCuboidArea(psRegion.getValue());
        }
    }

    public void addCuboidToCache(PSRegion psRegion) {
        if(plugin.getConfig().getStringList("blacklist.worlds").contains(psRegion.getWorld().getName())) return;
        if(plugin.getConfig().getStringList("blacklist.blocks").contains(psRegion.getType())) return;
        plugin.getAreaMarkerService().createCuboidArea(psRegion);
        cuboidsHashMap.put(psRegion.getId(), psRegion);
    }

    public void removeCuboidFromCache(String psRegionId) {
        plugin.getAreaMarkerService().deleteCuboidArea(psRegionId);
        cuboidsHashMap.remove(psRegionId);
    }
}
