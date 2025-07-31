package org.example.tdkms.mineNestEssentials.listener

import org.bukkit.entity.ArmorStand
import org.bukkit.entity.ItemFrame
import org.bukkit.entity.Painting
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.hanging.HangingBreakByEntityEvent
import org.example.tdkms.mineNestEssentials.util.ConfigService.isProtected
import org.example.tdkms.mineNestEssentials.util.ConfigService.settings

class EntityEventsListener : Listener {


    @EventHandler
    fun onExplosion(event: EntityExplodeEvent) {
        val entity = event.entity
        val world = entity.world

        if (isProtected(world.name)) event.isCancelled = true
    }

    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent) {
        val entity = event.entity
        val worldName = entity.world.name

        if (!isProtected(worldName)) return

        event.isCancelled = true

        if (entity is Player && event.cause == EntityDamageEvent.DamageCause.VOID) {
            entity.teleport(entity.world.spawnLocation.clone().apply {
                pitch = settings.spawnPitch
                yaw = settings.spawnYaw
            })
        }
    }


    @EventHandler
    fun onHangingEntityBreak(event: HangingBreakByEntityEvent) {
        val entity = event.entity
        val worldName = entity.world.name

        if (entity is ItemFrame || entity is Painting || entity is ArmorStand) {
            val player = event.remover as? Player
            if (player != null && isProtected(worldName)) {
                event.isCancelled = true
            }
        }
    }
}
