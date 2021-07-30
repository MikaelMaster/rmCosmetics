package com.mikael.rmcosmetics.menu

import com.kirelcodes.miniaturepets.pets.PetContainer
import com.mikael.rmcosmetics.core.CompanionSystem
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.kotlin.format
import net.eduard.api.lib.kotlin.lore
import net.eduard.api.lib.kotlin.name
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.user
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class MenuConfirmCompanionBuy2 : Menu("Confirmar Compra?", 4) {

    companion object {
        lateinit var instance: MenuConfirmCompanionBuy2
    }

    val comprando = mutableMapOf<Player, PetContainer>()

    init {
        instance = this
        button("confirm-buy") {
            setPosition(5, 2)

            iconPerPlayer = {
                val companion = comprando[player]!!
                val companionpreco = CompanionSystem.precoemcash[companion]

                val item = companion.symbol.clone()

                item.name = ("§aComprar Companheiro '${companion.name}'")
                item.lore(
                    "§7Tem certeza que deseja comprar",
                    "§7este companheiro? (${companion.name})",
                    "",
                    "§fIsto custará: §b${companionpreco?.format(false)} Cash"
                )
                item
            }

            button("cancel") {
                setPosition(3, 3)
                icon = ItemBuilder(Material.WOOL).data(14)
                    .name("§cCancelar Compra").lore(
                        "§7Cancela esta ação.",
                        "",
                        "§7§nNenhum§7 valor será gasto."
                    )
                click = ClickEffect {
                    val player = it.player
                    player.closeInventory()
                    player.sendMessage("§cOperação cancelada!")
                    player.playSound(it.player.location, Sound.VILLAGER_NO, 2f, 1f)
                    MenuCompanions.instance.open(player)

                }
            }
            button("confirm") {
                setPosition(7, 3)
                icon = ItemBuilder(Material.WOOL).data(5)
                    .name("§aConfirmar Compra").lore(
                        "§7Confirma a compra deste item.",
                        "",
                        "§7Esta ação §nnão§7 pode ser revertida!"
                    )

                click = ClickEffect {
                    val player = it.player
                    val user = player.user

                    val companion = comprando[player]!!
                    val companionpreco = CompanionSystem.precoemcash[companion]!!

                    if (user.cash >= companionpreco) {
                        player.closeInventory()
                        user.cash -= companionpreco
                        user.updateQueue()
                        Mine.makeCommand("lp user ${player.name} permission set ${companion.permission}")
                        player.sendMessage(
                            "§aCompanheiro '${companion.name}' adquirido com sucesso! Valor gasto: §b${
                                companionpreco.format(
                                    false
                                )
                            } Cash§a."
                        )
                        player.playSound(player.location, Sound.VILLAGER_YES, 2f, 1f)
                        MenuCompanions.instance.open(player)
                    }
                }
            }
        }
    }
}