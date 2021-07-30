package com.mikael.rmcosmetics.menu

import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenNoEffect
import net.eduard.redemikael.core.soundWhenSwitchMenu
import org.bukkit.Material

class MenuRequirementsYT : Menu("Informações Tag YouTuber",4) {

    init {
        openWithCommand = "/youtuber"
        openWithCommandText = "/yt"

        button("requirements") {
            setPosition(5, 2)

            iconPerPlayer = {
                ItemBuilder(Material.REDSTONE_BLOCK)
                    .name("§cRequisitos Gerais")
                    .lore(
                        "§eRequisitos Tag MiniYT:",
                        " §fInscritos: §7500",
                        " §fJogador da Rede Mift a: §77 dias",
                        " §fVídeos no servidor: §71 vídeo por semana",
                        " §fMédia de visualizações por vídeo: §780 views",
                        " §fVídeos já gravados no servidor: §75 vídeos",
                        "",
                        "§eRequisitos Tag YouTuber:",
                        " §fInscritos: §75000",
                        " §fJogador da Rede Mift a: §714 dias",
                        " §fVídeos no servidor: §72 vídeos por semana",
                        " §fMédia de visualizações por vídeo: §7350 views",
                        " §fVídeos já gravados no servidor: §710 vídeos"
                    )
            }
            click = ClickEffect {
                it.player.soundWhenNoEffect()
            }
            button("close") {
                setPosition(5, 4)

                iconPerPlayer = {
                    ItemBuilder(Material.INK_SACK).data(1)
                        .name("§cFechar")
                        .lore("§7Fecha este Menu.")
                }
                click = ClickEffect {
                    it.player.soundWhenSwitchMenu()
                    it.player.closeInventory()
                }
                button("info") {
                    setPosition(6, 4)

                    iconPerPlayer = {
                        ItemBuilder(Material.PAPER)
                            .name("§aInformações")
                            .lore(
                                "§7Para você requisitar sua Tag, você",
                                "§7deve possuir alguns requisitos que estão",
                                "§7informados no ícone 'Requisitos Gerais'.",
                                "",
                                "§7Ao possuir os requisitos, você poderá",
                                "§7solicitar sua Tag. Para fazer isto,",
                                "§7é simples! Você deve acessar nosso",
                                "§7Discord, ir em #Ticket e abrir um Ticket",
                                "§7requisitando sua Tag. Não se esqueça de",
                                "§7enviar o link do seu canal e prints",
                                "§7provando que você possui os requisitos.",
                                "",
                                "§eClique para entrar em nosso Discord!"
                            )
                    }
                    click = ClickEffect {
                        it.player.chat("/discord")
                    }
                }
            }
        }
    }
}