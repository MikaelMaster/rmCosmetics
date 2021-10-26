package com.mikael.rmcosmetics.menu

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.ParticleSystem
import com.mikael.rmcosmetics.objects.ParticleCosmetic
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.kotlin.format
import net.eduard.api.lib.kotlin.name
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.spigot.util.CoreGeneralUtils
import net.eduard.redemikael.core.user
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class MenuConfirmParticleBuy : Menu("Confirmar Compra?", 4) {

    companion object {
        lateinit var instance: MenuConfirmParticleBuy
    }

    val comprando = mutableMapOf<Player, ParticleCosmetic>()

    init {
        instance = this@MenuConfirmParticleBuy
        cooldownBetweenInteractions = 0

        button("confirm-buy") {
            setPosition(5, 2)

            iconPerPlayer = {
                val particle = comprando[player]!!
                val particlepreco = ParticleSystem.precoemgold[particle]

                val item = ItemBuilder(particle.material)

                item.name = ("§aComprar Partícula '${particle.display}'")
                item.lore(
                    "§7Tem certeza que deseja comprar",
                    "§7esta partícula? (${particle.display})",
                    "",
                    "§fIsto custará: §6${particlepreco?.format(false)} Gold"
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
                    val particlepreco = ParticleSystem.precoemgold[particle]!!

                    if (user.gold >= particlepreco) {
                        player.closeInventory()
                        CoreGeneralUtils.buyProductWithGold(player, particlepreco, "Partícula - ${particle.display}")
                        Mine.makeCommand("lp user ${player.name} permission set ${particle.permission}")
                        player.sendMessage("§aPartícula '${particle.display}' adquirida com sucesso!")
                        player.sendMessage("§aValor gasto: §6${particlepreco.format(false)} Gold§a.")
                        player.playSound(player.location, Sound.VILLAGER_YES, 2f, 1f)
                        MiftCosmetics.instance.syncDelay(10) {
                            MenuParticles.instance.open(player)
                        }
                    }
                }
            }
        }
    }
}