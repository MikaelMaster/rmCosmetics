package com.mikael.rmcosmetics.commands

import com.mikael.rmcosmetics.core.CosmeticsUtils
import com.mikael.rmcosmetics.menu.MenuCosmetics
import net.eduard.redemikael.core.api.MiftCommand
import net.eduard.redemikael.core.api.MiftGroup
import net.eduard.redemikael.core.soundWhenNoSuccess
import org.bukkit.entity.Player

class CosmeticsCommand : MiftCommand("cosmeticos", "cosmetics") {

    init {
        usage = "/cosmeticos"
        group = MiftGroup.MEMBRO
    }

    override fun playerCommand(player: Player, args: Array<String>) {
        if (CosmeticsUtils.loadingCosmetics.contains(player)) {
            player.soundWhenNoSuccess()
            player.sendMessage("§cSeus cosméticos ainda estão sendo carregados...")
            return
        }
        MenuCosmetics.instance.open(player)
    }

}