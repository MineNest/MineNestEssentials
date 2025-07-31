package org.example.tdkms.mineNestEssentials.listener

import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.ItemFrame
import org.bukkit.entity.Painting
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.*
import org.bukkit.event.player.*
import org.example.tdkms.mineNestEssentials.util.ConfigService.isProtected
import org.example.tdkms.mineNestEssentials.util.ConfigService.mm
import org.example.tdkms.mineNestEssentials.util.ConfigService.settings

class PlayerEventsListener : Listener {

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        val world = player.world

        if (!isProtected(world.name) || (player.isOp && settings.enableOpBypass)) return
        if (event.item?.type == Material.FISHING_ROD) return
        val block = event.clickedBlock ?: return
        if (block.type == Material.CHEST) return

        event.isCancelled = true
    }

    @EventHandler
    fun onItemFrameInteraction(event: PlayerInteractEntityEvent) {
        val player = event.player
        val world = player.world

        if (!isProtected(world.name) || (player.isOp && settings.enableOpBypass)) return

        val entity = event.rightClicked
        if (entity is ItemFrame || entity is Painting || entity is ArmorStand) {
            event.isCancelled = true
        }
    }

    @Suppress("DEPRECATION")
    @EventHandler
    fun onPickup(event: PlayerPickupItemEvent) {
        val player = event.player
        val world = player.world

        if (!isProtected(world.name) || (player.isOp && settings.enableOpBypass)) return
        if (settings.cancelPickup) event.isCancelled = true
    }

    @EventHandler
    fun onDrop(event: PlayerDropItemEvent) {
        val player = event.player
        val world = player.world

        if (!isProtected(world.name) || (player.isOp && settings.enableOpBypass)) return
        if (settings.cancelDrop) event.isCancelled = true
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        val world = player.world

        if (settings.tpSpawnOnJoin && isProtected(world.name)) {
            player.teleport(player.world.spawnLocation.clone().apply {
                pitch = settings.spawnPitch
                yaw = settings.spawnYaw
            })
        }

        if (settings.showJoinMessage) {
            val raw = settings.joinMessage.replace("{PLAYER}", player.name)
            val comp = mm.deserialize(raw)
            event.joinMessage(comp)
        }
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val player = event.player

        if (settings.showQuitMessage) {
            val raw = settings.quitMessage.replace("{PLAYER}", player.name)
            val comp = mm.deserialize(raw)
            event.quitMessage(comp)
        }
    }
}
