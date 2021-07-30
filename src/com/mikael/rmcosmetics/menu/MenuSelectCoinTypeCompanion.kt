package com.mikael.rmcosmetics.menu

import com.kirelcodes.miniaturepets.pets.PetContainer
import com.mikael.rmcosmetics.core.CompanionSystem
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

class MenuSelectCoinTypeCompanion : Menu("Qual moeda deseja usar?", 3) {

    companion object {
        lateinit var instance: MenuSelectCoinTypeCompanion
    }

    val comprando = mutableMapOf<Player, PetContainer>()

    init {
        instance = this

        button("gold") {
            setPosition(3, 2)

            iconPerPlayer = {
                val companion = comprando[player]!!
                val user = player.user
                val compravelporgold = CompanionSystem.compravelporgold[companion]
                val companionpreco = CompanionSystem.precoemgold[companion]

                val item = ItemBuilder(Material.GOLD_INGOT)

                item.name = ("§aSelecionar moeda: §6Gold")

                if (compravelporgold == true) {
                    if (user.gold >= companionpreco!!) {
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

                val companion = comprando[player]!!
                val compravelporgold = CompanionSystem.compravelporgold[companion]
                val companionpreco = CompanionSystem.precoemgold[companion]

                if (compravelporgold == true) {
                    if (user.gold >= companionpreco!!) {
                        player.soundWhenSwitchMenu()
                        player.sendMessage("§aVocê selecionou a moeda §6Gold §apara prosseguir com sua compra.")
                        MenuConfirmCompanionBuy.instance
                            .comprando[player] = companion

                        MenuConfirmCompanionBuy.instance.open(player)
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
                val companion = comprando[player]!!
                val compravelporcash = CompanionSystem.compravelporcash[companion]
                val companionpreco = CompanionSystem.precoemcash[companion]

                val item = ItemBuilder(Material.DIAMOND)

                item.name = ("§aSelecionar moeda: §bCash")

                if (compravelporcash == true) {
                    if (user.cash >= companionpreco!!) {
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

                val companion = comprando[player]!!
                val compravelporcash = CompanionSystem.compravelporcash[companion]
                val companionpreco = CompanionSystem.precoemcash[companion]

                if (compravelporcash == true) {
                    if (user.cash >= companionpreco!!) {
                        player.soundWhenSwitchMenu()
                        player.sendMessage("§aVocê selecionou a moeda §bCash §apara prosseguir com sua compra.")
                        MenuConfirmCompanionBuy2.instance
                            .comprando[player] = companion

                        MenuConfirmCompanionBuy2.instance.open(player)
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