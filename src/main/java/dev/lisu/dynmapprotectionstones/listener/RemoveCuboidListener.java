package dev.lisu.dynmapprotectionstones.listener;

import dev.espi.protectionstones.event.PSRemoveEvent;
import dev.lisu.dynmapprotectionstones.DynmapProtectionStones;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RemoveCuboidListener implements Listener {
    @EventHandler
    public void onCuboidRemove(PSRemoveEvent e) {
        DynmapProtectionStones.plugin.getCuboidsService().removeCuboidFromCache(e.getRegion().getId());
    }
}
