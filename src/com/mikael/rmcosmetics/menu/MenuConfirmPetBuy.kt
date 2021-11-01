package com.mikael.rmcosmetics.menu

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.objects.MiftPet
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.kotlin.format
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.soundWhenNoSuccess
import net.eduard.redemikael.core.soundWhenSuccess
import net.eduard.redemikael.core.spigot.util.CoreGeneralUtils
import net.eduard.redemikael.core.user
import org.bukkit.Material
import org.bukkit.entity.Player

class MenuConfirmPetBuy : Menu("Confirmar Compra?", 4) {
    companion object {
        lateinit var instance: MenuConfirmPetBuy
    }

    val comprando = mutableMapOf<Player, MiftPet>()

    init {
        instance = this@MenuConfirmPetBuy
        cooldownBetweenInteractions = 0

        button("confirm-buy") {
            setPosition(5, 2)

            iconPerPlayer = {
                val player = this
                val pet = comprando[player]!!
                val item = ItemBuilder().skin(pet.url)
                item.name("§aComprar Mascote '${pet.display}'")
                item.lore(
                    "§7Tem certeza que deseja comprar",
                    "§7este mascote? (${pet.display})",
                    "",
                    "§fIsto custará: §6${pet.goldPrice.format(false)} Gold"
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
                    player.soundWhenNoSuccess()
                    MenuPets.instance.open(player)
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
                    val pet = comprando[player]!!
                    if (user.gold >= pet.goldPrice) {
                        player.closeInventory()
                        CoreGeneralUtils.buyProductWithGold(player, pet.goldPrice, "Pet - ${pet.display}")
                        Mine.makeCommand("lp user ${player.name} permission set ${pet.permission}")
                        player.sendMessage("§aPet '${pet.display}' adquirido com sucesso!")
                        player.sendMessage("§aValor gasto: §6${pet.goldPrice.format(false)} Gold§a.")
                        player.soundWhenSuccess()
                        MiftCosmetics.instance.syncDelay(10) {
                            MenuPets.instance.open(player)
                        }
                    }
                }
            }
        }
    }
}