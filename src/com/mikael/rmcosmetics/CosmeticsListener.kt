package com.mikael.rmcosmetics

import com.kirelcodes.miniaturepets.api.events.pets.PetInteractEvent
import com.kirelcodes.miniaturepets.api.events.pets.PetSpawnEvent
import com.kirelcodes.miniaturepets.pets.PetManager
import com.mikael.rmcosmetics.core.*
import com.mikael.rmcosmetics.core.CompanionSystem.companionsSelected
import com.mikael.rmcosmetics.menu.MenuParticles
import com.mikael.rmcosmetics.objects.ClosetData
import com.mikael.rmcosmetics.objects.CompanionData
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.EventsManager
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.api.miftCore
import net.eduard.redemikael.core.user
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EnderPearl
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.*
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.weather.WeatherChangeEvent
import org.bukkit.scheduler.BukkitRunnable

class CosmeticsListener : EventsManager() {

    @EventHandler
    fun clickcompanheiro(event: PetInteractEvent) {
        if (event.clicker is Player) {
            event.isCancelled = true
            val player = event.clicker as Player
            if (PetManager.hasPet(player)) {
                if (event.pet == PetManager.getPet(player)) {
                    val petemuso = PetManager.getPet(player)
                    petemuso.isRide = true
                } else {
                    event.isCancelled = true
                    val donoDoCompanion = event.pet.owner
                    val userDoDono = donoDoCompanion.user
                    player.playSound(player.location, Sound.VILLAGER_NO, 2f, 1f)
                    player.sendMessage("§aEste é o Companheiro de ${userDoDono.visual}§7.")
                    val text =
                        net.md_5.bungee.api.chat.TextComponent("§aClique §a§lAQUI §apara adquirir o seu!")
                    val clickEvent = ClickEvent(
                        ClickEvent.Action.RUN_COMMAND,
                        "/companions"
                    )
                    text.clickEvent = clickEvent

                    val texts = net.md_5.bungee.api.chat.ComponentBuilder("§fClique para ver os companheiros!")

                    val hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, texts.create())
                    text.hoverEvent = hoverEvent
                    player.spigot().sendMessage(text)
                }
            } else {
                event.isCancelled = true
                val donoDoCompanion = event.pet.owner
                val userDoDono = donoDoCompanion.user
                player.playSound(player.location, Sound.VILLAGER_NO, 2f, 1f)
                player.sendMessage("§aEste é o Companheiro de ${userDoDono.visual}§7.")
                val text =
                    net.md_5.bungee.api.chat.TextComponent("§aClique §a§lAQUI §apara adquirir o seu!")
                val clickEvent = ClickEvent(
                    ClickEvent.Action.RUN_COMMAND,
                    "/companions"
                )
                text.clickEvent = clickEvent

                val texts = net.md_5.bungee.api.chat.ComponentBuilder("§fClique para ver os companheiros!")

                val hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, texts.create())
                text.hoverEvent = hoverEvent
                player.spigot().sendMessage(text)
            }
        }
    }

    @EventHandler
    fun blockStackItem(e: ItemMergeEvent) {
        e.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun removePlayerCar(e: PlayerQuitEvent) {
        val player = e.player
        if (player.isInsideVehicle) {
            player.vehicle.eject()
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun playerjoin(event: PlayerJoinEvent) {
        val player = event.player
        object : BukkitRunnable() {
            override fun run() {
                HatSystem.load(player)
                if (HatSystem.hasSelected(player)) {
                    val hatUsed = HatSystem.getSelectedHat(player)
                    player.inventory.helmet = ItemBuilder().skin(hatUsed.url)
                        .name("§a${hatUsed.display}")
                }
            }
        }.runTaskLaterAsynchronously(MiftCosmetics.instance, 1)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun playerjoin2(event: PlayerJoinEvent) {
        val player = event.player
        object : BukkitRunnable() {
            override fun run() {
                GadgetSystem.load(player)
                if (GadgetSystem.hasSelected(player)) {
                    val gadgetUsed = GadgetSystem.getSelectedGadget(player)
                    player.inventory.setItem(5, gadgetUsed.icon)
                }
                GadgetSystem.removeActiveGadget(player)
            }
        }.runTaskLaterAsynchronously(MiftCosmetics.instance, 1)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun playerjoin3(event: PlayerJoinEvent) {
        val player = event.player
        object : BukkitRunnable() {
            override fun run() {
                ParticleSystem.load(player)
                if (ParticleSystem.hasSelected(player)) {
                    val particleSelected = ParticleSystem.getSelectedParticle(player)
                    val particle = particleSelected.animationClass.constructors
                        .first().call(player)

                    particle.start()
                    MenuParticles.usingEffect[player] = particle

                }
            }
        }.runTaskAsynchronously(MiftCosmetics.instance)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun playerjoin5(event: PlayerJoinEvent) {
        val player = event.player
        object : BukkitRunnable() {
            override fun run() {
                HatAnimatedSystem.load(player)
                if (HatAnimatedSystem.hasSelected(player)) {
                    val hatAnimatedUsed = HatAnimatedSystem.getSelectedAnimatedHat(player)
                    player.equipment.helmet = ItemBuilder().skin(hatAnimatedUsed.urls.keys.first())
                        .name("§a${hatAnimatedUsed.display}")
                }
            }
        }.runTaskLaterAsynchronously(MiftCosmetics.instance, 1)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun playerjoin6(event: PlayerJoinEvent) {
        val player = event.player
        object : BukkitRunnable() {
            override fun run() {
                val user = player.user
                val companionSelected = miftCore.sqlManager.getDataOf<CompanionData>(user) ?: return
                companionsSelected[user] = companionSelected

                val companionUsed = companionSelected.companion ?: return
                val petContainer = CompanionSystem.petsByName[companionUsed.toLowerCase()]!!

                object : BukkitRunnable() {
                    override fun run() {

                        val pet = petContainer.spawnPet(player)

                        if (CompanionSystem.hasName(player)) {
                            pet.customName = CompanionSystem.getCustomName(player)
                        } else {
                            pet.customName = "§6${petContainer.name} §7de ${user.nick}"
                        }
                    }
                }.runTask(MiftCosmetics.instance)

            }
        }.runTaskAsynchronously(MiftCosmetics.instance)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun playerjoin7(event: PlayerJoinEvent) {
        val player = event.player
        object : BukkitRunnable() {
            override fun run() {
                BannerSystem.load(player)
                if (BannerSystem.hasSelected(player)) {
                    val bannerUsed = BannerSystem.getSelectedBanner(player)
                    player.inventory.helmet = BannerSystem.getSelectedBanner(player).icon()
                }
            }
        }.runTaskLaterAsynchronously(MiftCosmetics.instance, 1)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun playerjoin9(event: PlayerJoinEvent) {
        val player = event.player
        val user = player.user

        object : BukkitRunnable() {
            override fun run() {
                val closetLoaded = miftCore.sqlManager.getDataOf<ClosetData>(user) ?: return
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
            }
        }.runTaskLaterAsynchronously(MiftCosmetics.instance, 1)
    }

    @EventHandler
    fun removeparticle(event: PlayerQuitEvent) {
        val player = event.player
        if (ParticleSystem.hasSelected(player)) {
            val particleSelected = ParticleSystem.getSelectedParticle(player)
            val particleUsed = particleSelected.animationClass.constructors
                .first().call(player)

            particleUsed.stop()
            MenuParticles.usingEffect.remove(player)
        }
    }

    @EventHandler
    fun nocompanionspawnmessage(event: PetSpawnEvent) {
        event.setShouldSendSpawnMessage(false)
    }

    @EventHandler
    fun bloquearmobdano(event: EntityDamageEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun bloquearpocao(event: PotionSplashEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun removerChuva(event: WeatherChangeEvent) {
        if (event.toWeatherState()) {
            event.isCancelled = true
        }
    }
}