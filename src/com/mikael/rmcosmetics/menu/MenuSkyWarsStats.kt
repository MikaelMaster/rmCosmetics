package com.mikael.rmcosmetics.menu

import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.game.SoundEffect
import net.eduard.api.lib.kotlin.format
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenNoEffect
import net.eduard.redemikael.core.user
import org.bukkit.Material
import org.bukkit.Sound

class MenuSkyWarsStats : Menu("Estatísticas - Sky Wars", 5) {

    init {
        openWithCommand = "/swstats"

        backPage.item = ItemBuilder(Material.INK_SACK).data(1)
            .name("§cVoltar")
            .lore("§7Para Meu Perfil.")
        backPage.setPosition(5, 5)
        backPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

        button("general") {
            setPosition(5, 1)

            iconPerPlayer = {
                val skywarsUser = player.user.skywars

                ItemBuilder(Material.GRASS)
                    .name("§aGeral")
                    .lore(
                        "§eTotal:",
                        " §8▪ §fVitórias: §70",
                        " §8▪ §fPartidas: §70",
                        " §8▪ §fEliminações: §70",
                        " §8▪ §fMortes: §70",
                        " §8▪ §fAbandonos: §70",
                        " §8▪ §fAssistências: §70",
                        "",
                        "§fKDR: §70",
                        "",
                        "§eEste Mês:",
                        " §8▪ §fVitórias: §70",
                        " §8▪ §fPartidas: §70",
                        " §8▪ §fEliminações: §70",
                        " §8▪ §fMortes: §70",
                        " §8▪ §fAbandonos: §70",
                        " §8▪ §fAssistências: §70",
                        "",
                        "§fCoins: §e${skywarsUser.coins.format(false)}"
                    )
            }
            click = ClickEffect {
                it.player.soundWhenNoEffect()
            }
        }
        button("solo") {
            setPosition(4, 3)

            iconPerPlayer = {
                ItemBuilder(Material.DIAMOND_SWORD)
                    .name("§aSolo")
                    .lore(
                        "§eTotal:",
                        " §8▪ §fVitórias: §70",
                        " §8▪ §fPartidas: §70",
                        " §8▪ §fEliminações: §70",
                        " §8▪ §fMortes: §70",
                        " §8▪ §fAbandonos: §70",
                        " §8▪ §fAssistências: §70",
                        "",
                        "§fKDR: §70",
                        "",
                        "§eEste Mês:",
                        " §8▪ §fVitórias: §70",
                        " §8▪ §fPartidas: §70",
                        " §8▪ §fEliminações: §70",
                        " §8▪ §fMortes: §70",
                        " §8▪ §fAbandonos: §70",
                        " §8▪ §fAssistências: §70"
                    )
            }
            click = ClickEffect {
                it.player.soundWhenNoEffect()
            }
        }
        button("duo") {
            setPosition(6, 3)

            iconPerPlayer = {
                ItemBuilder(Material.GOLD_SWORD)
                    .name("§aDupla")
                    .lore(
                        "§eTotal:",
                        " §8▪ §fVitórias: §70",
                        " §8▪ §fPartidas: §70",
                        " §8▪ §fEliminações: §70",
                        " §8▪ §fMortes: §70",
                        " §8▪ §fAbandonos: §70",
                        " §8▪ §fAssistências: §70",
                        "",
                        "§fKDR: §70",
                        "",
                        "§eEste Mês:",
                        " §8▪ §fVitórias: §70",
                        " §8▪ §fPartidas: §70",
                        " §8▪ §fEliminações: §70",
                        " §8▪ §fMortes: §70",
                        " §8▪ §fAbandonos: §70",
                        " §8▪ §fAssistências: §70"
                    )
            }
            click = ClickEffect {
                it.player.soundWhenNoEffect()
            }
        }
    }
}