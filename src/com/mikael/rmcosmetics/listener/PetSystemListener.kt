package com.mikael.rmcosmetics.listener

import com.mikael.rmcosmetics.core.PetSystem
import net.eduard.api.lib.manager.EventsManager
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityTargetEvent
import org.bukkit.event.entity.EntityTeleportEvent
import org.bukkit.event.player.PlayerQuitEvent

class PetSystemListener : EventsManager() {

    /**
     * Listener do sistema de Pets do rmCosmetics
     */

    // Faz com que entidades não setem players
    // como target automaticamente
    @EventHandler(priority = EventPriority.HIGHEST)
    fun noPetsTarget(e: EntityTargetEvent) {
        e.isCancelled = true
    }

    // Faz com que o pet enderman não teleporte
    @EventHandler(priority = EventPriority.HIGHEST)
    fun noPetsTeleport(e: EntityTeleportEvent) {
        if (e.entity.type != EntityType.ENDERMAN) return
        e.isCancelled = true
    }

    // Remove o pet spawnado do jogador no quit
    @EventHandler(priority = EventPriority.HIGHEST)
    fun removePetOnQuit(e: PlayerQuitEvent) {
        val player = e.player
        PetSystem.removePet(player)
    }
}