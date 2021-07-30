package com.mikael.rmcosmetics.menu

import com.mikael.rmcosmetics.core.ParticleSystem
import com.mikael.rmcosmetics.objects.ParticleCosmetic
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

class MenuConfirmParticleBuy2 : Menu("Confirmar Compra?", 4) {

    companion object {
        lateinit var instance: MenuConfirmParticleBuy2
    }

    val comprando = mutableMapOf<Player, ParticleCosmetic>()

    init {
        instance = this
        button("confirm-buy") {
            setPosition(5, 2)

            iconPerPlayer = {
                val particle = comprando[player]!!
                val particlepreco = ParticleSystem.precoemcash[particle]

                val item = ItemBuilder(particle.material)

                item.name = ("§aComprar Partícula '${particle.display}'")
                item.lore(
                    "§7Tem certeza que deseja comprar",
                    "§7esta Partícula? (${particle.display})",
                    "",
                    "§fIsto custará: §b${particlepreco?.format(false)} Cash"
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
                    MenuParticles.instance.open(player)

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

                    val particle = comprando[player]!!
                    val particlepreco = ParticleSystem.precoemcash[particle]!!

                    if (user.cash >= particlepreco) {
                        player.closeInventory()
                        user.cash -= particlepreco
                        user.updateQueue()
                        Mine.makeCommand("lp user ${player.name} permission set ${particle.permission}")
                        player.sendMessage(
                            "§aPartícula '${particle.display}' adquirida com sucesso! Valor gasto: §b${
                                particlepreco.format(
                                    false
                                )
                            } Cash§a."
                        )
                        player.playSound(player.location, Sound.VILLAGER_YES, 2f, 1f)
                        MenuParticles.instance.open(player)
                    }
                }
            }
        }
    }
}