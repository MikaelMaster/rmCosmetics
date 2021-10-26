package com.mikael.rmcosmetics.listener

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.*
import com.mikael.rmcosmetics.menu.MenuParticles
import com.mikael.rmcosmetics.objects.ClosetData
import com.mikael.rmcosmetics.objects.CompanionData
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.EventsManager
import net.eduard.redemikael.core.api.miftCore
import net.eduard.redemikael.core.spigot.CoreMain
import net.eduard.redemikael.core.user
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class CosmeticLoaderListener : EventsManager() {

    /**
     * Listener responsável por carregar cosméticos dos jogadores
     * e aplicar os mesmos no join-- bem como remover
     * cosméticos no quit.
     */

    // Carrega e spawna o mascote de um jogador no join
    @EventHandler(priority = EventPriority.HIGHEST)
    fun loadPlayerPetOnJoin(e: PlayerJoinEvent) {
        MiftCosmetics.instance.asyncTask {
            try {
                val player = e.player
                PetSystem.load(player)
                if (!PetSystem.hasSelected(player)) return@asyncTask
                val pet = PetSystem.getSelected(player)
                MiftCosmetics.instance.syncTask {
                    PetSystem.createPet(player, pet.type)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    // Carrega e aplica o chapéu de um jogador no join
    @EventHandler(priority = EventPriority.HIGHEST)
    fun loadPlayerHatOnJoin(e: PlayerJoinEvent) {
        MiftCosmetics.instance.asyncDelay(1) {
            try {
                val player = e.player
                HatSystem.load(player)
                if (HatSystem.hasSelected(player)) {
                    val hatUsed = HatSystem.getSelectedHat(player)
                    player.inventory.helmet = ItemBuilder().skin(hatUsed.url)
                        .name("§a${hatUsed.display}")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    // Carrega e aplica o gadget de um jogador no join
    @EventHandler(priority = EventPriority.HIGHEST)
    fun loadPlayerGadgetOnJoin(e: PlayerJoinEvent) {
        MiftCosmetics.instance.asyncDelay(1) {
            try {
                val player = e.player
                GadgetSystem.load(player)
                if (GadgetSystem.hasSelected(player)) {
                    val gadgetUsed = GadgetSystem.getSelectedGadget(player)
                    var slot = 5
                    if (CoreMain.instance.getBoolean("is-minigame-lobby")) {
                        slot = 6
                    }
                    player.inventory.setItem(slot, gadgetUsed.icon)
                }
                GadgetSystem.removeActiveGadget(player)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    // Carrega e ativa a partícula de um jogador no join
    @EventHandler(priority = EventPriority.HIGHEST)
    fun loadPlayerParticleOnJoin(e: PlayerJoinEvent) {
        MiftCosmetics.instance.asyncTask {
            try {
                val player = e.player
                ParticleSystem.load(player)
                if (ParticleSystem.hasSelected(player)) {
                    val particleSelected = ParticleSystem.getSelectedParticle(player)
                    val particle = particleSelected.animationClass.constructors
                        .first().call(player)
                    particle.start()
                    MenuParticles.usingEffect[player] = particle
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    // Carrega e equipa o chapéu animado de um jogador no join
    @EventHandler(priority = EventPriority.HIGHEST)
    fun loadPlayerAnimatedHatOnJoin(e: PlayerJoinEvent) {
        MiftCosmetics.instance.asyncDelay(1) {
            try {
                val player = e.player
                HatAnimatedSystem.load(player)
                if (HatAnimatedSystem.hasSelected(player)) {
                    val hatAnimatedUsed = HatAnimatedSystem.getSelectedAnimatedHat(player)
                    player.equipment.helmet = ItemBuilder().skin(hatAnimatedUsed.urls.keys.first())
                        .name("§a${hatAnimatedUsed.display}")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    // Carrega e spawna o companheiro de um jogador no join
    @EventHandler(priority = EventPriority.HIGHEST)
    fun loadPlayerCompanionOnJoin(e: PlayerJoinEvent) {
        MiftCosmetics.instance.asyncTask {
            try {
                val player = e.player
                val user = player.user
                val companionSelected = miftCore.sqlManager.getDataOf<CompanionData>(user) ?: return@asyncTask
                CompanionSystem.companionsSelected[user] = companionSelected
                val companionUsed = companionSelected.companion ?: return@asyncTask
                val petContainer = CompanionSystem.petsByName[companionUsed.toLowerCase()]!!
                MiftCosmetics.instance.syncTask {
                    val pet = petContainer.spawnPet(player)
                    if (CompanionSystem.hasName(player)) {
                        pet.customName = CompanionSystem.getCustomName(player)
                    } else {
                        pet.customName = "§6${petContainer.name} §7de ${user.nick}"
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    // Carrega e equipa o banner de um jogador no join
    @EventHandler(priority = EventPriority.HIGHEST)
    fun loadPlayerBannerOnJoin(e: PlayerJoinEvent) {
        MiftCosmetics.instance.asyncDelay(1) {
            try {
                val player = e.player
                BannerSystem.load(player)
                if (BannerSystem.hasSelected(player)) {
                    val bannerUsed = BannerSystem.getSelectedBanner(player)
                    player.inventory.helmet = bannerUsed.icon()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    // Carrega e aplica as peças do closet de um player no join
    @EventHandler(priority = EventPriority.HIGHEST)
    fun loadPlayerClosetOnJoin(e: PlayerJoinEvent) {
        MiftCosmetics.instance.asyncDelay(1) {
            try {
                val player = e.player
                val user = player.user
                val closetLoaded = miftCore.sqlManager.getDataOf<ClosetData>(user) ?: return@asyncDelay
                ClosetSystem.closets[user] = closetLoaded
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
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    // Remove a partícula ativa de um player no quit para a
    // mesma parar de ser spawnada no mapa
    @EventHandler(priority = EventPriority.HIGHEST)
    fun removePlayerParticleOnQuit(e: PlayerQuitEvent) {
        try {
            val player = e.player
            if (ParticleSystem.hasSelected(player)) {
                val particleSelected = ParticleSystem.getSelectedParticle(player)
                val particleUsed = particleSelected.animationClass.constructors
                    .first().call(player)
                particleUsed.stop()
                MenuParticles.usingEffect.remove(player)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}