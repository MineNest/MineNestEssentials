package org.example.tdkms.mineNestEssentials

import org.bukkit.GameRule
import org.bukkit.plugin.java.JavaPlugin

/**
 * Main plugin class. Initializes config and registers event listeners.
 */
class MineNestEssentials : JavaPlugin() {
    private lateinit var settings: Settings

    override fun onEnable() {
        saveDefaultConfig()
        settings = Settings(config)

        server.pluginManager.registerEvents(ProtectedWorldsListener(settings), this)

        settings.protectedWorlds.forEach { worldName ->
            server.getWorld(worldName)?.apply {
                setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
                // (noon)
                time = 6000L
            }
        }

        logger.info("MineNestEssentials enabled. Protected worlds: ${settings.protectedWorlds}")
    }
}