package com.mikael.rmcosmetics.menu

import com.mikael.rmcosmetics.objects.MiftPet
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenNoEffect
import net.eduard.redemikael.core.soundWhenSwitchMenu
import net.eduard.redemikael.core.user
import org.bukkit.Material
import org.bukkit.entity.Player

class MenuSelectCoinTypePet : Menu("Qual moeda deseja usar?", 3) {

    companion object {
        lateinit var instance: MenuSelectCoinTypePet
    }

    val comprando = mutableMapOf<Player, MiftPet>()

    init {
        instance = this@MenuSelectCoinTypePet
        cooldownBetweenInteractions = 0

        button("gold") {
            setPosition(3, 2)

            iconPerPlayer = {
                val player = this
                val user = player.user
                val pet = comprando[player]!!
                val item = ItemBuilder(Material.GOLD_INGOT)
                item.name("§aSelecionar moeda: §6Gold")
                if (pet.isGoldBuyable) {
                    if (user.gold >= pet.goldPrice) {
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
                val pet = comprando[player]!!
                if (pet.isGoldBuyable) {
                    if (user.gold >= pet.goldPrice) {
                        player.soundWhenSwitchMenu()
                        player.sendMessage("§aVocê selecionou a moeda §6Gold §apara prosseguir com sua compra.")
                        MenuConfirmPetBuy.instance.comprando[player] = pet
                        MenuConfirmPetBuy.instance.open(player)
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
                val player = this
                val user = player.user
                val pet = comprando[player]!!
                val item = ItemBuilder(Material.DIAMOND)
                item.name("§aSelecionar moeda: §bCash")
                if (pet.isCashBuyable) {
                    if (user.cash >= pet.cashPrice) {
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
                val pet = comprando[player]!!
                if (pet.isCashBuyable) {
                    if (user.cash >= pet.cashPrice) {
                        player.soundWhenSwitchMenu()
                        player.sendMessage("§aVocê selecionou a moeda §bCash §apara prosseguir com sua compra.")
                        MenuConfirmPetBuy2.instance.comprando[player] = pet
                        MenuConfirmPetBuy2.instance.open(player)
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