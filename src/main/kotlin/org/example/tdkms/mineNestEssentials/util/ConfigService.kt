package org.example.tdkms.mineNestEssentials.util

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.java.JavaPlugin
import org.example.tdkms.mineNestEssentials.Settings

object ConfigService {
    val mm: MiniMessage = MiniMessage.miniMessage()

    private lateinit var plugin: JavaPlugin

    lateinit var settings: Settings
        private set

    fun init(plugin: JavaPlugin) {
        this.plugin = plugin
        plugin.saveDefaultConfig()
        reload()
    }

    fun reload() {
        plugin.reloadConfig()
        settings = Settings(plugin.config)
    }

    fun isProtected(world: String) = world in settings.protectedWorlds
}
