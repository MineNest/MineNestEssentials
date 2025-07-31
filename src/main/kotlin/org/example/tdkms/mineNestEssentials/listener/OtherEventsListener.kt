package org.example.tdkms.mineNestEssentials.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.example.tdkms.mineNestEssentials.util.ConfigService.isProtected

class OtherEventsListener : Listener {

    @EventHandler
    fun onInventoryOpen(e: InventoryOpenEvent) {
        val player = e.player
        val world = player.world

        if (!isProtected(world.name)) return

        // Blocking these specific types:
        when (e.inventory.type) {
            InventoryType.ANVIL,
            InventoryType.WORKBENCH,
            InventoryType.FURNACE,
            InventoryType.BLAST_FURNACE,
            InventoryType.ENDER_CHEST,
            InventoryType.HOPPER,
            InventoryType.BREWING -> e.isCancelled = true
            else -> {}
        }
    }
}
