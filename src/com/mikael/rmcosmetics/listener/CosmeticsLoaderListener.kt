package com.mikael.rmcosmetics.listener

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.*
import com.mikael.rmcosmetics.menu.MenuParticles
import net.eduard.api.lib.database.customTypeRegister
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.EventsManager
import net.eduard.redemikael.core.soundWhenNoSuccess
import net.eduard.redemikael.core.spigot.CoreMain
import net.eduard.redemikael.core.testLag
import net.eduard.redemikael.core.user
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class CosmeticsLoaderListener : EventsManager() {
    companion object {
        lateinit var instance: CosmeticsLoaderListener
    }

    init {
        instance = this@CosmeticsLoaderListener
    }

    /**
     * Listener responsável por carregar cosméticos dos jogadores
     * e aplicar os mesmos no join-- bem como remover
     * cosméticos e info da ram no quit.
     */

    @EventHandler(priority = EventPriority.HIGHEST)
    fun loadAllCosmeticsOnJoin(e: PlayerJoinEvent) {
        MiftCosmetics.instance.asyncDelay(1) {
            val player = e.player
            val user = player.user
            testLag("Carregando cosméticos de ${player.name}") {
                try {
                    val data = CosmeticsUtils.loadAllCosmetics(player)

                    // Mascotes (Pets)
                    if (PetSystem.hasSelected(player)) {
                        MiftCosmetics.instance.syncTask {
                            PetSystem.createPet(player, PetSystem.getSelected(player).type)
                        }
                    }

                    // Chapéus
                    if (HatSystem.hasSelected(player)) {
                        val hatUsed = HatSystem.getSelectedHat(player)
                        if (player.hasPermission(hatUsed.permission)) {
                            player.inventory.helmet = ItemBuilder().skin(hatUsed.url)
                                .name("§a${hatUsed.display}")
                        } else {
                            HatSystem.deselect(player)
                        }
                    }

                    // Engenhocas
                    if (GadgetSystem.hasSelected(player)) {
                        val gadgetUsed = GadgetSystem.getSelectedGadget(player)
                        if (player.hasPermission(gadgetUsed.permission)) {
                            var slot = 5
                            if (CoreMain.instance.getBoolean("is-minigame-lobby")) {
                                slot = 6
                            }
                            player.inventory.setItem(slot, gadgetUsed.icon)
                        } else {
                            GadgetSystem.deselect(player)
                        }
                    }
                    GadgetSystem.removeActiveGadget(player)

                    // Partículas
                    if (ParticleSystem.hasSelected(player)) {
                        val particle = ParticleSystem.getSelectedParticle(player)
                        if (player.hasPermission(particle.permission) &&
                            player.hasPermission(particle.groupPermission)
                        ) {
                            val particleEffect = particle.animationClass.constructors.first().call(player)
                            particleEffect.start()
                            MenuParticles.usingEffect[player] = particleEffect
                        } else {
                            ParticleSystem.deselect(player)
                        }
                    }

                    // Chapéis Animados
                    if (HatAnimatedSystem.hasSelected(player)) {
                        val hatAnimatedUsed = HatAnimatedSystem.getSelectedAnimatedHat(player)
                        if (player.hasPermission(hatAnimatedUsed.permission) &&
                            player.hasPermission(hatAnimatedUsed.groupPermission)
                        ) {
                            player.equipment.helmet = ItemBuilder().skin(hatAnimatedUsed.urls.keys.first())
                                .name("§a${hatAnimatedUsed.display}")
                        } else {
                            HatAnimatedSystem.deselect(player)
                        }
                    }

                    // Banners
                    if (BannerSystem.hasSelected(player)) {
                        val bannerSelected = BannerSystem.getSelectedBanner(player)
                        if (player.hasPermission(bannerSelected.permission)) {
                            player.inventory.helmet = bannerSelected.icon()
                        } else {
                            BannerSystem.deselect(player)
                        }
                    }

                    // Companheiros
                    val companionSelected = data?.companion
                    if (companionSelected != null) {
                        val petContainer = CompanionSystem.petsByName[companionSelected.toLowerCase()]!!
                        if (player.hasPermission(petContainer.permission) &&
                            player.hasPermission(CompanionSystem.groupPermission[petContainer])
                        ) {
                            MiftCosmetics.instance.syncTask {
                                CompanionSystem.companionsSelected[user] = data
                                val pet = petContainer.spawnPet(player)
                                if (CompanionSystem.hasName(player)) {
                                    pet.customName = CompanionSystem.getCustomName(player)
                                } else {
                                    pet.customName = "§6${petContainer.name} §7de ${user.nick}"
                                }
                            }
                        } else {
                            CompanionSystem.deselect(player)
                        }
                    }

                    // Todas as peças do Guarda-Roupa (Closet)
                    if (ClosetSystem.closets.containsKey(user) &&
                        !player.hasPermission("rmcore.beneftis.vip")
                    ) {
                        val closet = ClosetSystem.closets[user]!!
                        closet.delete()
                        ClosetSystem.closets.remove(user)
                        return@testLag
                    }
                    if (ClosetSystem.closets.containsKey(user)) {
                        val closet = ClosetSystem.getPlayerCloset(player)
                        if (closet.helmet != null) {
                            player.inventory.helmet = ItemBuilder(closet.helmet).name(closet.helmetName)
                            if (closet.helmetBright) {
                                player.inventory.helmet.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1)
                            }
                        }
                        if (closet.chestplate != null) {
                            player.inventory.chestplate = ItemBuilder(closet.chestplate).name(closet.helmetName)
                            if (closet.chestplateBright) {
                                player.inventory.chestplate.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1)
                            }
                        }
                        if (closet.leggings != null) {
                            player.inventory.leggings = ItemBuilder(closet.leggings).name(closet.leggingsName)
                            if (closet.leggingsBright) {
                                player.inventory.leggings.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1)
                            }
                        }
                        if (closet.boots != null) {
                            player.inventory.boots = ItemBuilder(closet.boots).name(closet.bootsName)
                            if (closet.bootsBright) {
                                player.inventory.boots.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1)
                            }
                        }
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    player.soundWhenNoSuccess()
                    player.sendMessage("§cOcorreu um erro interno ao carregar seus cosméticos.")
                }
            }
        }
    }

    // Remove todos os dados do jogador das listas da ram e remove a partícula ativa do mesmo.
    @EventHandler(priority = EventPriority.HIGHEST)
    fun removePlayerParticleOnQuit(e: PlayerQuitEvent) {
        try {
            val player = e.player
            val user = player.user
            if (ParticleSystem.hasSelected(player)) {
                val particleSelected = ParticleSystem.getSelectedParticle(player)
                val particleUsed = particleSelected.animationClass.constructors
                    .first().call(player)
                particleUsed.stop()
                MenuParticles.usingEffect.remove(player)
            }
            ClosetSystem.closets.remove(user)
            BannerSystem.bannersSelected.remove(user)
            CompanionSystem.companionsSelected.remove(user)
            GadgetSystem.gadgetsSelected.remove(user)
            HatAnimatedSystem.hatsSelected.remove(user)
            HatSystem.hatsSelected.remove(user)
            ParticleSystem.particlesSelected.remove(user)
            PetSystem.petsSelected.remove(user)
            CosmeticsUtils.selecteds.remove(user)
            CosmeticsUtils.loadingCosmetics.remove(player)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}