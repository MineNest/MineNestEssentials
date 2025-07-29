package org.example.tdkms.mineNestEssentials

import org.bukkit.Material
import org.bukkit.block.data.type.Chest
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.*
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.event.inventory.InventoryType


/**
 * Listener that enforces restrictions based on Settings for protected worlds.
 */
class ProtectedWorldsListener(private val settings: Settings) : Listener {

    private fun isProtected(player: Player) = settings.protectedWorlds.contains(player.world.name)
    private val mm: MiniMessage = MiniMessage.miniMessage()

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (settings.cancelBlockBreak && isProtected(event.player)) event.isCancelled = true
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        if (settings.cancelBlockPlace && isProtected(event.player)) event.isCancelled = true
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player

        if (!isProtected(player) || player.isOp && settings.canOpInteract) return

        val block = event.clickedBlock ?: return

        // allow opening chests only
        if (block.type == Material.CHEST) {
            return
        }

        event.isCancelled = true
    }

    @EventHandler
    fun onInventoryOpen(e: InventoryOpenEvent) {
        val player = e.player as? Player ?: return

        if (!isProtected(player)) return

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



    @Suppress("DEPRECATION")
    @EventHandler
    fun onPickup(event: PlayerPickupItemEvent) {
        if (settings.cancelPickup && isProtected(event.player)) event.isCancelled = true
    }

    @EventHandler
    fun onDrop(event: PlayerDropItemEvent) {
        if (settings.cancelDrop && isProtected(event.player)) event.isCancelled = true
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        val entity = event.entity
        if (entity is Player && isProtected(entity)) {
            if (event.cause == EntityDamageEvent.DamageCause.VOID) {
                event.isCancelled = true
                entity.teleport(entity.world.spawnLocation)
            } else if (event.cause != EntityDamageEvent.DamageCause.VOID) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player

        if (settings.tpSpawnOnJoin && isProtected(player)) {
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