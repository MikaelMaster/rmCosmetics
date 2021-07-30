package com.mikael.rmcosmetics.menu

import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.game.SoundEffect
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenNoEffect
import org.bukkit.Material
import org.bukkit.Sound

class MenuCelebrations : Menu("Comemorações de Vitória", 5) {

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
                        "§8Comemorações de Vitória",
                        "",
                        "§7Você pode encontrar novas comemorações",
                        "§7em §bCaixas Misteriosas §7ou comprá-las",
                        "§7utilizando §6Gold §7e §bCash§7.",
                        "",
                        "§fDesbloqueadas: §c0/0 §8(0%)",
                        "§fSelecionada atualmente:",
                        "§a▸ Nenhuma"
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

    }
}