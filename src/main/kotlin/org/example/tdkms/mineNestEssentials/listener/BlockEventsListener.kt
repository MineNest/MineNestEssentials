package org.example.tdkms.mineNestEssentials.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.*
import org.example.tdkms.mineNestEssentials.util.ConfigService.isProtected
import org.example.tdkms.mineNestEssentials.util.ConfigService.settings

class BlockEventsListener : Listener {

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        val world = player.world.name

        if (!isProtected(world) || player.isOp && settings.enableOpBypass) return
        if (settings.cancelBlockBreak) event.isCancelled = true
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        val world = player.world.name

        if (!isProtected(world) || player.isOp && settings.enableOpBypass) return
        if (settings.cancelBlockPlace) event.isCancelled = true
    }

    @EventHandler
    fun onBlockIgnite(event: BlockIgniteEvent) {
        val block = event.block
        val world = block.world.name

        if (isProtected(world)) event.isCancelled = true

    }

    @EventHandler
    fun onBlockBurn(event: BlockBurnEvent) {
        val block = event.block
        val world = block.world.name

        if (isProtected(world)) event.isCancelled = true

    }

    @EventHandler
    fun onBlockSpread(event: BlockSpreadEvent) {
        val block = event.block
        val world = block.world.name

        if (isProtected(world)) event.isCancelled = true

    }

    @EventHandler
    fun onLiquidFlow(event: BlockFromToEvent) {
        val block = event.block
        val world = block.world.name

        if (isProtected(world)) event.isCancelled = true

    }
}
