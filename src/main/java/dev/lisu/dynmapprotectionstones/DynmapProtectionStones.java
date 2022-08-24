package dev.lisu.dynmapprotectionstones;

import dev.lisu.dynmapprotectionstones.listener.CreateCuboidListener;
import dev.lisu.dynmapprotectionstones.listener.RemoveCuboidListener;
import dev.lisu.dynmapprotectionstones.service.AreaMarkerService;
import dev.lisu.dynmapprotectionstones.service.CuboidsService;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;

public final class DynmapProtectionStones extends JavaPlugin {
    public static DynmapProtectionStones plugin;

    private DynmapAPI dynmap;
    private AreaMarkerService areaMarkerService;
    private CuboidsService cuboidsService;

    @Override
    public void onEnable() {
        plugin = this;
        plugin.saveDefaultConfig();

        dynmap = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("dynmap");
        if (dynmap == null) {
            Bukkit.getLogger().warning("Dynmap plugin not found!");
            Bukkit.getServer().getPluginManager().disablePlugin(plugin);
        }

        if (Bukkit.getServer().getPluginManager().getPlugin("ProtectionStones") == null) {
            Bukkit.getLogger().warning("ProtectionStones plugin not found!");
            Bukkit.getServer().getPluginManager().disablePlugin(plugin);
        }

        new Metrics(this, 16252);

        areaMarkerService = new AreaMarkerService(plugin);
        cuboidsService = new CuboidsService(plugin);

        getServer().getPluginManager().registerEvents(new CreateCuboidListener(), this);
        getServer().getPluginManager().registerEvents(new RemoveCuboidListener(), this);
    }

    public DynmapAPI getDynmap() {
        return dynmap;
    }

    public AreaMarkerService getAreaMarkerService() {
        return areaMarkerService;
    }

    public CuboidsService getCuboidsService() {
        return cuboidsService;
    }
}
