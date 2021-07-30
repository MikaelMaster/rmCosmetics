package com.mikael.rmcosmetics.menu

import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.game.SoundEffect
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenNoEffect
import org.bukkit.Material
import org.bukkit.Sound

class MenuTheBridgeStats : Menu("Estatísticas - The Bridge", 5) {

    init {
        openWithCommand = "/tbstats"

        backPage.item = ItemBuilder(Material.INK_SACK).data(1)
            .name("§cVoltar")
            .lore("§7Para Meu Perfil.")
        backPage.setPosition(5, 5)
        backPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

        button("general") {
            setPosition(5, 1)

            iconPerPlayer = {
                ItemBuilder(Material.STAINED_CLAY).data(14)
                    .name("§aGeral")
                    .lore(
                        "§eTotal:",
                        " §8▪ §fVitórias: §70",
                        " §8▪ §fPartidas: §70",
                        " §8▪ §fAbates: §70",
                        " §8▪ §fMortes: §70",
                        " §8▪ §fPontos: §70",
                        "",
                        "§eEste Mês:",
                        " §8▪ §fVitórias: §70",
                        " §8▪ §fPartidas: §70",
                        " §8▪ §fAbates: §70",
                        " §8▪ §fMortes: §70",
                        " §8▪ §fPontos: §70",
                        "",
                        "§fCoins: §e0"
                    )
            }
            click = ClickEffect {
                it.player.soundWhenNoEffect()
            }
        }
        button("solo") {
            setPosition(4, 3)

            iconPerPlayer = {
                ItemBuilder(Material.STAINED_CLAY).data(11)
                    .name("§aSolo")
                    .lore(
                        "§eTotal:",
                        " §8▪ §fVitórias: §70",
                        " §8▪ §fPartidas: §70",
                        " §8▪ §fAbates: §70",
                        " §8▪ §fMortes: §70",
                        " §8▪ §fPontos: §70",
                        "",
                        "§eEste Mês:",
                        " §8▪ §fVitórias: §70",
                        " §8▪ §fPartidas: §70",
                        " §8▪ §fAbates: §70",
                        " §8▪ §fMortes: §70",
                        " §8▪ §fPontos: §70",
                    )
            }
            click = ClickEffect {
                it.player.soundWhenNoEffect()
            }
        }
        button("duo") {
            setPosition(6, 3)

            iconPerPlayer = {
                ItemBuilder(Material.STAINED_CLAY).data(11)
                    .name("§aDupla")
                    .lore(
                        "§eTotal:",
                        " §8▪ §fVitórias: §70",
                        " §8▪ §fPartidas: §70",
                        " §8▪ §fAbates: §70",
                        " §8▪ §fMortes: §70",
                        " §8▪ §fPontos: §70",
                        "",
                        "§eEste Mês:",
                        " §8▪ §fVitórias: §70",
                        " §8▪ §fPartidas: §70",
                        " §8▪ §fAbates: §70",
                        " §8▪ §fMortes: §70",
                        " §8▪ §fPontos: §70",
                    )
            }
            click = ClickEffect {
                it.player.soundWhenNoEffect()
            }
        }
    }
}