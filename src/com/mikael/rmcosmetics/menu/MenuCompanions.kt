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
import net.eduard.redemikael.core.*
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.AsyncPlayerChatEvent

class MenuCompanions : Menu("Companheiros", 6) {
    companion object {
        lateinit var instance: MenuCompanions
    }

    private val editandoPetName = mutableMapOf<Player, Pet>()

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun chatCancelMessage(e: AsyncPlayerChatEvent) {
        val message = e.message
        val player = e.player
        if (message == "rmcosmetics:cancelchangename") {
            e.isCancelled = !editandoPetName.containsKey(player)
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
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
                    player.soundWhenNoSuccess()
                    player.sendMessage("§cO nome do seu companheiro não pode conter mais que 32 caracteres.")
                }
            } else {
                e.isCancelled = true
                editandoPetName.remove(player)
                player.soundWhenNoSuccess()
                player.sendMessage("§cMudança de apelido do companheiro cancelada.")
            }
        }

    }

    init {
        instance = this@MenuCompanions
        isAutoAlignItems = true
        openWithCommand = "/companions"
        openWithCommandText = "/companheiros"
        autoAlignSkipLines = listOf(1, 5, 6)
        autoAlignSkipColumns = listOf(9, 1)
        autoAlignPerLine = 7
        autoAlignPerPage = 3 * autoAlignPerLine
        update()
    }

    override fun update() {
        removeAllButtons()

        for (pet in PetLoader.getPets()) {
            button(pet.name) {
                iconPerPlayer = {
                    val player = this
                    val user = player.user

                    val item = ItemBuilder(pet.symbol).clone().name("§a" + pet.name)
                    val companionprecogold = CompanionSystem.precoemgold[pet]!!
                    val companionprecocash = CompanionSystem.precoemcash[pet]!!
                    val companioncompravel = CompanionSystem.compravel[pet]!!
                    val companionporgold = CompanionSystem.compravelporgold[pet]!!
                    val companionporcash = CompanionSystem.compravelporcash[pet]!!
                    val companionGroup = CompanionSystem.groupPermission[pet]!!
                    val exclusiveGroupName = CompanionSystem.exclusiveGroupName[pet]!!

                    if (PetManager.hasPet(player)) {
                        if (PetManager.getPet(player).container == pet) {
                            item.name("§6${pet.name}")
                            item.glowed()
                        } else if (player.hasPermission(pet.permission)) {
                            item.name("§a${pet.name}")
                        } else {
                            item.name("§c${pet.name}")
                        }
                    } else if (player.hasPermission(pet.permission)) {
                        item.name("§a${pet.name}")
                    } else {
                        item.name("§c${pet.name}")
                    }

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
                            loreUsada.add("§eCaixas contendo este item:")
                            loreUsada.add(" §8▪ §bCaixa Misteriosa de Cosméticos")
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
                                    if (user.gold >= companionprecogold
                                        || user.cash >= companionprecocash
                                    )
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

                    val companionemgold = CompanionSystem.precoemgold[pet]!!
                    val companionemcash = CompanionSystem.precoemcash[pet]!!
                    val companionGroup = CompanionSystem.groupPermission[pet]!!

                    if (player.hasPermission(companionGroup)) {
                        if (player.hasPermission(pet.permission)) {
                            if (PetManager.hasPet(player)) {
                                val companionUsed = PetManager.getPet(player).container
                                if (companionUsed == pet) {
                                    player.soundWhenEffect()
                                    player.sendMessage("§cVocê removeu o companheiro '${pet.name}'.")
                                    PetManager.getPet(player).remove()
                                    CompanionSystem.deselect(player)
                                    open(player, getPageOpen(player))
                                } else {
                                    player.soundWhenEffect()
                                    player.sendMessage("§aVocê selecionou o companheiro '${pet.name}'.")
                                    pet.spawnPet(player)
                                    val companionSelected = PetManager.getPet(player)
                                    CompanionSystem.select(player, companionSelected)
                                    val companionUsedVal = CompanionSystem.getSelectedCompanion(player)
                                    if (CompanionSystem.hasName(player)) {
                                        companionUsedVal.customName = CompanionSystem.getCustomName(player)
                                    } else {
                                        companionUsedVal.customName =
                                            "§6${companionUsedVal.container.name} §7de ${user.nick}"
                                    }
                                    open(player, getPageOpen(player))
                                }
                            } else {
                                player.soundWhenEffect()
                                player.sendMessage("§aVocê selecionou o companheiro '${pet.name}'.")
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
                                        .comprando[player] = pet
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
                val porcentagemDesbloqueada = companionsDesbloqueados.toDouble() / PetLoader.getPets().size
                val porcentagemTexto = porcentagemDesbloqueada.percent() + "%"
                val corNumero = porcentagemDesbloqueada.percentColor()
                var companionUsed = "Nenhum"
                if (PetManager.hasPet(player)) {
                    companionUsed = PetManager.getPet(player).container.name
                }
                val item = ItemBuilder(Material.PAPER).name("§aInformações")
                item.lore(
                    "§8Companheiros",
                    "",
                    "§7Você pode encontrar novos companheiros em",
                    "§bCaixas Misteriosas de Cosméticos §7ou",
                    "§7comprá-los utilizando §6Gold §7e §bCash§7.",
                    "",
                    "§fDesbloqueados: ${corNumero}${companionsDesbloqueados}/${PetLoader.getPets().size} §8(${porcentagemTexto})",
                    "§fSelecionado atualmente:",
                    "§a▸ $companionUsed"
                )
            }
            click = ClickEffect {
                it.player.soundWhenNoEffect()
            }
        }

        button("companion-name") {
            setPosition(9, 1)

            fixed = true
            iconPerPlayer = {
                val player = this
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
                    player.sendMessage("")
                    player.closeInventory()
                } else {
                    player.soundWhenNoEffect()
                }
            }
        }

        backPage.item = ItemBuilder(Material.ARROW)
            .name("§aVoltar")
        backPage.setPosition(5, 6)
        backPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)
    }
}