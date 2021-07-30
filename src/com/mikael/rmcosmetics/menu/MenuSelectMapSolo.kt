package com.mikael.rmcosmetics.menu

import com.kirelcodes.miniaturepets.loader.PetLoader
import com.kirelcodes.miniaturepets.pets.PetManager
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.game.SoundEffect
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenNoEffect
import net.eduard.redemikael.core.user
import org.bukkit.Material
import org.bukkit.Sound

class MenuSelectMapSolo : Menu("Mapas - Sky Wars Solo", 6) {

    init {

        isAutoAlignItems = true
        autoAlignSkipLines = listOf(1, 5, 6)
        autoAlignSkipColumns = listOf(9, 1)
        autoAlignPerLine = 7
        autoAlignPerPage = 3 * autoAlignPerLine

        // for aqui


        button("info") {
            setPosition(6, 6)

            fixed = true
            iconPerPlayer = {
                val user = player.user
                val item = ItemBuilder(Material.PAPER)
                item.name("§aInformações")

                if (player.hasPermission("rmcore.benefits.vip")) {
                    item.lore(
                        "§fLimite diário se seleções: §70/3",
                        "",
                        "§fEvolua para §6MVP §fe livre-se do",
                        "§flimite diário de seleções!",
                        "§fBasta utilizar §a/upgrades§f.",
                        "",
                        "§eClique para abrir o menu evoluções!"
                    )
                } else if (player.hasPermission("rmcore.benefits.mvp") || player.hasPermission("rmcore.benefits.mvpplus")) {
                    item.lore(
                        "§fVocê é ${user.tag.name} §fe não possui",
                        "§flimite diário de seleções."
                    )
                } else {
                    item.lore(
                        "§fLimite diário se seleções: §70/1",
                        "",
                        "§fAdquira seu §6MVP §fe livre-se do",
                        "§flimite diário de seleções!",
                        "§fBasta acessar nossa loja:",
                        "§awww.redemift.com/loja",
                        "",
                        "§eClique para acessar nossa loja!"
                    )
                }
            }
            click = ClickEffect {
                val player = it.player

                if (player.hasPermission("rmcore.benefits.vip")) {
                    player.chat("/upgrades")
                } else if (player.hasPermission("rmcore.benefits.mvp") || player.hasPermission("rmcore.benefits.mvpplus")) {
                    player.soundWhenNoEffect()
                } else {
                    player.chat("/loja")
                }
            }
        }

        backPage.item = ItemBuilder(Material.INK_SACK).data(1)
            .name("§cVoltar")
            .lore("§7Para Sky Wars Solo.")
        backPage.setPosition(5, 6)
        backPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

        nextPage.item = ItemBuilder(Material.INK_SACK).data(10)
            .name("§aPágina %page")
            .lore("§7Ir até a página %page.")
        nextPage.setPosition(9, 6)
        nextPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

        previousPage.item = ItemBuilder(Material.INK_SACK).data(8)
            .name("§aPágina %page")
            .lore("§7Ir até a página %page.")
        previousPage.setPosition(1, 6)
        previousPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

    }
}