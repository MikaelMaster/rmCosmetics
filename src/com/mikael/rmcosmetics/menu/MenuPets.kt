package com.mikael.rmcosmetics.menu

import com.mikael.rmcosmetics.core.PetSystem
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.game.SoundEffect
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenEffect
import net.eduard.redemikael.core.soundWhenNoEffect
import org.bukkit.Material
import org.bukkit.Sound

class MenuPets : Menu("Mascotes", 6) {
    companion object {
        lateinit var instance: MenuPets
    }

    init {
        instance = this@MenuPets
        openWithCommand = "/pets"
        openWithCommandText = "/mascotes"
        isAutoAlignItems = true
        cooldownBetweenInteractions = 0
        autoAlignSkipLines = listOf(1, 5, 6)
        autoAlignSkipColumns = listOf(9, 1)
        autoAlignPerLine = 7
        autoAlignPerPage = 3 * autoAlignPerLine

        for (pet in PetSystem.pets) {
            button(pet.name) {
                iconPerPlayer = {
                    val player = this
                    val item = ItemBuilder().skin(pet.url)

                    var color = "§c"
                    if (player.hasPermission(pet.group) &&
                        player.hasPermission(pet.permission)
                    ) {
                        color = "§a"
                        if (PetSystem.hasSelected(player) &&
                            PetSystem.getSelected(player) == pet
                        ) {
                            color = "§6"
                        }
                    }
                    item.name(color + pet.display)

                    var gender = "um"
                    if (pet.gender)
                        gender = "uma"
                    item.lore(
                        "§8Mascote",
                        "",
                        "§7Desfile pelos nossos lobbies",
                        "§7ao lado de $gender ${pet.display}.",
                        ""
                    )
                    if (player.hasPermission(pet.group)) {
                        if (player.hasPermission(pet.permission)) {
                            if (PetSystem.hasSelected(player) &&
                                PetSystem.getSelected(player) == pet
                            ) {
                                item.addLore("§eClique para remover!")
                            } else {
                                item.addLore("§eClique para utilizar!")
                            }
                        } else {
                            item.addLore("§cVocê não possui esse mascote.")
                        }
                    } else {
                        item.addLore("§cExclusivo.")
                    }
                    item
                }
                click = ClickEffect {
                    val player = it.player
                    if (!player.hasPermission(pet.group)) {
                        player.soundWhenNoEffect()
                        return@ClickEffect
                    }
                    if (!player.hasPermission(pet.permission)) {
                        player.soundWhenNoEffect()
                        return@ClickEffect
                    }
                    if (PetSystem.hasSelected(player) &&
                        PetSystem.getSelected(player) == pet
                    ) {
                        player.sendMessage("§cVocê removeu o mascote '${pet.display}'.")
                        player.soundWhenEffect()
                        PetSystem.removePet(player)
                        PetSystem.deselect(player)
                        open(player, getPageOpen(player))
                        return@ClickEffect
                    }
                    player.sendMessage("§aVocê selecionou o mascote '${pet.display}'.")
                    player.soundWhenEffect()
                    PetSystem.createPet(player, pet.type)
                    PetSystem.select(player, pet)
                    open(player, getPageOpen(player))
                }
            }
        }

        button("remove-pet") {
            setPosition(4, 6)

            fixed = true
            iconPerPlayer = {
                ItemBuilder(Material.BARRIER)
                    .name("§cRemover Mascote")
                    .lore("§7Remove seu mascote atual.")
            }
            click = ClickEffect {
                val player = it.player
                player.soundWhenEffect()
                if (PetSystem.hasSelected(player)) {
                    player.sendMessage("§cSeu mascote atual foi removido.")
                    PetSystem.removePet(player)
                    PetSystem.deselect(player)
                    open(player)
                }
            }
        }

        button("info") {
            setPosition(6, 6)

            fixed = true
            iconPerPlayer = {
                val player = this
                var selectedName = "Nenhum"
                if (PetSystem.hasSelected(player)) {
                    selectedName = PetSystem.getSelected(player).display
                }
                ItemBuilder(Material.PAPER)
                    .name("§aInformações")
                    .lore(
                        "§8Mascotes",
                        "",
                        "§7Você pode encontrar novos mascotes em",
                        "§bCaixas Misteriosas de Cosméticos §7ou",
                        "§7comprá-los utilizando §6Gold §7e §bCash§7.",
                        "",
                        "§fDesbloqueados: §c0/0 §8(0%)",
                        "§fSelecionado atualmente:",
                        "§a▸ $selectedName"
                    )
            }
            click = ClickEffect {
                it.player.soundWhenNoEffect()
            }
        }

        button("rename-pet") {
            setPosition(9, 1)

            fixed = true
            iconPerPlayer = {
                ItemBuilder(Material.NAME_TAG)
                    .name("§aRenomear Mascote")
                    .lore(
                        "§fNome atual: §cNenhum",
                        "",
                        "§cVocê não possui um mascote."
                    )
            }
            click = ClickEffect {
                val player = it.player
                player.soundWhenNoEffect()
            }
        }

        backPage.item = ItemBuilder(Material.ARROW)
            .name("§aVoltar")
        backPage.setPosition(5, 6)
        backPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)
    }
}