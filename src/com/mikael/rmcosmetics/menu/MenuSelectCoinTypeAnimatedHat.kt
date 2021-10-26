package com.mikael.rmcosmetics.menu

import com.mikael.rmcosmetics.core.HatAnimatedSystem
import com.mikael.rmcosmetics.objects.HatAnimated
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.kotlin.name
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenNoEffect
import net.eduard.redemikael.core.soundWhenSwitchMenu
import net.eduard.redemikael.core.user
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class MenuSelectCoinTypeAnimatedHat : Menu("Qual moeda deseja usar?", 3) {

    companion object {
        lateinit var instance: MenuSelectCoinTypeAnimatedHat
    }

    val comprando = mutableMapOf<Player, HatAnimated>()

    init {
        instance = this@MenuSelectCoinTypeAnimatedHat
        cooldownBetweenInteractions = 0

        button("gold") {
            setPosition(3, 2)

            iconPerPlayer = {
                val hat = comprando[player]!!
                val user = player.user
                val compravelporgold = HatAnimatedSystem.compravelporgold[hat]
                val hatpreco = HatAnimatedSystem.precoemgold[hat]

                val item = ItemBuilder(Material.GOLD_INGOT)

                item.name = ("§aSelecionar moeda: §6Gold")

                if (compravelporgold == true) {
                    if (user.gold >= hatpreco!!) {
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

                val hat = comprando[player]!!
                val compravelporgold = HatAnimatedSystem.compravelporgold[hat]
                val hatpreco = HatAnimatedSystem.precoemgold[hat]

                if (compravelporgold == true) {
                    if (user.gold >= hatpreco!!) {
                        player.soundWhenSwitchMenu()
                        player.sendMessage("§aVocê selecionou a moeda §6Gold §apara prosseguir com sua compra.")
                        MenuConfirmAnimatedHatBuy.instance
                            .comprando[player] = hat

                        MenuConfirmAnimatedHatBuy.instance.open(player)
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
                val hat = comprando[player]!!
                val compravelporcash = HatAnimatedSystem.compravelporcash[hat]
                val hatpreco = HatAnimatedSystem.precoemcash[hat]

                val item = ItemBuilder(Material.DIAMOND)

                item.name = ("§aSelecionar moeda: §bCash")

                if (compravelporcash == true) {
                    if (user.cash >= hatpreco!!) {
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

                val hat = comprando[player]!!
                val compravelporcash = HatAnimatedSystem.compravelporcash[hat]
                val hatpreco = HatAnimatedSystem.precoemcash[hat]

                if (compravelporcash == true) {
                    if (user.cash >= hatpreco!!) {
                        player.soundWhenSwitchMenu()
                        player.sendMessage("§aVocê selecionou a moeda §bCash §apara prosseguir com sua compra.")
                        MenuConfirmAnimatedHatBuy2.instance
                            .comprando[player] = hat

                        MenuConfirmAnimatedHatBuy2.instance.open(player)
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