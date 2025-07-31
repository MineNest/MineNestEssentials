package org.example.tdkms.mineNestEssentials

import org.bukkit.GameRule
import org.bukkit.plugin.java.JavaPlugin
import org.example.tdkms.mineNestEssentials.command.ReloadCommand
import org.example.tdkms.mineNestEssentials.util.ConfigService
import org.example.tdkms.mineNestEssentials.util.eventsRegistrar

/**
 * Main plugin class. Initializes config and registers event listeners.
 */
class MineNestEssentials : JavaPlugin() {
    override fun onEnable() {
        saveDefaultConfig()

        ConfigService.init(this)
        ReloadCommand.register(this)
        eventsRegistrar(this)

        ConfigService.settings.protectedWorlds.forEach { worldName ->
            server.getWorld(worldName)?.apply {
                setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
                // (noon)
                time = 6000L
            }
        }
    }
}