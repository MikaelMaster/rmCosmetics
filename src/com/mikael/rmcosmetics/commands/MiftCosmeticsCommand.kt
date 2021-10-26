package com.mikael.rmcosmetics.commands

import net.eduard.api.lib.manager.CommandManager
import org.bukkit.command.CommandSender

class MiftCosmeticsCommand : CommandManager("rmcosmetics") {

    init {
        permission = "rmcosmetics.defaultperm"
        register(RemoveCosmeticsCommand())
        register(GetBannerDataCommand())
        register(SyncAllCosmeticsCommand())
    }

    override fun command(sender: CommandSender, args: Array<String>) {
        sender.sendMessage("§armCosmetics §ev1.5 §f- §bby Mikael.")
        sender.sendMessage("§6Website: §ehttps://www.redemift.com")
    }
}