package com.mikael.rmcosmetics.menu

import com.kirelcodes.miniaturepets.loader.PetLoader
import com.kirelcodes.miniaturepets.pets.Pet
import com.kirelcodes.miniaturepets.pets.PetManager
import com.mikael.rmcosmetics.core.CompanionSystem
import com.mikael.rmcosmetics.percentColor
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.game.SoundEffect
import net.eduard.api.lib.kotlin.*
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenEffect
import net.eduard.redemikael.core.soundWhenNoEffect
import net.eduard.redemikael.core.soundWhenSwitchMenu
import net.eduard.redemikael.core.user
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.AsyncPlayerChatEvent

class MenuCompanions : Menu("Companheiros", 6) {

    val editandoPetName = mutableMapOf<Player, Pet>()

    @EventHandler
    fun chatCancelMessage(e: AsyncPlayerChatEvent) {
        val message = e.message
        val player = e.player

        if (message == "rmcosmetics:cancelchangename") {
            e.isCancelled = !editandoPetName.containsKey(player)
        }
    }

    @EventHandler
    fun changePetName(e: AsyncPlayerChatEvent) {
        val newname = e.message.colored()
        val player = e.player

        if (player in editandoPetName) {
            if (e.message != "rmcosmetics:cancelchangename") {
                if (e.message.length <= 32) {
                    e.isCancelled = true
                    val pet = editandoPetName[player]!!
                    player.playSound(player.location, Sound.LEVEL_UP, 2f, 1f)
                    player.sendMessage("§aVocê alterou o nome do seu companheiro para '§f${newname}§a'.")
                    editandoPetName.remove(player)
                    open(player)
                    pet.customName = newname
                    CompanionSystem.setCustomName(player, newname)
                } else {
                    e.isCancelled = true
                    editandoPetName.remove(player)
                    player.playSound(player.location, Sound.VILLAGER_NO, 2f, 1f)
                    player.sendMessage("§cO nome do seu companheiro não pode conter mais que 32 caracteres.")
                }
            } else {
                e.isCancelled = true
                editandoPetName.remove(player)
                player.playSound(player.location, Sound.VILLAGER_NO, 2f, 1f)
                player.sendMessage("§cMudança de apelido do companheiro cancelada.")
            }
        }

    }

    companion object {
        lateinit var instance: MenuCompanions
    }

    init {
        instance = this
        isAutoAlignItems = true
        openWithCommand = "/companions"

        for (pet in PetLoader.getPets()) {
            button {

                iconPerPlayer = {
                    val item = ItemBuilder(pet.symbol).name("§a" + pet.name)
                    val user = player.user

                    val companion = pet
                    val companionprecogold = CompanionSystem.precoemgold[companion]!!
                    val companionprecocash = CompanionSystem.precoemcash[companion]!!
                    val companioncompravel = CompanionSystem.compravel[companion]!!
                    val companionporgold = CompanionSystem.compravelporgold[companion]!!
                    val companionporcash = CompanionSystem.compravelporcash[companion]!!
                    val companionGroup = CompanionSystem.groupPermission[companion]!!
                    val exclusiveGroupName = CompanionSystem.exclusiveGroupName[companion]!!

                    if (hasPermission(companionGroup)) {
                        if (hasPermission(pet.permission)) {

                            if (PetManager.hasPet(player)) {
                                val companionUsed = PetManager.getPet(player).container
                                if (companionUsed == pet) {
                                    val loreUsada = mutableListOf<String>()

                                    loreUsada.add("§8Companheiro")
                                    loreUsada.add("")
                                    loreUsada.addAll(pet.description)
                                    loreUsada.add("")
                                    loreUsada.add("§eClique para remover!")
                                    item.lore(loreUsada)
                                } else {
                                    val loreUsada = mutableListOf<String>()

                                    loreUsada.add("§8Companheiro")
                                    loreUsada.add("")
                                    loreUsada.addAll(pet.description)
                                    loreUsada.add("")
                                    loreUsada.add("§eClique para utilizar!")
                                    item.lore(loreUsada)
                                }
                            } else {
                                val loreUsada = mutableListOf<String>()

                                loreUsada.add("§8Companheiro")
                                loreUsada.add("")
                                loreUsada.addAll(pet.description)
                                loreUsada.add("")
                                loreUsada.add("§eClique para utilizar!")
                                item.lore(loreUsada)
                            }
                        } else {
                            val loreUsada = mutableListOf<String>()

                            loreUsada.add("§8Companheiro")
                            loreUsada.add("")
                            loreUsada.addAll(pet.description)
                            loreUsada.add("")
                            if (companioncompravel) {
                                loreUsada.add("§eOpções de compra:")
                            }
                            if (companionporgold) {
                                loreUsada.add(" §8▪ §fComprar com Gold: §6${companionprecogold.format(false)} Gold")
                            }
                            if (companionporcash) {
                                loreUsada.add(" §8▪ §fComprar com Cash: §b${companionprecocash.format(false)} Cash")
                            }
                            if (companioncompravel) {
                                loreUsada.add("")
                            }
                            if (companioncompravel) {
                                loreUsada.add(
                                    if (user.gold >= companionprecogold || user.cash >= companionprecocash)
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

                        loreUsada.add("§8Companheiro")
                        loreUsada.add("")
                        loreUsada.addAll(pet.description)
                        loreUsada.add("")
                        loreUsada.add(exclusiveGroupName)
                        item.lore(loreUsada)
                    }
                    item
                }
                click = ClickEffect {
                    val player = it.player
                    val user = player.user

                    val companion = pet
                    val companionemgold = CompanionSystem.precoemgold[companion]!!
                    val companionemcash = CompanionSystem.precoemcash[companion]!!
                    val companionGroup = CompanionSystem.groupPermission[companion]!!
                    val exclusiveGroupName = CompanionSystem.exclusiveGroupName[companion]!!

                    if (player.hasPermission(companionGroup)) {
                        if (player.hasPermission(pet.permission)) {

                            if (PetManager.hasPet(player)) {
                                val companionUsed = PetManager.getPet(player).container
                                if (companionUsed == pet) {
                                    player.soundWhenEffect()
                                    player.sendMessage("§cVocê removeu o companheiro ${pet.name}.")
                                    PetManager.getPet(player).remove()
                                    CompanionSystem.deselect(player)
                                    open(player, getPageOpen(player))
                                } else {
                                    player.soundWhenEffect()
                                    player.sendMessage("§aVocê selecionou o companheiro ${pet.name}.")
                                    pet.spawnPet(player)
                                    val companionSelected = PetManager.getPet(player)
                                    CompanionSystem.select(player, companionSelected)

                                    val companionUsed = CompanionSystem.getSelectedCompanion(player)
                                    if (CompanionSystem.hasName(player)) {
                                        companionUsed.customName = CompanionSystem.getCustomName(player)
                                    } else {
                                        companionUsed.customName = "§6${companionUsed.container.name} §7de ${user.nick}"
                                    }
                                    open(player, getPageOpen(player))
                                }
                            } else {
                                player.soundWhenEffect()
                                player.sendMessage("§aVocê selecionou o companheiro ${pet.name}.")
                                pet.spawnPet(player)
                                val companionSelected = PetManager.getPet(player)
                                CompanionSystem.select(player, companionSelected)

                                val companionUsed = CompanionSystem.getSelectedCompanion(player)
                                if (CompanionSystem.hasName(player)) {
                                    companionUsed.customName = CompanionSystem.getCustomName(player)
                                } else {
                                    companionUsed.customName = "§6${companionUsed.container.name} §7de ${user.nick}"
                                }
                                open(player, getPageOpen(player))
                            }
                        } else {
                            if (CompanionSystem.compravel[pet] == true) {
                                if (user.gold >= companionemgold || user.cash >= companionemcash) {
                                    player.soundWhenSwitchMenu()
                                    MenuSelectCoinTypeCompanion.instance
                                        .comprando[player] = companion

                                    MenuSelectCoinTypeCompanion.instance.open(player)
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

        button("remove-companion") {
            setPosition(4, 6)
            fixed = true
            iconPerPlayer = {
                ItemBuilder(Material.BARRIER)
                    .name("§cRemover Companheiro")
                    .lore("§7Remove seu companheiro atual.")
            }
            click = ClickEffect {
                val player = it.player

                player.soundWhenEffect()
                if (PetManager.hasPet(player)) {
                    PetManager.getPet(player).remove()
                    CompanionSystem.deselect(player)
                    player.sendMessage("§cSeu companheiro atual foi removido.")
                    open(player, getPageOpen(player))
                } else {
                    player.sendMessage("§cVocê não possui um companheiro selecionado.")
                }
            }
        }
        button("info") {
            setPosition(6, 6)

            fixed = true
            iconPerPlayer = {
                val player = this
                val companionsDesbloqueados = PetLoader.getPets().count {
                    hasPermission(it.permission)
                }

                var porcentagemDesbloqueada = companionsDesbloqueados.toDouble() / PetLoader.getPets().size
                var porcentagemTexto = porcentagemDesbloqueada.percent() + "%"
                val corNumero = porcentagemDesbloqueada.percentColor()

                val item = ItemBuilder(Material.PAPER).name("§aInformações")
                if (PetManager.hasPet(player)) {
                    item.lore(
                        "§8Companheiros",
                        "",
                        "§7Você pode encontrar novos companheiros",
                        "§7em §bCaixas Misteriosas §7ou comprá-los",
                        "§7utilizando §6Gold §7e §bCash§7.",
                        "",
                        "§fDesbloqueados: ${corNumero}${companionsDesbloqueados}/${PetLoader.getPets().size} §8(${porcentagemTexto})",
                        "§fSelecionado atualmente:",
                        "§a▸ ${PetManager.getPet(player).container.name}"
                    )
                } else {
                    item.lore(
                        "§8Companheiros",
                        "",
                        "§7Você pode encontrar novos companheiros",
                        "§7em §bCaixas Misteriosas §7ou comprá-los",
                        "§7utilizando §6Gold §7e §bCash§7.",
                        "",
                        "§fDesbloqueados: ${corNumero}${companionsDesbloqueados}/${PetLoader.getPets().size} §8(${porcentagemTexto})",
                        "§fSelecionado atualmente:",
                        "§a▸ Nenhum"
                    )
                }
            }
            click = ClickEffect {
                it.player.soundWhenNoEffect()
            }
        }
        button("companion-name") {
            setPosition(9, 1)

            fixed = true
            iconPerPlayer = {
                val item = ItemBuilder(Material.NAME_TAG)

                if (PetManager.hasPet(player)) {
                    val companion = PetManager.getPet(player)

                    item.name("§aRenomear Companheiro '${companion.container.name}'")
                } else {
                    item.name("§aRenomear Companheiro")
                }

                if (PetManager.hasPet(player)) {
                    if (PetManager.getPet(player).hasCustomName()) {
                        item.lore(
                            "§fNome atual: ${PetManager.getPet(player).customName}",
                            "",
                            "§eClique para renomear!"
                        )
                    } else {
                        item.lore(
                            "§fNome atual: §cNenhum",
                            "",
                            "§eClique para renomear!"
                        )
                    }
                } else {
                    item.lore(
                        "§fNome atual: §cNenhum",
                        "",
                        "§cVocê não possui um companheiro."
                    )
                }
            }
            click = ClickEffect {
                val player = it.player

                if (PetManager.hasPet(player)) {
                    player.soundWhenEffect()
                    val companion = PetManager.getPet(player)

                    editandoPetName[player] = companion

                    player.sendMessage("")
                    player.sendMessage("§aQual será o nome do seu companheiro? §7(${companion.container.name})")
                    val text =
                        net.md_5.bungee.api.chat.TextComponent("§7Responda no chat ou clique §7§lAQUI §7para cancelar.")
                    val clickEvent = ClickEvent(
                        ClickEvent.Action.RUN_COMMAND,
                        "rmcosmetics:cancelchangename"
                    )
                    text.clickEvent = clickEvent

                    val texts = net.md_5.bungee.api.chat.ComponentBuilder("§fClique para cancelar!")

                    val hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, texts.create())
                    text.hoverEvent = hoverEvent
                    player.spigot().sendMessage(text)

                    // SpigotAPI.sendMessage(player,"","","comandinho")
                    player.sendMessage("")
                    player.closeInventory()
                } else {
                    player.soundWhenNoEffect()
                }
            }
        }

        backPage.item = ItemBuilder(Material.INK_SACK).data(1)
            .name("§cVoltar")
            .lore("§7Para Cosméticos.")
        backPage.setPosition(5, 6)
        backPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

    }
}