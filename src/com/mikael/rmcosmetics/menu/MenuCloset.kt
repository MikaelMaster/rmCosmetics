package com.mikael.rmcosmetics.menu

import com.mikael.rmcosmetics.core.ClosetSystem
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.kotlin.name
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenEffect
import net.eduard.redemikael.core.soundWhenNoEffect
import net.eduard.redemikael.core.soundWhenSwitchMenu
import net.eduard.redemikael.core.user
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment

class MenuCloset : Menu("Guarda-Roupa", 6) {

    companion object {
        lateinit var instance: MenuCloset
    }

    init {
        instance = this

        openWithCommand = "/closet"
        openNeedPermission = "rmcosmetics.use.closet"
        messagePermission = "§cVocê precisa do Grupo §aVIP §cou superior para utilizar o Guarda-Roupa!"

        button("turn-bright-helmet") {
            setPosition(9, 2)

            iconPerPlayer = {
                ItemBuilder(Material.EXP_BOTTLE)
                    .name("§aAtivar/Desativar Brilho §f(Capacete)")
                    .lore(
                        "§7Irá fazer seu capacete equipado",
                        "§7brilhar quando ativado.",
                        "",
                        if (player.equipment.helmet == null) "§fEstado atual: §cDesativado" else if (player.equipment.helmet
                                .containsEnchantment(Enchantment.PROTECTION_PROJECTILE)
                        ) "§fEstado atual: §aAtivado" else "§fEstado atual: §cDesativado",
                        "",
                        if (player.equipment.helmet == null) "§cVocê não possui um capacete equipado." else if (player.equipment.helmet
                                .containsEnchantment(Enchantment.PROTECTION_PROJECTILE)
                        ) "§eClique para desativar!" else "§eClique para ativar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                if (player.equipment.helmet != null) {

                    if (player.equipment.helmet.containsEnchantment(Enchantment.PROTECTION_PROJECTILE)) {
                        player.soundWhenEffect()
                        player.equipment.helmet.removeEnchantment(Enchantment.PROTECTION_PROJECTILE)
                        closet.helmetBright = false
                        closet.updateQueue()
                        open(player)
                    } else {
                        player.soundWhenEffect()
                        player.equipment.helmet.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1)
                        closet.helmetBright = true
                        closet.updateQueue()
                        open(player)
                    }
                } else {
                    player.soundWhenNoEffect()
                }
            }
        }

        button("turn-bright-chestplate") {
            setPosition(9, 3)

            iconPerPlayer = {
                ItemBuilder(Material.EXP_BOTTLE)
                    .name("§aAtivar/Desativar Brilho §f(Peitoral)")
                    .lore(
                        "§7Irá fazer seu peitoral equipado",
                        "§7brilhar quando ativado.",
                        "",
                        if (player.equipment.chestplate == null) "§fEstado atual: §cDesativado" else if (player.equipment.chestplate
                                .containsEnchantment(Enchantment.PROTECTION_PROJECTILE)
                        ) "§fEstado atual: §aAtivado" else "§fEstado atual: §cDesativado",
                        "",
                        if (player.equipment.chestplate == null) "§cVocê não possui um peitoral equipado." else if (player.equipment.chestplate
                                .containsEnchantment(Enchantment.PROTECTION_PROJECTILE)
                        ) "§eClique para desativar!" else "§eClique para ativar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                if (player.equipment.chestplate != null) {

                    if (player.equipment.chestplate.containsEnchantment(Enchantment.PROTECTION_PROJECTILE)) {
                        player.soundWhenEffect()
                        player.equipment.chestplate.removeEnchantment(Enchantment.PROTECTION_PROJECTILE)
                        closet.chestplateBright = false
                        closet.updateQueue()
                        open(player)
                    } else {
                        player.soundWhenEffect()
                        player.equipment.chestplate.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1)
                        closet.chestplateBright = true
                        closet.updateQueue()
                        open(player)
                    }
                } else {
                    player.soundWhenNoEffect()
                }
            }
        }

        button("turn-bright-leggings") {
            setPosition(9, 4)

            iconPerPlayer = {
                ItemBuilder(Material.EXP_BOTTLE)
                    .name("§aAtivar/Desativar Brilho §f(Calça)")
                    .lore(
                        "§7Irá fazer sua calça equipada",
                        "§7brilhar quando ativado.",
                        "",
                        if (player.equipment.leggings == null) "§fEstado atual: §cDesativado" else if (player.equipment.leggings
                                .containsEnchantment(Enchantment.PROTECTION_PROJECTILE)
                        ) "§fEstado atual: §aAtivado" else "§fEstado atual: §cDesativado",
                        "",
                        if (player.equipment.leggings == null) "§cVocê não possui uma calça equipada." else if (player.equipment.leggings
                                .containsEnchantment(Enchantment.PROTECTION_PROJECTILE)
                        ) "§eClique para desativar!" else "§eClique para ativar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                if (player.equipment.leggings != null) {

                    if (player.equipment.leggings.containsEnchantment(Enchantment.PROTECTION_PROJECTILE)) {
                        player.soundWhenEffect()
                        player.equipment.leggings.removeEnchantment(Enchantment.PROTECTION_PROJECTILE)
                        closet.leggingsBright = false
                        closet.updateQueue()
                        open(player)
                    } else {
                        player.soundWhenEffect()
                        player.equipment.leggings.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1)
                        closet.leggingsBright = true
                        closet.updateQueue()
                        open(player)
                    }
                } else {
                    player.soundWhenNoEffect()
                }
            }
        }

        button("turn-bright-boots") {
            setPosition(9, 5)

            iconPerPlayer = {
                ItemBuilder(Material.EXP_BOTTLE)
                    .name("§aAtivar/Desativar Brilho §f(Botas)")
                    .lore(
                        "§7Irá fazer suas botas equipadas",
                        "§7brilharem quando ativado.",
                        "",
                        if (player.equipment.boots == null) "§fEstado atual: §cDesativado" else if (player.equipment.boots
                                .containsEnchantment(Enchantment.PROTECTION_PROJECTILE)
                        ) "§fEstado atual: §aAtivado" else "§fEstado atual: §cDesativado",
                        "",
                        if (player.equipment.boots == null) "§cVocê não possui uma bota equipada." else if (player.equipment.boots
                                .containsEnchantment(Enchantment.PROTECTION_PROJECTILE)
                        ) "§eClique para desativar!" else "§eClique para ativar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                if (player.equipment.boots != null) {

                    if (player.equipment.boots.containsEnchantment(Enchantment.PROTECTION_PROJECTILE)) {
                        player.soundWhenEffect()
                        player.equipment.boots.removeEnchantment(Enchantment.PROTECTION_PROJECTILE)
                        closet.bootsBright = false
                        closet.updateQueue()
                        open(player)
                    } else {
                        player.soundWhenEffect()
                        player.equipment.boots.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1)
                        closet.bootsBright = true
                        closet.updateQueue()
                        open(player)
                    }
                } else {
                    player.soundWhenNoEffect()
                }
            }
        }

        button("back") {
            setPosition(5, 6)

            iconPerPlayer = {
                ItemBuilder(Material.INK_SACK).data(1)
                    .name("§cVoltar")
                    .lore("§7Para Cosméticos.")
            }
            click = ClickEffect {
                val player = it.player

                player.soundWhenSwitchMenu()
                MenuCosmetics.instance.open(player)
            }
        }

        button("remove-all-armor") {
            setPosition(4, 6)

            iconPerPlayer = {
                ItemBuilder(Material.BARRIER)
                    .name("§cRemover Armadura")
                    .lore(
                        "§7Remove toda a sua armadura.",
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                player.soundWhenEffect()
                player.sendMessage("§cToda a sua armadura foi removida.")

                if (closet.helmet != null) {
                    player.inventory.helmet = null
                    closet.helmet = null
                    closet.helmetName = null
                    closet.helmetBright = false
                }
                if (closet.chestplate != null) {
                    player.inventory.chestplate = null
                    closet.chestplate = null
                    closet.chestplateName = null
                    closet.chestplateBright = false
                }
                if (closet.leggings != null) {
                    player.inventory.leggings = null
                    closet.leggings = null
                    closet.leggingsName = null
                    closet.leggingsBright = false
                }
                if (closet.boots != null) {
                    player.inventory.boots = null
                    closet.boots = null
                    closet.bootsName = null
                    closet.bootsBright = false
                }
                closet.updateQueue()
                open(player)
            }
        }

        button("info") {
            setPosition(6, 6)

            iconPerPlayer = {
                val user = player.user
                val closet = ClosetSystem.getPlayerCloset(player)

                var helmet = "§a▸ Vazio"
                if (closet.helmet != null) {
                    helmet = "§a▸ ${closet.helmetName} §c(Brilho inativo)"
                }
                if (closet.helmetBright) {
                    helmet = "§a▸ ${closet.helmetName} §e(Brilho ativo)"
                }
                var chestplate = "§a▸ Vazio"
                if (closet.chestplate != null) {
                    chestplate = "§a▸ ${closet.chestplateName} §c(Brilho inativo)"
                }
                if (closet.chestplateBright) {
                    chestplate = "§a▸ ${closet.chestplateName} §e(Brilho ativo)"
                }
                var leggings = "§a▸ Vazio"
                if (closet.leggings != null) {
                    leggings = "§a▸ ${closet.leggingsName} §c(Brilho inativo)"
                }
                if (closet.leggingsBright) {
                    leggings = "§a▸ ${closet.leggingsName} §e(Brilho ativo)"
                }
                var boots = "§a▸ Vazio"
                if (closet.boots != null) {
                    boots = "§a▸ ${closet.bootsName} §c(Brilho inativo)"
                }
                if (closet.bootsBright) {
                    boots = "§a▸ ${closet.bootsName} §e(Brilho ativo)"
                }

                ItemBuilder(Material.PAPER)
                    .name("§aInformações")
                    .lore(
                        "§8Guarda-Roupa",
                        "",
                        "§7Você é ${user.tag.name} §7e pode utilizar",
                        "§7o Guarda-Roupa a vontade!",
                        "",
                        "§fCombinação de roupas atual:",
                        helmet,
                        chestplate,
                        leggings,
                        boots
                    )
            }
            click = ClickEffect {
                it.player.soundWhenNoEffect()
            }
        }

        button("leather-helmet") {
            setPosition(3, 2)

            iconPerPlayer = {
                ItemBuilder(Material.LEATHER_HELMET)
                    .name("§aCapacete de Couro")
                    .lore(
                        "§8Roupa",
                        "",
                        if (player.equipment.helmet != null)
                            if (player.equipment.helmet.data.itemType == Material.LEATHER_HELMET) "§eClique para remover!" else "§eClique para equipar!" else "§eClique para equipar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                player.soundWhenEffect()
                if (player.equipment.helmet != null) {
                    if (player.equipment.helmet.data.itemType == Material.LEATHER_HELMET) {
                        player.equipment.helmet = null
                        closet.helmet = null
                        closet.helmetName = null
                        closet.helmetBright = false

                    } else {
                        player.equipment.helmet =
                            ItemBuilder(Material.LEATHER_HELMET).name("§aCapacete de Couro")
                        closet.helmet = Material.LEATHER_HELMET
                        closet.helmetName = "§aCapacete de Couro"
                    }
                } else {
                    player.equipment.helmet =
                        ItemBuilder(Material.LEATHER_HELMET).name("§aCapacete de Couro")
                    closet.helmet = Material.LEATHER_HELMET
                    closet.helmetName = "§aCapacete de Couro"
                }
                closet.updateQueue()
                open(player)
            }
        }

        button("leather-chestplate") {
            setPosition(3, 3)

            iconPerPlayer = {
                ItemBuilder(Material.LEATHER_CHESTPLATE)
                    .name("§aPeitoral de Couro")
                    .lore(
                        "§8Roupa",
                        "",
                        if (player.equipment.chestplate != null)
                            if (player.equipment.chestplate.data.itemType == Material.LEATHER_CHESTPLATE) "§eClique para remover!" else "§eClique para equipar!" else "§eClique para equipar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                player.soundWhenEffect()
                if (player.equipment.chestplate != null) {
                    if (player.equipment.chestplate.data.itemType == Material.LEATHER_CHESTPLATE) {
                        player.equipment.chestplate = null
                        closet.chestplate = null
                        closet.chestplateName = null
                        closet.chestplateBright = false
                    } else {
                        player.equipment.chestplate =
                            ItemBuilder(Material.LEATHER_CHESTPLATE).name("§aPeitoral de Couro")
                        closet.chestplate = Material.LEATHER_CHESTPLATE
                        closet.chestplateName = "§aPeitoral de Couro"
                    }
                } else {
                    player.equipment.chestplate =
                        ItemBuilder(Material.LEATHER_CHESTPLATE).name("§aPeitoral de Couro")
                    closet.chestplate = Material.LEATHER_CHESTPLATE
                    closet.chestplateName = "§aPeitoral de Couro"
                }
                closet.updateQueue()
                open(player)
            }
        }

        button("leather-leggings") {
            setPosition(3, 4)

            iconPerPlayer = {
                ItemBuilder(Material.LEATHER_LEGGINGS)
                    .name("§aCalça de Couro")
                    .lore(
                        "§8Roupa",
                        "",
                        if (player.equipment.leggings != null)
                            if (player.equipment.leggings.data.itemType == Material.LEATHER_LEGGINGS) "§eClique para remover!" else "§eClique para equipar!" else "§eClique para equipar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                player.soundWhenEffect()
                if (player.equipment.leggings != null) {
                    if (player.equipment.leggings.data.itemType == Material.LEATHER_LEGGINGS) {
                        player.equipment.leggings = null
                        closet.leggings = null
                        closet.leggingsName = null
                        closet.leggingsBright = false
                    } else {
                        player.equipment.leggings =
                            ItemBuilder(Material.LEATHER_LEGGINGS).name("§aCalça de Couro")
                        closet.leggings = Material.LEATHER_LEGGINGS
                        closet.leggingsName = "§aCalça de Couro"
                    }
                } else {
                    player.equipment.leggings =
                        ItemBuilder(Material.LEATHER_LEGGINGS).name("§aCalça de Couro")
                    closet.leggings = Material.LEATHER_LEGGINGS
                    closet.leggingsName = "§aCalça de Couro"
                }
                closet.updateQueue()
                open(player)
            }
        }

        button("leather-boots") {
            setPosition(3, 5)

            iconPerPlayer = {
                ItemBuilder(Material.LEATHER_BOOTS)
                    .name("§aBotas de Couro")
                    .lore(
                        "§8Roupa",
                        "",
                        if (player.equipment.boots != null)
                            if (player.equipment.boots.data.itemType == Material.LEATHER_BOOTS) "§eClique para remover!" else "§eClique para equipar!" else "§eClique para equipar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                player.soundWhenEffect()
                if (player.equipment.boots != null) {
                    if (player.equipment.boots.data.itemType == Material.LEATHER_BOOTS) {
                        player.equipment.boots = null
                        closet.boots = null
                        closet.bootsName = null
                        closet.bootsBright = false
                    } else {
                        player.equipment.boots =
                            ItemBuilder(Material.LEATHER_BOOTS).name("§aBotas de Couro")
                        closet.boots = Material.LEATHER_BOOTS
                        closet.bootsName = "§aBotas de Couro"
                    }
                } else {
                    player.equipment.boots =
                        ItemBuilder(Material.LEATHER_BOOTS).name("§aBotas de Couro")
                    closet.boots = Material.LEATHER_BOOTS
                    closet.bootsName = "§aBotas de Couro"
                }
                closet.updateQueue()
                open(player)
            }
        }

        button("iron-helmet") {
            setPosition(4, 2)

            iconPerPlayer = {
                ItemBuilder(Material.IRON_HELMET)
                    .name("§aCapacete de Ferro")
                    .lore(
                        "§8Roupa",
                        "",
                        if (player.equipment.helmet != null)
                            if (player.equipment.helmet.data.itemType == Material.IRON_HELMET) "§eClique para remover!" else "§eClique para equipar!" else "§eClique para equipar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                player.soundWhenEffect()
                if (player.equipment.helmet != null) {
                    if (player.equipment.helmet.data.itemType == Material.IRON_HELMET) {
                        player.equipment.helmet = null
                        closet.helmet = null
                        closet.helmetName = null
                        closet.helmetBright = false
                    } else {
                        player.equipment.helmet =
                            ItemBuilder(Material.IRON_HELMET).name("§aCapacete de Ferro")
                        closet.helmet = Material.IRON_HELMET
                        closet.helmetName = "§aCapacete de Ferro"
                    }
                } else {
                    player.equipment.helmet =
                        ItemBuilder(Material.IRON_HELMET).name("§aCapacete de Ferro")
                    closet.helmet = Material.IRON_HELMET
                    closet.helmetName = "§aCapacete de Ferro"
                }
                closet.updateQueue()
                open(player)
            }
        }

        button("iron-chestplate") {
            setPosition(4, 3)

            iconPerPlayer = {
                ItemBuilder(Material.IRON_CHESTPLATE)
                    .name("§aPeitoral de Ferro")
                    .lore(
                        "§8Roupa",
                        "",
                        if (player.equipment.chestplate != null)
                            if (player.equipment.chestplate.data.itemType == Material.IRON_CHESTPLATE) "§eClique para remover!" else "§eClique para equipar!" else "§eClique para equipar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                player.soundWhenEffect()
                if (player.equipment.chestplate != null) {
                    if (player.equipment.chestplate.data.itemType == Material.IRON_CHESTPLATE) {
                        player.equipment.chestplate = null
                        closet.chestplate = null
                        closet.chestplateName = null
                        closet.chestplateBright = false
                    } else {
                        player.equipment.chestplate =
                            ItemBuilder(Material.IRON_CHESTPLATE).name("§aPeitoral de Ferro")
                        closet.chestplate = Material.IRON_CHESTPLATE
                        closet.chestplateName = "§aPeitoral de Ferro"
                    }
                } else {
                    player.equipment.chestplate =
                        ItemBuilder(Material.IRON_CHESTPLATE).name("§aPeitoral de Ferro")
                    closet.chestplate = Material.IRON_CHESTPLATE
                    closet.chestplateName = "§aPeitoral de Ferro"
                }
                closet.updateQueue()
                open(player)
            }
        }

        button("iron-leggings") {
            setPosition(4, 4)

            iconPerPlayer = {
                ItemBuilder(Material.IRON_LEGGINGS)
                    .name("§aCalça de Ferro")
                    .lore(
                        "§8Roupa",
                        "",
                        if (player.equipment.leggings != null)
                            if (player.equipment.leggings.data.itemType == Material.IRON_LEGGINGS) "§eClique para remover!" else "§eClique para equipar!" else "§eClique para equipar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                player.soundWhenEffect()
                if (player.equipment.leggings != null) {
                    if (player.equipment.leggings.data.itemType == Material.IRON_LEGGINGS) {
                        player.equipment.leggings = null
                        closet.leggings = null
                        closet.leggingsName = null
                        closet.leggingsBright = false
                    } else {
                        player.equipment.leggings =
                            ItemBuilder(Material.IRON_LEGGINGS).name("§aCalça de Ferro")
                        closet.leggings = Material.IRON_LEGGINGS
                        closet.leggingsName = "§aCalça de Ferro"
                    }
                } else {
                    player.equipment.leggings =
                        ItemBuilder(Material.IRON_LEGGINGS).name("§aCalça de Ferro")
                    closet.leggings = Material.IRON_LEGGINGS
                    closet.leggingsName = "§aCalça de Ferro"
                }
                closet.updateQueue()
                open(player)
            }
        }

        button("iron-boots") {
            setPosition(4, 5)

            iconPerPlayer = {
                ItemBuilder(Material.IRON_BOOTS)
                    .name("§aBotas de Ferro")
                    .lore(
                        "§8Roupa",
                        "",
                        if (player.equipment.boots != null)
                            if (player.equipment.boots.data.itemType == Material.IRON_BOOTS) "§eClique para remover!" else "§eClique para equipar!" else "§eClique para equipar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                player.soundWhenEffect()
                if (player.equipment.boots != null) {
                    if (player.equipment.boots.data.itemType == Material.IRON_BOOTS) {
                        player.equipment.boots = null
                        closet.boots = null
                        closet.bootsName = null
                        closet.bootsBright = false
                    } else {
                        player.equipment.boots =
                            ItemBuilder(Material.IRON_BOOTS).name("§aBotas de Ferro")
                        closet.boots = Material.IRON_BOOTS
                        closet.bootsName = "§aBotas de Ferro"
                    }
                } else {
                    player.equipment.boots =
                        ItemBuilder(Material.IRON_BOOTS).name("§aBotas de Ferro")
                    closet.boots = Material.IRON_BOOTS
                    closet.bootsName = "§aBotas de Ferro"
                }
                closet.updateQueue()
                open(player)
            }
        }

        button("gold-helmet") {
            setPosition(6, 2)

            iconPerPlayer = {
                ItemBuilder(Material.GOLD_HELMET)
                    .name("§aCapacete de ouro")
                    .lore(
                        "§8Roupa",
                        "",
                        if (player.equipment.helmet != null)
                            if (player.equipment.helmet.data.itemType == Material.GOLD_HELMET) "§eClique para remover!" else "§eClique para equipar!" else "§eClique para equipar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                player.soundWhenEffect()
                if (player.equipment.helmet != null) {
                    if (player.equipment.helmet.data.itemType == Material.GOLD_HELMET) {
                        player.equipment.helmet = null
                        closet.helmet = null
                        closet.helmetName = null
                        closet.helmetBright = false
                    } else {
                        player.equipment.helmet =
                            ItemBuilder(Material.GOLD_HELMET).name("§aCapacete de Ouro")
                        closet.helmet = Material.GOLD_HELMET
                        closet.helmetName = "§aCapacete de Ouro"
                    }
                } else {
                    player.equipment.helmet =
                        ItemBuilder(Material.GOLD_HELMET).name("§aCapacete de Ouro")
                    closet.helmet = Material.GOLD_HELMET
                    closet.helmetName = "§aCapacete de Ouro"
                }
                closet.updateQueue()
                open(player)
            }
        }

        button("gold-chestplate") {
            setPosition(6, 3)

            iconPerPlayer = {
                ItemBuilder(Material.GOLD_CHESTPLATE)
                    .name("§aPeitoral de Ouro")
                    .lore(
                        "§8Roupa",
                        "",
                        if (player.equipment.chestplate != null)
                            if (player.equipment.chestplate.data.itemType == Material.GOLD_CHESTPLATE) "§eClique para remover!" else "§eClique para equipar!" else "§eClique para equipar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                player.soundWhenEffect()
                if (player.equipment.chestplate != null) {
                    if (player.equipment.chestplate.data.itemType == Material.GOLD_CHESTPLATE) {
                        player.equipment.chestplate = null
                        closet.chestplate = null
                        closet.chestplateName = null
                        closet.chestplateBright = false
                    } else {
                        player.equipment.chestplate =
                            ItemBuilder(Material.GOLD_CHESTPLATE).name("§aPeitoral de Ouro")
                        closet.chestplate = Material.GOLD_CHESTPLATE
                        closet.chestplateName = "§aPeitoral de Ouro"
                    }
                } else {
                    player.equipment.chestplate =
                        ItemBuilder(Material.GOLD_CHESTPLATE).name("§aPeitoral de Ouro")
                    closet.chestplate = Material.GOLD_CHESTPLATE
                    closet.chestplateName = "§aPeitoral de Ouro"
                }
                closet.updateQueue()
                open(player)
            }
        }

        button("gold-leggings") {
            setPosition(6, 4)

            iconPerPlayer = {
                ItemBuilder(Material.GOLD_LEGGINGS)
                    .name("§aCalça de Ouro")
                    .lore(
                        "§8Roupa",
                        "",
                        if (player.equipment.leggings != null)
                            if (player.equipment.leggings.data.itemType == Material.GOLD_LEGGINGS) "§eClique para remover!" else "§eClique para equipar!" else "§eClique para equipar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                player.soundWhenEffect()
                if (player.equipment.leggings != null) {
                    if (player.equipment.leggings.data.itemType == Material.GOLD_LEGGINGS) {
                        player.equipment.leggings = null
                        closet.leggings = null
                        closet.leggingsName = null
                        closet.leggingsBright = false
                    } else {
                        player.equipment.leggings =
                            ItemBuilder(Material.GOLD_LEGGINGS).name("§aCalça de Ouro")
                        closet.leggings = Material.GOLD_LEGGINGS
                        closet.leggingsName = "§aCalça de Ouro"
                    }
                } else {
                    player.equipment.leggings =
                        ItemBuilder(Material.GOLD_LEGGINGS).name("§aCalça de Ouro")
                    closet.leggings = Material.GOLD_LEGGINGS
                    closet.leggingsName = "§aCalça de Ouro"
                }
                closet.updateQueue()
                open(player)
            }
        }

        button("gold-boots") {
            setPosition(6, 5)

            iconPerPlayer = {
                ItemBuilder(Material.GOLD_BOOTS)
                    .name("§aBotas de Ouro")
                    .lore(
                        "§8Roupa",
                        "",
                        if (player.equipment.boots != null)
                            if (player.equipment.boots.data.itemType == Material.GOLD_BOOTS) "§eClique para remover!" else "§eClique para equipar!" else "§eClique para equipar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                player.soundWhenEffect()
                if (player.equipment.boots != null) {
                    if (player.equipment.boots.data.itemType == Material.GOLD_BOOTS) {
                        player.equipment.boots = null
                        closet.boots = null
                        closet.bootsName = null
                        closet.bootsBright = false
                    } else {
                        player.equipment.boots =
                            ItemBuilder(Material.GOLD_BOOTS).name("§aBotas de Ouro")
                        closet.boots = Material.GOLD_BOOTS
                        closet.bootsName = "§aBotas de Ouro"
                    }
                } else {
                    player.equipment.boots =
                        ItemBuilder(Material.GOLD_BOOTS).name("§aBotas de Ouro")
                    closet.boots = Material.GOLD_BOOTS
                    closet.bootsName = "§aBotas de Ouro"
                }
                closet.updateQueue()
                open(player)
            }
        }

        button("diamond-helmet") {
            setPosition(7, 2)

            iconPerPlayer = {
                ItemBuilder(Material.DIAMOND_HELMET)
                    .name("§aCapacete de Diamante")
                    .lore(
                        "§8Roupa",
                        "",
                        if (player.equipment.helmet != null)
                            if (player.equipment.helmet.data.itemType == Material.DIAMOND_HELMET) "§eClique para remover!" else "§eClique para equipar!" else "§eClique para equipar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                player.soundWhenEffect()
                if (player.equipment.helmet != null) {
                    if (player.equipment.helmet.data.itemType == Material.DIAMOND_HELMET) {
                        player.equipment.helmet = null
                        closet.helmet = null
                        closet.helmetName = null
                        closet.helmetBright = false
                    } else {
                        player.equipment.helmet =
                            ItemBuilder(Material.DIAMOND_HELMET).name("§aCapacete de Diamante")
                        closet.helmet = Material.DIAMOND_HELMET
                        closet.helmetName = "§aCapacete de Diamante"
                    }
                } else {
                    player.equipment.helmet =
                        ItemBuilder(Material.DIAMOND_HELMET).name("§aCapacete de Diamante")
                    closet.helmet = Material.DIAMOND_HELMET
                    closet.helmetName = "§aCapacete de Diamante"
                }
                closet.updateQueue()
                open(player)
            }
        }

        button("diamond-chestplate") {
            setPosition(7, 3)

            iconPerPlayer = {
                ItemBuilder(Material.DIAMOND_CHESTPLATE)
                    .name("§aPeitoral de Diamante")
                    .lore(
                        "§8Roupa",
                        "",
                        if (player.equipment.chestplate != null)
                            if (player.equipment.chestplate.data.itemType == Material.DIAMOND_CHESTPLATE) "§eClique para remover!" else "§eClique para equipar!" else "§eClique para equipar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                player.soundWhenEffect()
                if (player.equipment.chestplate != null) {
                    if (player.equipment.chestplate.data.itemType == Material.DIAMOND_CHESTPLATE) {
                        player.equipment.chestplate = null
                        closet.chestplate = null
                        closet.chestplateName = null
                        closet.chestplateBright = false
                    } else {
                        player.equipment.chestplate =
                            ItemBuilder(Material.DIAMOND_CHESTPLATE).name("§aPeitoral de Diamante")
                        closet.chestplate = Material.DIAMOND_CHESTPLATE
                        closet.chestplateName = "§aPeitoral de Diamante"
                    }
                } else {
                    player.equipment.chestplate =
                        ItemBuilder(Material.DIAMOND_CHESTPLATE).name("§aPeitoral de Diamante")
                    closet.chestplate = Material.DIAMOND_CHESTPLATE
                    closet.chestplateName = "§aPeitoral de Diamante"
                }
                closet.updateQueue()
                open(player)
            }
        }

        button("diamond-leggings") {
            setPosition(7, 4)

            iconPerPlayer = {
                ItemBuilder(Material.DIAMOND_LEGGINGS)
                    .name("§aCalça de Diamante")
                    .lore(
                        "§8Roupa",
                        "",
                        if (player.equipment.leggings != null)
                            if (player.equipment.leggings.data.itemType == Material.DIAMOND_LEGGINGS) "§eClique para remover!" else "§eClique para equipar!" else "§eClique para equipar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                player.soundWhenEffect()
                if (player.equipment.leggings != null) {
                    if (player.equipment.leggings.data.itemType == Material.DIAMOND_LEGGINGS) {
                        player.equipment.leggings = null
                        closet.leggings = null
                        closet.leggingsName = null
                        closet.leggingsBright = false
                    } else {
                        player.equipment.leggings =
                            ItemBuilder(Material.DIAMOND_LEGGINGS).name("§aCalça de Diamante")
                        closet.leggings = Material.DIAMOND_LEGGINGS
                        closet.leggingsName = "§aCalça de Diamante"
                    }
                } else {
                    player.equipment.leggings =
                        ItemBuilder(Material.DIAMOND_LEGGINGS).name("§aCalça de Diamante")
                    closet.leggings = Material.DIAMOND_LEGGINGS
                    closet.leggingsName = "§aCalça de Diamante"
                }
                closet.updateQueue()
                open(player)
            }
        }

        button("diamond-boots") {
            setPosition(7, 5)

            iconPerPlayer = {
                ItemBuilder(Material.DIAMOND_BOOTS)
                    .name("§aBotas de Diamante")
                    .lore(
                        "§8Roupa",
                        "",
                        if (player.equipment.boots != null)
                            if (player.equipment.boots.data.itemType == Material.DIAMOND_BOOTS) "§eClique para remover!" else "§eClique para equipar!" else "§eClique para equipar!"
                    )
            }
            click = ClickEffect {
                val player = it.player
                val closet = ClosetSystem.getPlayerCloset(player)

                player.soundWhenEffect()
                if (player.equipment.boots != null) {
                    if (player.equipment.boots.data.itemType == Material.DIAMOND_BOOTS) {
                        player.equipment.boots = null
                        closet.boots = null
                        closet.bootsName = null
                        closet.bootsBright = false
                    } else {
                        player.equipment.boots =
                            ItemBuilder(Material.DIAMOND_BOOTS).name("§aBotas de Diamante")
                        closet.boots = Material.DIAMOND_BOOTS
                        closet.bootsName = "§aBotas de Diamante"
                    }
                } else {
                    player.equipment.boots =
                        ItemBuilder(Material.DIAMOND_BOOTS).name("§aBotas de Diamante")
                    closet.boots = Material.DIAMOND_BOOTS
                    closet.bootsName = "§aBotas de Diamante"
                }
                closet.updateQueue()
                open(player)
            }
        }
    }
}