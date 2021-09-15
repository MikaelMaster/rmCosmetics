package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.game.Particle
import net.eduard.api.lib.game.ParticleType
import net.eduard.api.lib.kotlin.isPlayer
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.spigot.CoreMain
import net.eduard.redemikael.core.user
import net.eduard.redemikael.parkour.isPlaying
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class DiscothequeGadget : Gadget(
    "Discoteca",
    "epico",
    listOf(
        "§7Invoque uma discoteca no lobby",
        "§7e dance com todos!"
    ), ItemBuilder(Material.GOLD_RECORD), 45, "rmcosmetics.gadget.discotheque"
) {
    companion object {
        lateinit var instance: DiscothequeGadget
    }

    val listaDeEntityParaRemover = mutableListOf<Entity>()

    val cooldown = CooldownManager(20 * 45)

    init {
        cooldown.msgCooldown = "§cVocê precisa esperar mais %times para utilizar esta engenhoca novamente!"
    }

    @EventHandler
    fun clicando(event: PlayerInteractEvent) {
        val player = event.player
        if (event.item == null) return
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        if (icon != event.item) return
        if (CoreMain.instance.getBoolean("is-minigame-lobby")) {
            if (player.isPlaying) {
                player.sendMessage("§cVocê não pode ativar uma engenhoca enquanto percorre o parkour.")
                return
            }
        }

        if (GadgetSystem.hasActiveGadget(player)) {
            player.sendMessage("§cVocê já possui uma engenhoca ativa no momento!")
            return
        }
        if (cooldown.cooldown(player)) {
            val user = player.user
            player.sendMessage("§aVocê ativou a engenhoca Discoteca! Duração: §f15s")
            Mine.broadcast("§6[Cosméticos] §aO jogador ${user.nick} §aativou uma Discoteca neste lobby!")
            GadgetSystem.putActiveGadget(player)

            val local = event.clickedBlock.location
            val localGlass = local.clone().add(0.5, 5.0, 0.5)
            val localParticle = local.clone().add(0.5, 5.5, 0.5)

            if (localGlass.block.type != Material.AIR) {
                player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
                return
            }
            var active = true

            localGlass.block.type = Material.STAINED_GLASS
            localGlass.block.data = 1.toByte()
            GadgetSystem.addBlockToList(localGlass.block.location)

            object : BukkitRunnable() {
                override fun run() {
                    Particle(ParticleType.RED_DUST, 30, 1f, 1f, 0.5f, 1f)
                        .create(localParticle)
                    Particle(ParticleType.RED_DUST, 30, 1f, 1f, 0.5f, 1f)
                        .create(localParticle)
                    Particle(ParticleType.RED_DUST, 30, 1f, 1f, 0.5f, 1f)
                        .create(localParticle)

                    for (entity in player.world.getNearbyEntities(local, 5.0, 5.0, 5.0)) {
                        if (entity.type == EntityType.PLAYER) {
                            val playerFor = entity as Player
                            if (playerFor.isOnGround) {
                                playerFor.velocity = Vector(0.0, 0.4, 0.0)
                            }
                            // playerFor.playSound(playerFor.location, Sound.ITEM_PICKUP, 2f, 2f)
                            playerFor.playSound(playerFor.location, Sound.NOTE_PIANO, 2f, 2f)
                        }
                    }

                    if (!active) {
                        cancel()
                    }
                }
            }.runTaskTimer(MiftCosmetics.instance, 5, 5)

            object : BukkitRunnable() {
                override fun run() {
                    player.sendMessage("§cSua Discoteca foi removida.")
                    GadgetSystem.removeActiveGadget(player)
                    active = false
                    Mine.broadcast("§6[Cosméticos] §cA Discoteca de ${user.nick} §cdesligou as luzes.")

                    localGlass.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(localGlass.block.location)
                }
            }.runTaskLater(MiftCosmetics.instance, 20 * 15)
        }
    }

    fun removeActiveGadgets() {
        for (itemLoop in listaDeEntityParaRemover) {
            itemLoop.remove()
        }
    }

    init {
        instance = this@DiscothequeGadget
        icon = ItemBuilder(Material.GOLD_RECORD).name("§aEngenhoca: §eDiscoteca")
            .lore(
                "§7Invoque uma discoteca no lobby",
                "§7e dance com todos!"
            )
    }
}