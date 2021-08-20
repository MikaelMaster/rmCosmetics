package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.redemikael.core.user
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Egg
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable

class EggRainfallGadget : Gadget(
    "Chuva de Ovos", listOf(
        "§7Já pensou em uma chuva de ovos?",
        "§7Pena que estes não de chocolate..."
    ), ItemBuilder(Material.EGG), 45, "rmcosmetics.gadget.eggrainfall"
) {
    companion object {
        lateinit var instance: EggRainfallGadget
    }

    val listaDeEntityParaRemover = mutableListOf<Entity>()

    val cooldown = CooldownManager(20 * 45)

    init {
        cooldown.msgCooldown = "§cVocê precisa esperar mais %times para utilizar esta engenhoca novamente!"
    }

    @EventHandler
    fun canelareggspawn(event: CreatureSpawnEvent) {
        if (event.entityType == EntityType.CHICKEN && event.spawnReason == CreatureSpawnEvent.SpawnReason.EGG) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun clicando(event: PlayerInteractEvent) {
        val player = event.player
        if (event.item == null) return
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        if (icon != event.item) return
        player.updateInventory()
        if (GadgetSystem.hasActiveGadget(player)) {
            player.sendMessage("§cVocê já possui uma engenhoca ativa no momento!")
            return
        }
        val user = player.user
        if (cooldown.cooldown(player)) {
            player.sendMessage("§aVocê ativou a engenhoca Chuva de Ovos! Duração: §f15s")
            GadgetSystem.putActiveGadget(player)

            val local = event.clickedBlock.location
            val localstand = local.clone().add(1.0, 1.8, 1.0)
            val playerloc1 = event.player.location.add(3.0, 5.5, 0.0)
            val playerloc2 = event.player.location.add(2.0, 5.5, 0.0)
            val playerloc3 = event.player.location.add(1.0, 5.5, 0.0)
            val playerloc4 = event.player.location.add(0.0, 5.5, 0.0)
            val playerloc5 = event.player.location.add(0.0, 5.5, 1.0)
            val playerloc6 = event.player.location.add(0.0, 5.5, 2.0)
            val playerloc7 = event.player.location.add(0.0, 5.5, 3.0)
            val playerloc8 = event.player.location.add(0.0, 5.5, -1.0)
            val playerloc9 = event.player.location.add(0.0, 5.5, -2.0)
            val playerloc10 = event.player.location.add(0.0, 5.5, -3.0)
            val playerloc11 = event.player.location.add(-1.0, 5.5, 0.0)
            val playerloc12 = event.player.location.add(-2.0, 5.5, 0.0)
            val playerloc13 = event.player.location.add(-3.0, 5.5, 0.0)
            val stand = local.world.spawn(localstand, ArmorStand::class.java)
            listaDeEntityParaRemover.add(stand)
            stand.isVisible = false
            stand.setGravity(false)
            stand.customName = "§fChuva de Ovos §7de ${user.nick}"
            stand.isCustomNameVisible = true
            object : BukkitRunnable() {
                var tempo = 60
                override fun run() {
                    local.world.spawn(playerloc1, Egg::class.java)
                    local.world.spawn(playerloc2, Egg::class.java)
                    local.world.spawn(playerloc3, Egg::class.java)
                    local.world.spawn(playerloc4, Egg::class.java)
                    local.world.spawn(playerloc5, Egg::class.java)
                    local.world.spawn(playerloc6, Egg::class.java)
                    local.world.spawn(playerloc7, Egg::class.java)
                    local.world.spawn(playerloc8, Egg::class.java)
                    local.world.spawn(playerloc9, Egg::class.java)
                    local.world.spawn(playerloc10, Egg::class.java)
                    local.world.spawn(playerloc11, Egg::class.java)
                    local.world.spawn(playerloc12, Egg::class.java)
                    local.world.spawn(playerloc13, Egg::class.java)
                    tempo -= 1
                    if (tempo == 0) {
                        player.sendMessage("§cSua Chuva de Ovos foi removida.")
                        GadgetSystem.removeActiveGadget(player)
                        stand.remove()
                        listaDeEntityParaRemover.remove(stand)
                        cancel()
                    }
                }

            }.runTaskTimer(MiftCosmetics.instance, 5, 5);

        }
    }

    fun removeActiveGadgets() {
        for (entity in listaDeEntityParaRemover) {
            entity.remove()
        }
    }

    init {
        instance = this@EggRainfallGadget
        icon = ItemBuilder(Material.EGG).name("§aEngenhoca: §eChuva de Ovos")
            .lore(
                "§7Já pensou em uma chuva de ovos?",
                "§7Pena que estes não de chocolate..."
            )
    }
}