package dev.lisu.dynmapprotectionstones.listener;

import dev.espi.protectionstones.event.PSCreateEvent;
import dev.lisu.dynmapprotectionstones.DynmapProtectionStones;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CreateCuboidListener implements Listener {
    @EventHandler
    public void onCuboidCreate(PSCreateEvent e) {
        DynmapProtectionStones.plugin.getCuboidsService().addCuboidToCache(e.getRegion());
    }
}
