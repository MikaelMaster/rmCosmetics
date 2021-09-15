package com.mikael.rmcosmetics.menu

import com.mikael.rmcosmetics.core.BannerSystem
import com.mikael.rmcosmetics.core.ClosetSystem
import com.mikael.rmcosmetics.core.HatAnimatedSystem
import com.mikael.rmcosmetics.core.HatSystem
import com.mikael.rmcosmetics.percentColor
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.game.SoundEffect
import net.eduard.api.lib.kotlin.percent
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenEffect
import net.eduard.redemikael.core.soundWhenNoEffect
import org.bukkit.*

class MenuHats : Menu("Chapéus", 6) {

    init {
        isAutoAlignItems = true

        cooldownBetweenInteractions = 0
        autoAlignSkipLines = listOf(1, 5, 6)
        autoAlignSkipColumns = listOf(9, 1)
        autoAlignPerLine = 7
        autoAlignPerPage = 3 * autoAlignPerLine

        for (hat in HatSystem.hats) {

            button {

                iconPerPlayer = {
                    val player = this
                    val item = ItemBuilder()
                        .skin(hat.url).name("§a" + hat.display)

                    if (HatSystem.hasSelected(player)) {
                        if (HatSystem.getSelectedHat(player) == hat) {
                            item.name("§6${hat.display}")
                            item.glowed()
                        } else if (player.hasPermission(hat.permission)) {
                            item.name("§a${hat.display}")
                        } else {
                            item.name("§c${hat.display}")
                        }
                    } else if (player.hasPermission(hat.permission)) {
                        item.name("§a${hat.display}")
                    } else {
                        item.name("§c${hat.display}")
                    }

                    if (hasPermission(hat.permission)) {
                        if (HatSystem.hasSelected(player)) {
                            val hatUsed = HatSystem.getSelectedHat(player)
                            if (hatUsed == hat) {
                                val loreUsada = mutableListOf<String>()

                                loreUsada.add("§8Chapéu")
                                loreUsada.add("")
                                loreUsada.add("§7Exiba seu estilo em nossos lobbies")
                                loreUsada.add("§7utilizando o chapéu ${hat.display}§7.")
                                loreUsada.addAll(hat.lore)
                                loreUsada.add("§eClique para remover!")
                                item.lore(loreUsada)

                            } else {
                                val loreUsada = mutableListOf<String>()

                                loreUsada.add("§8Chapéu")
                                loreUsada.add("")
                                loreUsada.add("§7Exiba seu estilo em nossos lobbies")
                                loreUsada.add("§7utilizando o chapéu ${hat.display}§7.")
                                loreUsada.addAll(hat.lore)
                                loreUsada.add("§eClique para utilizar!")
                                item.lore(loreUsada)
                            }
                        } else {
                            val loreUsada = mutableListOf<String>()

                            loreUsada.add("§8Chapéu")
                            loreUsada.add("")
                            loreUsada.add("§7Exiba seu estilo em nossos lobbies")
                            loreUsada.add("§7utilizando o chapéu ${hat.display}§7.")
                            loreUsada.addAll(hat.lore)
                            loreUsada.add("§eClique para utilizar!")
                            item.lore(loreUsada)
                        }
                    } else {
                        val loreUsada = mutableListOf<String>()

                        loreUsada.add("§8Chapéu")
                        loreUsada.add("")
                        loreUsada.add("§7Exiba seu estilo em nossos lobbies")
                        loreUsada.add("§7utilizando o chapéu ${hat.display}§7.")
                        loreUsada.addAll(hat.lore)
                        loreUsada.add("§cVocê não possui esse chapéu.")
                        item.lore(loreUsada)
                    }

                    item
                }
                click = ClickEffect {
                    val player = it.player
                    if (player.hasPermission(hat.permission)) {

                        if (HatSystem.hasSelected(player)) {
                            val hatUsed = HatSystem.getSelectedHat(player)
                            if (hatUsed == hat) {
                                player.soundWhenEffect()
                                player.sendMessage("§cVocê removeu o chapéu '${hat.display}'.")
                                player.equipment.helmet = null
                                HatSystem.deselect(player)
                                open(player, getPageOpen(player))
                            } else {
                                player.soundWhenEffect()

                                if (HatAnimatedSystem.hasSelected(player)) {
                                    HatAnimatedSystem.deselect(player)
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
                                player.sendMessage("§aVocê equipou o chapéu '${hat.display}'.")
                                player.equipment.helmet = ItemBuilder().skin(hat.url)
                                    .name("§a${hat.display}")
                                HatSystem.select(player, hat)
                                open(player, getPageOpen(player))
                            }
                        } else {
                            player.soundWhenEffect()

                            if (HatAnimatedSystem.hasSelected(player)) {
                                HatAnimatedSystem.deselect(player)
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
                            player.sendMessage("§aVocê equipou o chapéu '${hat.display}'.")
                            player.equipment.helmet = ItemBuilder().skin(hat.url)
                                .name("§a${hat.display}")
                            HatSystem.select(player, hat)
                            open(player, getPageOpen(player))
                        }
                    } else {
                        player.soundWhenNoEffect()
                    }
                }
            }
        }
        button("remove-hat") {
            setPosition(4, 6)
            fixed = true
            iconPerPlayer = {
                ItemBuilder(Material.BARRIER)
                    .name("§cRemover Chapéu")
                    .lore("§7Remove seu chapéu atual.")
            }
            click = ClickEffect {
                val player = it.player

                player.soundWhenEffect()
                if (HatSystem.hasSelected(player)) {
                    player.equipment.helmet = null
                    HatSystem.deselect(player)
                    player.sendMessage("§cSeu chapéu atual foi removido.")
                    open(player, getPageOpen(player))
                } else {
                    player.sendMessage("§cVocê não possui um chapéu selecionado.")
                }
            }

            button("info") {
                setPosition(6, 6)
                fixed = true
                iconPerPlayer = {
                    val player = this
                    var usedHatName = "Nenhum"

                    if (HatSystem.hasSelected(player)) {

                        val usedHat = HatSystem.getSelectedHat(player)
                        val hatName = usedHat.display
                        usedHatName = hatName
                    }

                    val hatsDesbloqueados = HatSystem.hats.count {
                        hasPermission(it.permission)
                    }

                    var porcentagemDesbloqueada = hatsDesbloqueados.toDouble() / HatSystem.hats.size
                    var porcentagemTexto = porcentagemDesbloqueada.percent() + "%"
                    val corNumero = porcentagemDesbloqueada.percentColor()

                    ItemBuilder(Material.PAPER)
                        .name("§aInformações")
                        .lore(
                            "§8Chapéus",
                            "",
                            "§7Você pode encontrar novos chapéus",
                            "§7em §bCaixas Misteriosas de Cosméticos§7.",
                            "",
                            "§fDesbloqueados: ${corNumero}${hatsDesbloqueados}/${HatSystem.hats.size} §8(${porcentagemTexto})",
                            "§fSelecionado atualmente:",
                            "§a▸ $usedHatName"
                        )
                }
                click = ClickEffect {
                    it.player.soundWhenNoEffect()
                }
            }

            backPage.item = ItemBuilder(Material.ARROW)
                .name("§aVoltar")
            backPage.setPosition(5, 6)
            backPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

            nextPage.item = ItemBuilder(Material.ARROW)
                .name("§aPágina %page")
            nextPage.setPosition(9, 3)
            nextPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

            previousPage.item = ItemBuilder(Material.ARROW)
                .name("§aPágina %page")
            previousPage.setPosition(1, 3)
            previousPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)
        }
    }
}