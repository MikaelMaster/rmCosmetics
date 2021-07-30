package com.mikael.rmcosmetics.menu

import com.mikael.rmcosmetics.core.HatAnimatedSystem
import com.mikael.rmcosmetics.objects.HatAnimated
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.kotlin.format
import net.eduard.api.lib.kotlin.name
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.user
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class MenuConfirmAnimatedHatBuy : Menu("Confirmar Compra?", 4) {
    companion object {
        lateinit var instance: MenuConfirmAnimatedHatBuy
    }

    val comprando = mutableMapOf<Player, HatAnimated>()

    init {
        instance = this

        button("confirm-buy") {
            setPosition(5, 2)

            iconPerPlayer = {
                val hat = comprando[player]!!
                val hatpreco = HatAnimatedSystem.precoemgold[hat]

                val item = ItemBuilder().skin(hat.urls.keys.last())

                item.name = ("§aComprar Chapéu Animado '${hat.display}'")
                item.lore(
                    "§7Tem certeza que deseja comprar",
                    "§7este chapéu animado? (${hat.display})",
                    "",
                    "§fIsto custará: §6${hatpreco?.format(false)} Gold"
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
                    MenuAnimatedHats.instance.open(player)
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

                    val hat = comprando[player]!!
                    val hatpreco = HatAnimatedSystem.precoemgold[hat]!!

                    if (user.gold >= hatpreco) {
                        player.closeInventory()
                        user.gold -= hatpreco
                        user.updateQueue()
                        Mine.makeCommand("lp user ${player.name} permission set ${hat.permission}")
                        player.sendMessage(
                            "§Chapéu Animado '${hat.display}' adquirido com sucesso! Valor gasto: §6${
                                hatpreco.format(
                                    false
                                )
                            } Gold§a."
                        )
                        player.playSound(player.location, Sound.VILLAGER_YES, 2f, 1f)
                        MenuAnimatedHats.instance.open(player)
                    }
                }
            }
        }
    }
}