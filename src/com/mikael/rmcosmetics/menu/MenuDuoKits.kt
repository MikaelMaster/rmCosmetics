package com.mikael.rmcosmetics.menu

import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.game.SoundEffect
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenNoEffect
import org.bukkit.Material
import org.bukkit.Sound

class MenuDuoKits : Menu("Kits (Modo Dupla)", 6) {

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
                ItemBuilder(Material.PAPER)
                    .name("§aInformações")
                    .lore(
                        "§8Kits (Modo Dupla)",
                        "",
                        "§7Você pode encontrar novos kits",
                        "§7em §bCaixas Misteriosas Sky Wars",
                        "§7ou comprá-los utilizando §6Gold§7,",
                        "§bCash §7e §eCoins§7.",
                        "",
                        "§fDesbloqueados: §c0/0 §8(0%)",
                        "§fPré-selecionado atualmente:",
                        "§a▸ Nenhum"
                    )
            }
            click = ClickEffect {
                it.player.soundWhenNoEffect()
            }
        }

        backPage.item = ItemBuilder(Material.INK_SACK).data(1)
            .name("§cVoltar")
            .lore("§7Para Loja - Sky Wars.")
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