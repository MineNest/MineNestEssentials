package org.example.tdkms.mineNestEssentials

import org.bukkit.configuration.file.FileConfiguration

/**
 * Reads and holds plugin configuration values.
 */
class Settings(config: FileConfiguration) {
    /** Worlds where restrictions apply */
    val protectedWorlds: List<String> = config.getStringList("protected-worlds")

    val cancelBlockBreak: Boolean  = config.getBoolean("breaking-blocks", true)
    val cancelBlockPlace: Boolean  = config.getBoolean("placing-blocks", true)
    val cancelPickup: Boolean      = config.getBoolean("item-pickup", true)
    val cancelDrop: Boolean        = config.getBoolean("item-drop", true)
    val canOpInteract: Boolean        = config.getBoolean("op-interaction", true)
    val showJoinMessage: Boolean = config.getBoolean("join-msg", true)
    val showQuitMessage: Boolean = config.getBoolean("quit-msg", true)
    val tpSpawnOnJoin: Boolean     = config.getBoolean("tp-spawn-on-join", true)

    /** Custom join message format */
    val joinMessage: String = config.getString("messages.join", "§a[+] {PLAYER}")!!
    val quitMessage: String = config.getString("messages.quit", "§a[-] {PLAYER}")!!
}