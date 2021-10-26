package com.mikael.rmcosmetics.menu

import com.mikael.rmcosmetics.core.ParticleSystem
import com.mikael.rmcosmetics.objects.ParticleCosmetic
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.kotlin.name
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenNoEffect
import net.eduard.redemikael.core.soundWhenSwitchMenu
import net.eduard.redemikael.core.user
import org.bukkit.Material
import org.bukkit.entity.Player

class MenuSelectCoinTypeParticle : Menu("Qual moeda deseja usar?", 3) {

    companion object {
        lateinit var instance: MenuSelectCoinTypeParticle
    }

    val comprando = mutableMapOf<Player, ParticleCosmetic>()

    init {
        instance = this@MenuSelectCoinTypeParticle
        cooldownBetweenInteractions = 0

        button("gold") {
            setPosition(3, 2)

            iconPerPlayer = {
                val particle = comprando[player]!!
                val user = player.user
                val compravelporgold = ParticleSystem.compravelporgold[particle]
                val precoemgold = ParticleSystem.precoemgold[particle]

                val item = ItemBuilder(Material.GOLD_INGOT)

                item.name = ("§aSelecionar moeda: §6Gold")

                if (compravelporgold == true) {
                    if (user.gold >= precoemgold!!) {
                        item.lore(
                            "§7Irá selecionar a moeda Gold para",
                            "§7continuar sua compra.",
                            "",
                            "§eClique para selecionar!"
                        )
                    } else {
                        item.lore(
                            "§7Irá selecionar a moeda Gold para",
                            "§7continuar sua compra.",
                            "",
                            "§cVocê não possui saldo em Gold o",
                            "§csuficiente para utilizar essa moeda."
                        )
                    }
                } else {
                    item.lore(
                        "§7Irá selecionar a moeda Gold para",
                        "§7continuar sua compra.",
                        "",
                        "§cEste item não pode ser comprado",
                        "§cutilizando a moeda Gold."
                    )
                }

                item
            }
            click = ClickEffect {
                val player = it.player
                val user = player.user

                val particle = comprando[player]!!
                val compravelporgold = ParticleSystem.compravelporgold[particle]
                val precoemgold = ParticleSystem.precoemgold[particle]

                if (compravelporgold == true) {
                    if (user.gold >= precoemgold!!) {
                        player.soundWhenSwitchMenu()
                        player.sendMessage("§aVocê selecionou a moeda §6Gold §apara prosseguir com sua compra.")
                        MenuConfirmParticleBuy.instance
                            .comprando[player] = particle

                        MenuConfirmParticleBuy.instance.open(player)
                    } else {
                        player.soundWhenNoEffect()
                    }
                } else {
                    player.soundWhenNoEffect()
                }
            }
        }

        button("cash") {
            setPosition(7, 2)

            iconPerPlayer = {
                val particle = comprando[player]!!
                val user = player.user
                val compravelporcash = ParticleSystem.compravelporcash[particle]
                val precoemcash = ParticleSystem.precoemcash[particle]

                val item = ItemBuilder(Material.DIAMOND)

                item.name = ("§aSelecionar moeda: §bCash")

                if (compravelporcash == true) {
                    if (user.cash >= precoemcash!!) {
                        item.lore(
                            "§7Irá selecionar a moeda Cash para",
                            "§7continuar sua compra.",
                            "",
                            "§eClique para selecionar!"
                        )
                    } else {
                        item.lore(
                            "§7Irá selecionar a moeda Cash para",
                            "§7continuar sua compra.",
                            "",
                            "§cVocê não possui saldo em Cash o",
                            "§csuficiente para utilizar essa moeda."
                        )
                    }
                } else {
                    item.lore(
                        "§7Irá selecionar a moeda Cash para",
                        "§7continuar sua compra.",
                        "",
                        "§cEste item não pode ser comprado",
                        "§cutilizando a moeda Cash."
                    )
                }

                item
            }
            click = ClickEffect {
                val player = it.player
                val user = player.user

                val particle = comprando[player]!!
                val compravelporcash = ParticleSystem.compravelporcash[particle]
                val precoemcash = ParticleSystem.precoemcash[particle]

                if (compravelporcash == true) {
                    if (user.cash >= precoemcash!!) {
                        player.soundWhenSwitchMenu()
                        player.sendMessage("§aVocê selecionou a moeda §bCash §apara prosseguir com sua compra.")
                        MenuConfirmParticleBuy2.instance
                            .comprando[player] = particle

                        MenuConfirmParticleBuy2.instance.open(player)
                    } else {
                        player.soundWhenNoEffect()
                    }
                } else {
                    player.soundWhenNoEffect()
                }
            }
        }
    }
}