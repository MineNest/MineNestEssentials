package org.example.tdkms.mineNestEssentials.util

import org.bukkit.plugin.java.JavaPlugin
import org.example.tdkms.mineNestEssentials.listener.BlockEventsListener
import org.example.tdkms.mineNestEssentials.listener.EntityEventsListener
import org.example.tdkms.mineNestEssentials.listener.OtherEventsListener
import org.example.tdkms.mineNestEssentials.listener.PlayerEventsListener

fun eventsRegistrar(plugin: JavaPlugin) {
    val pm = plugin.server.pluginManager
    pm.registerEvents(BlockEventsListener(), plugin)
    pm.registerEvents(EntityEventsListener(), plugin)
    pm.registerEvents(PlayerEventsListener(), plugin)
    pm.registerEvents(OtherEventsListener(), plugin)
}
