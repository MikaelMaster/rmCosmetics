package com.mikael.rmcosmetics.commands

import net.eduard.redemikael.core.api.MiftCommand
import net.eduard.redemikael.core.api.MiftGroup
import org.bukkit.command.CommandSender

class MiftCosmeticsCommand : MiftCommand("rmcosmetics", "rmcs") {

    init {
        group = MiftGroup.MEMBRO
        register(RemoveCosmeticsCommand())
        register(GetBannerDataCommand())
        register(SyncAllCosmeticsCommand())
    }

    override fun command(sender: CommandSender, args: Array<String>) {
        sender.sendMessage("§armCosmetics §ev1.5 §f- §bby Mikael.")
        sender.sendMessage("§6Website: §ehttps://www.redemift.com")
    }
}