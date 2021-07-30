package com.mikael.rmcosmetics.menu

import com.mikael.rmcosmetics.core.BannerSystem
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.percentColor
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.game.SoundEffect
import net.eduard.api.lib.kotlin.percent
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenEffect
import net.eduard.redemikael.core.soundWhenNoEffect
import org.bukkit.Material
import org.bukkit.Sound

class MenuGadgets : Menu("Engenhocas", 6) {

    init {

        isAutoAlignItems = true
        autoAlignSkipLines = listOf(1, 5, 6)
        autoAlignSkipColumns = listOf(9, 1)
        autoAlignPerLine = 7
        autoAlignPerPage = 3 * autoAlignPerLine

        for (gadget in GadgetSystem.gadgets) {

            button {

                iconPerPlayer = {
                    val player = this
                    val item = ItemBuilder(gadget.material).name("§a" + gadget.name)

                    if (hasPermission(gadget.permission)) {

                        if (GadgetSystem.hasSelected(player)) {
                            val gadgetUsed = GadgetSystem.getSelectedGadget(player)
                            if (gadgetUsed == gadget) {
                                val loreUsada = mutableListOf<String>()

                                loreUsada.add("§8Engenhoca")
                                loreUsada.add("")
                                loreUsada.addAll(gadget.lore)
                                loreUsada.add("")
                                loreUsada.add("§fDelay entre usos: §7${gadget.delay}s")
                                loreUsada.add("")
                                loreUsada.add("§eClique para remover!")
                                item.lore(loreUsada)
                            } else {
                                val loreUsada = mutableListOf<String>()

                                loreUsada.add("§8Engenhoca")
                                loreUsada.add("")
                                loreUsada.addAll(gadget.lore)
                                loreUsada.add("")
                                loreUsada.add("§fDelay entre usos: §7${gadget.delay}s")
                                loreUsada.add("")
                                loreUsada.add("§eClique para utilizar!")
                                item.lore(loreUsada)
                            }
                        } else {
                            val loreUsada = mutableListOf<String>()

                            loreUsada.add("§8Engenhoca")
                            loreUsada.add("")
                            loreUsada.addAll(gadget.lore)
                            loreUsada.add("")
                            loreUsada.add("§fDelay entre usos: §7${gadget.delay}s")
                            loreUsada.add("")
                            loreUsada.add("§eClique para utilizar!")
                            item.lore(loreUsada)
                        }
                    } else {
                        val loreUsada = mutableListOf<String>()

                        loreUsada.add("§8Engenhoca")
                        loreUsada.add("")
                        loreUsada.addAll(gadget.lore)
                        loreUsada.add("")
                        loreUsada.add("§fDelay entre usos: §7${gadget.delay}s")
                        loreUsada.add("")
                        loreUsada.add("§cVocê não possui essa engenhoca.")
                        item.lore(loreUsada)
                    }

                    item
                }
                click = ClickEffect {
                    val player = it.player
                    if (player.hasPermission(gadget.permission)) {

                        if (GadgetSystem.hasSelected(player)) {
                            val gadgetUsed = GadgetSystem.getSelectedGadget(player)
                            if (gadgetUsed == gadget) {
                                player.soundWhenEffect()
                                player.sendMessage("§cVocê removeu a engenhoca ${gadget.name}.")
                                player.inventory.setItem(5, ItemBuilder(Material.AIR))
                                GadgetSystem.deselect(player)
                                open(player, getPageOpen(player))
                            } else {
                                player.soundWhenEffect()
                                player.sendMessage("§aVocê selecionou a engenhoca ${gadget.name}.")
                                player.inventory.setItem(5, gadget.icon)
                                GadgetSystem.select(player, gadget)
                                open(player, getPageOpen(player))
                            }
                        } else {
                            player.soundWhenEffect()
                            player.sendMessage("§aVocê selecionou a engenhoca ${gadget.name}.")
                            player.inventory.setItem(5, gadget.icon)
                            GadgetSystem.select(player, gadget)
                            open(player, getPageOpen(player))
                        }
                    } else {
                        player.soundWhenNoEffect()
                    }
                }

                button("remove-gadget") {
                    setPosition(4, 6)

                    fixed = true
                    iconPerPlayer = {
                        ItemBuilder(Material.BARRIER)
                                .name("§cRemover Engenhoca")
                                .lore("§7Remove sua engenhoca atual.")
                    }
                    click = ClickEffect {
                        val player = it.player

                        player.soundWhenEffect()
                        if (GadgetSystem.hasSelected(player)) {
                            player.inventory.setItem(5, ItemBuilder(Material.AIR))
                            GadgetSystem.deselect(player)
                            player.sendMessage("§cVocê removeu sua engenhoca atual.")
                            open(player, getPageOpen(player))
                        } else {
                            player.sendMessage("§cVocê não possui uma engenhoca selecioanda.")
                        }
                    }
                }
            }
            button("info") {
                setPosition(6, 6)

                fixed = true
                iconPerPlayer = {
                    val player = this
                    var usedGadgetName = "Nenhum"

                    if (GadgetSystem.hasSelected(player)) {

                        val usedGadget = GadgetSystem.getSelectedGadget(player)
                        val gadgetName = usedGadget.name
                        usedGadgetName = gadgetName
                    }

                    val gadgetsDesbloqueados = GadgetSystem.gadgets.count {
                        hasPermission(it.permission)
                    }

                    var porcentagemDesbloqueada = gadgetsDesbloqueados.toDouble() / GadgetSystem.gadgets.size
                    var porcentagemTexto = porcentagemDesbloqueada.percent() + "%"
                    val corNumero = porcentagemDesbloqueada.percentColor()

                    ItemBuilder(Material.PAPER)
                            .name("§aInformações")
                            .lore(
                                    "§8Engenhocas",
                                    "",
                                    "§7Você pode encontrar novas",
                                    "§7engenhocas em §bCaixas Misteriosas§7.",
                                    "",
                                    "§fDesbloqueados: ${corNumero}${gadgetsDesbloqueados}/${GadgetSystem.gadgets.size} §8(${porcentagemTexto})",
                                    "§fSelecionado atualmente:",
                                    "§a▸ ${usedGadgetName}"
                            )
                }
                click = ClickEffect {
                    it.player.soundWhenNoEffect()
                }
            }

            backPage.item = ItemBuilder(Material.INK_SACK).data(1)
                    .name("§cVoltar")
                    .lore("§7Para Cosméticos.")
            backPage.setPosition(5, 6)
            backPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

            nextPage.item = ItemBuilder(Material.INK_SACK).data(10)
                    .name("§aPágina %page")
                    .lore("§7Ir até a página %page.")
            nextPage.setPosition(9, 6)
            nextPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

            previousPage.item = ItemBuilder(Material.INK_SACK).data(8)
                    .name("§aPágina %page")
                    .lore("§7Ir até a página %page.")
            previousPage.setPosition(1, 6)
            previousPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

        }
    }
}