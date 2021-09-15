package com.mikael.rmcosmetics.commands

import net.eduard.api.lib.manager.CommandManager
import org.bukkit.command.CommandSender

class VersionCommand : CommandManager("rmcosmetics", "rmcs") {

    init {
        permission = "rmcosmetics.see.version"
        permissionMessage = "§cVocê não possui permissão para utilizar este comando!"
    }

    override fun command(sender: CommandSender, args: Array<String>) {
        sender.sendMessage("§armCosmetics §ev1.3 §f- §bby Mikael.")
        sender.sendMessage("§6Website: §ehttps://www.redemift.com")
    }
}