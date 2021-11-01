package com.mikael.rmcosmetics.listener

import com.kirelcodes.miniaturepets.api.events.pets.PetInteractEvent
import com.kirelcodes.miniaturepets.api.events.pets.PetSpawnEvent
import com.kirelcodes.miniaturepets.pets.PetManager
import com.mikael.rmcosmetics.MiftCosmetics
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.api.lib.manager.EventsManager
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.soundWhenPickup
import net.eduard.redemikael.core.user
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockPhysicsEvent
import org.bukkit.event.entity.*
import org.bukkit.event.player.*
import org.bukkit.event.weather.WeatherChangeEvent
import org.spigotmc.event.entity.EntityDismountEvent

class CosmeticsListener : EventsManager() {

    /**
     * Listener responsável por sistemas gerais do rmCosmetics
     */

    val clickCompanionCooldown = CooldownManager(20).apply { noMessages() }
    @EventHandler(priority = EventPriority.HIGHEST)
    fun clickOnCompanion(e: PetInteractEvent) {
        if (e.clicker is Player) {
            e.isCancelled = true
            val player = e.clicker as Player
            if (clickCompanionCooldown.cooldown(player)) {
                if (PetManager.hasPet(player) && e.pet == PetManager.getPet(player)) {
                    val playerPet = PetManager.getPet(player)
                    playerPet.isRide = true
                    return
                }
                val donoDoCompanion = e.pet.owner
                val userDoDono = donoDoCompanion.user
                player.soundWhenPickup()
                player.sendMessage("§aEste é o Companheiro de ${userDoDono.visual}§7.")
                val text = net.md_5.bungee.api.chat.TextComponent("§aClique §a§lAQUI §apara adquirir o seu!")
                val clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/companions")
                text.clickEvent = clickEvent
                val texts = net.md_5.bungee.api.chat.ComponentBuilder("§fClique para ver os companheiros!")
                val hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, texts.create())
                text.hoverEvent = hoverEvent
                player.spigot().sendMessage(text)
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun petDismount(e: EntityDismountEvent) {
        if (e.entity is Player) {
            val player = e.entity as Player
            if (PetManager.hasPet(player)) {
                val pet = PetManager.getPet(player)
                if (e.dismounted == pet.navigator) {
                    MiftCosmetics.instance.syncDelay(1) {
                        if (pet.isRide) {
                            pet.isRide = false
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun blockStackItem(e: ItemMergeEvent) {
        e.isCancelled = true
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun removePlayerCar(e: PlayerQuitEvent) {
        val player = e.player
        if (player.isInsideVehicle) {
            player.vehicle.eject()
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun noBlockPhisics(e: BlockPhysicsEvent) {
        e.isCancelled = true
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun noSheepCutWool(e: PlayerInteractEntityEvent) {
        if (e.rightClicked.type == EntityType.SHEEP) {
            e.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun noCompanionSpawnMessage(e: PetSpawnEvent) {
        e.setShouldSendSpawnMessage(false)
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun bloqMobPvP(e: EntityDamageEvent) {
        e.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun noPotionSplash(e: PotionSplashEvent) {
        e.isCancelled = true
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun noRain(e: WeatherChangeEvent) {
        if (e.toWeatherState()) {
            e.isCancelled = true
        }
    }
}