package com.mikael.rmcosmetics.commands

import net.eduard.redemikael.core.api.MiftCommand
import net.eduard.redemikael.core.api.MiftGroup
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.BannerMeta
import java.io.File
import java.nio.file.Files

class GetBannerDataCommand : MiftCommand("getbannerdata") {

    init {
        group = MiftGroup.GERENTE
    }

    override fun playerCommand(player: Player, args: Array<String>) {
        val text = StringBuilder()
        val item = player.itemInHand
        val bannerMeta = item.itemMeta as BannerMeta
        text.append("DyeColor.${bannerMeta.baseColor},\n")
        for (data in bannerMeta.patterns) {
            text.append("Pattern(DyeColor.${data.color}, PatternType.${data.pattern}),\n")
        }
        Files.write(File("banners.txt").toPath(), text.toString().toByteArray())
        player.sendMessage("§aFormato salvo em 'container/banners.txt'!")
    }
}