package com.mikael.rmcosmetics.commands

import net.eduard.api.lib.manager.CommandManager
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.BannerMeta
import java.io.File
import java.nio.file.Files

class GetBannerDataCommand : CommandManager("getbannerdata") {

    init {
        permission = "rmcore.group.gerente"
        permissionMessage = "§cVocê precisa do Grupo §4Gerente §cou superior para utilizar este comando!"
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