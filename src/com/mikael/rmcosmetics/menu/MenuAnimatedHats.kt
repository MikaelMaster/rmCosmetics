package com.mikael.rmcosmetics.menu

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.BannerSystem
import com.mikael.rmcosmetics.core.ClosetSystem
import com.mikael.rmcosmetics.core.HatAnimatedSystem
import com.mikael.rmcosmetics.core.HatSystem
import com.mikael.rmcosmetics.percentColor
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.game.SoundEffect
import net.eduard.api.lib.kotlin.format
import net.eduard.api.lib.kotlin.percent
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenEffect
import net.eduard.redemikael.core.soundWhenNoEffect
import net.eduard.redemikael.core.soundWhenSwitchMenu
import net.eduard.redemikael.core.user
import org.bukkit.Material
import org.bukkit.Sound

class MenuAnimatedHats : Menu("Chapéus Animados", 5) {

    companion object {
        lateinit var instance: MenuAnimatedHats
    }

    init {
        instance = this

        isAutoAlignItems = true
        autoAlignSkipLines = listOf(1, 4, 5)
        autoAlignSkipColumns = listOf(9, 1)
        autoAlignPerLine = 7
        autoAlignPerPage = 1 * autoAlignPerLine

        for (hat in HatAnimatedSystem.animatedHats) {
            button {

                iconPerPlayer = {
                    val player = this
                    val user = player.user

                    val item = ItemBuilder().skin(hat.urls.keys.last())
                        .name("§a" + hat.display)

                    val hatprecogold = HatAnimatedSystem.precoemgold[hat]!!
                    val hatprecocash = HatAnimatedSystem.precoemcash[hat]!!
                    val hatcompravel = HatAnimatedSystem.compravel[hat]!!
                    val hatporgold = HatAnimatedSystem.compravelporgold[hat]!!
                    val hatporcash = HatAnimatedSystem.compravelporcash[hat]!!

                    if (player.hasPermission(hat.groupPermission)) {
                        if (player.hasPermission(hat.permission)) {
                            if (HatAnimatedSystem.hasSelected(player)) {
                                val hatUsed = HatAnimatedSystem.getSelectedAnimatedHat(player)
                                if (hatUsed == hat) {
                                    val loreUsada = mutableListOf<String>()

                                    loreUsada.add("§8Chapéu Animado")
                                    loreUsada.add("")
                                    loreUsada.add("§7Esboce sua expressão em nossos lobbies")
                                    loreUsada.add("§7utilizando o chapéu animado ${hat.display}.")
                                    loreUsada.add("")
                                    loreUsada.add("§eClique para remover!")
                                    item.lore(loreUsada)
                                } else {
                                    val loreUsada = mutableListOf<String>()

                                    loreUsada.add("§8Chapéu Animado")
                                    loreUsada.add("")
                                    loreUsada.add("§7Esboce sua expressão em nossos lobbies")
                                    loreUsada.add("§7utilizando o chapéu animado ${hat.display}.")
                                    loreUsada.add("")
                                    loreUsada.add("§eClique para utilizar!")
                                    item.lore(loreUsada)
                                }
                            } else {
                                val loreUsada = mutableListOf<String>()

                                loreUsada.add("§8Chapéu Animado")
                                loreUsada.add("")
                                loreUsada.add("§7Esboce sua expressão em nossos lobbies")
                                loreUsada.add("§7utilizando o chapéu animado ${hat.display}.")
                                loreUsada.add("")
                                loreUsada.add("§eClique para utilizar!")
                                item.lore(loreUsada)
                            }
                        } else {
                            val loreUsada = mutableListOf<String>()

                            loreUsada.add("§8Chapéu Animado")
                            loreUsada.add("")
                            loreUsada.add("§7Esboce sua expressão em nossos lobbies")
                            loreUsada.add("§7utilizando o chapéu animado ${hat.display}.")
                            loreUsada.add("")
                            if (hatcompravel) {
                                loreUsada.add("§eOpções de compra:")
                            }
                            if (hatporgold) {
                                loreUsada.add(" §8▪ §fComprar com Gold: §6${hatprecogold.format(false)} Gold")
                            }
                            if (hatporcash) {
                                loreUsada.add(" §8▪ §fComprar com Cash: §b${hatprecocash.format(false)} Cash")
                            }
                            if (hatcompravel) {
                                loreUsada.add("")
                            }
                            if (hatcompravel) {
                                loreUsada.add(
                                    if (user.gold >= hatprecogold || user.cash >= hatprecocash)
                                        "§aClique para comprar!" else
                                        "§cVocê não possui saldo suficiente."
                                )
                            } else {
                                loreUsada.add("§cIndisponível para compra.")
                            }

                            item.lore(loreUsada)
                        }
                    } else {
                        val loreUsada = mutableListOf<String>()

                        loreUsada.add("§8Chapéu Animado")
                        loreUsada.add("")
                        loreUsada.add("§7Esboce sua expressão em nossos lobbies")
                        loreUsada.add("§7utilizando o chapéu animado ${hat.display}.")
                        loreUsada.add("")
                        loreUsada.add(hat.exclusiveGroupName)
                        item.lore(loreUsada)
                    }

                    item
                }
                click = ClickEffect {
                    val player = it.player
                    val user = player.user

                    val hatemgold = HatAnimatedSystem.precoemgold[hat]!!
                    val hatemcash = HatAnimatedSystem.precoemcash[hat]!!

                    if (player.hasPermission(hat.groupPermission)) {
                        if (player.hasPermission(hat.permission)) {

                            if (HatAnimatedSystem.hasSelected(player)) {
                                val hatUsed = HatAnimatedSystem.getSelectedAnimatedHat(player)
                                if (hatUsed == hat) {
                                    player.soundWhenEffect()
                                    player.sendMessage("§cVocê removeu o chapéu animado ${hat.display}.")
                                    player.equipment.helmet = null
                                    HatAnimatedSystem.deselect(player)
                                    open(player, getPageOpen(player))
                                } else {
                                    player.soundWhenEffect()

                                    if (HatSystem.hasSelected(player)) {
                                        HatSystem.deselect(player)
                                    }
                                    if (BannerSystem.hasSelected(player)) {
                                        BannerSystem.deselect(player)
                                    }
                                    val closet = ClosetSystem.getPlayerCloset(player)
                                    if (closet.helmet != null) {
                                        closet.helmet = null
                                        closet.helmetBright = false
                                        closet.helmetName = null
                                        closet.updateQueue()
                                    }
                                    player.equipment.helmet = null
                                    player.sendMessage("§aVocê equipou o chapéu animado ${hat.display}.")
                                    player.equipment.helmet = ItemBuilder().skin(hat.urls.keys.first())
                                        .name("§a${hat.display}")
                                    HatAnimatedSystem.select(player, hat)
                                    open(player, getPageOpen(player))
                                }
                            } else {
                                player.soundWhenEffect()

                                if (HatSystem.hasSelected(player)) {
                                    HatSystem.deselect(player)
                                }
                                if (BannerSystem.hasSelected(player)) {
                                    BannerSystem.deselect(player)
                                }
                                val closet = ClosetSystem.getPlayerCloset(player)
                                if (closet.helmet != null) {
                                    closet.helmet = null
                                    closet.helmetBright = false
                                    closet.helmetName = null
                                    closet.updateQueue()
                                }
                                player.equipment.helmet = null
                                player.sendMessage("§aVocê equipou o chapéu animado ${hat.display}.")
                                player.equipment.helmet = ItemBuilder().skin(hat.urls.keys.first())
                                    .name("§a${hat.display}")
                                HatAnimatedSystem.select(player, hat)
                                open(player, getPageOpen(player))
                            }
                        } else {
                            if (HatAnimatedSystem.compravel[hat] == true) {
                                if (user.gold >= hatemgold || user.cash >= hatemcash) {
                                    player.soundWhenSwitchMenu()
                                    MenuSelectCoinTypeAnimatedHat.instance
                                        .comprando[player] = hat

                                    MenuSelectCoinTypeAnimatedHat.instance.open(player)
                                } else {
                                    player.soundWhenNoEffect()

                                }
                            } else {
                                player.soundWhenNoEffect()
                            }
                        }
                    } else {
                        player.soundWhenNoEffect()
                    }
                }
            }
        }

        button("remove-animated-hat") {
            setPosition(4, 5)

            fixed = true
            iconPerPlayer = {
                ItemBuilder(Material.BARRIER)
                    .name("§cRemover Chapéu Animado")
                    .lore("§7Remove seu chapéu animado atual.")
            }
            click = ClickEffect {
                val player = it.player

                player.soundWhenEffect()
                if (HatAnimatedSystem.hasSelected(player)) {
                    player.equipment.helmet = null
                    HatAnimatedSystem.deselect(player)
                    player.sendMessage("§cSeu chapéu animado atual foi removido.")
                    open(player, getPageOpen(player))
                } else {
                    player.sendMessage("§cVocê não possui um chapéu animado selecionado.")
                }
            }
        }

        button("info") {
            setPosition(6, 5)

            fixed = true
            iconPerPlayer = {
                val player = this
                var usedHatName = "Nenhum"

                if (HatAnimatedSystem.hasSelected(player)) {

                    val usedHat = HatAnimatedSystem.getSelectedAnimatedHat(player)
                    val hatName = usedHat.display
                    usedHatName = hatName
                }

                val animatedHatsDesbloqueados = HatAnimatedSystem.animatedHats.count {
                    hasPermission(it.permission)
                }

                var porcentagemDesbloqueada = animatedHatsDesbloqueados.toDouble() / HatAnimatedSystem.animatedHats.size
                var porcentagemTexto = porcentagemDesbloqueada.percent() + "%"
                val corNumero = porcentagemDesbloqueada.percentColor()

                ItemBuilder(Material.PAPER)
                    .name("§aInformações")
                    .lore(
                        "§8Chapéus Animados",
                        "",
                        "§7Você pode encontrar novos chapéus animados",
                        "§7em §bCaixas Misteriosas §7ou comprá-los",
                        "§7utilizando §6Gold §7e §bCash§7.",
                        "",
                        "§fDesbloqueados: ${corNumero}${animatedHatsDesbloqueados}/${HatAnimatedSystem.animatedHats.size} §8(${porcentagemTexto})",
                        "§fSelecionado atualmente:",
                        "§a▸ ${usedHatName}"
                    )
            }
            click = ClickEffect {
                it.player.soundWhenNoEffect()
            }
        }

        backPage.item = ItemBuilder(Material.INK_SACK).data(1)
            .name("§cVoltar")
            .lore("§7Para Cosméticos.")
        backPage.setPosition(5, 5)
        backPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

    }
}