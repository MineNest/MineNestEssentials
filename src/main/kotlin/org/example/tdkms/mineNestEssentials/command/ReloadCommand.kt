// File: src/main/kotlin/org/example/tdkms/mineNestEssentials/command/ReloadCommand.kt
package org.example.tdkms.mineNestEssentials.command

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.CommandMap
import org.bukkit.command.PluginCommand
import org.bukkit.plugin.Plugin
import org.example.tdkms.mineNestEssentials.MineNestEssentials
import org.example.tdkms.mineNestEssentials.util.ConfigService
import java.lang.reflect.Constructor
import java.lang.reflect.Field

class ReloadCommand(private val plugin: MineNestEssentials) : CommandExecutor {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.size == 1 && args[0].equals("reload", ignoreCase = true)) {
            if (!sender.hasPermission(PERMISSION_NODE)) {
                sender.sendMessage("${ChatColor.RED}You don't have permission.")
                return true
            }
            plugin.reloadConfig()
            ConfigService.reload()
            sender.sendMessage("${ChatColor.GREEN}MineNestEssentials config reloaded!")
            return true
        }
        sender.sendMessage("${ChatColor.RED}Usage: /$COMMAND_ALIAS reload")
        return true
    }

    companion object {
        private const val COMMAND_ALIAS = "mne"
        private const val PERMISSION_NODE = "MineNestEssentials.reload"

        fun register(plugin: MineNestEssentials) {
            try {
                val field: Field = plugin.server.javaClass.getDeclaredField("commandMap")
                    .apply { isAccessible = true }
                val commandMap = field.get(plugin.server) as CommandMap

                val constructor: Constructor<PluginCommand> =
                    PluginCommand::class.java
                        .getDeclaredConstructor(String::class.java, Plugin::class.java)
                        .apply { isAccessible = true }
                val cmd = constructor.newInstance(COMMAND_ALIAS, plugin).apply {
                    usage = "/$COMMAND_ALIAS reload"
                    description = "Reloads MineNestEssentials config"
                    permission = PERMISSION_NODE
                    permissionMessage = "${ChatColor.RED}You don't have permission to do that."
                    setExecutor(ReloadCommand(plugin))
                }

                commandMap.register(plugin.description.name.lowercase(), cmd)
                plugin.logger.info("Registered command '/$COMMAND_ALIAS'")
            } catch (ex: Exception) {
                plugin.logger.severe("Failed to register '/$COMMAND_ALIAS': ${ex.message}")
                ex.printStackTrace()
            }
        }
    }
}
