package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.redemikael.core.spigot.CoreMain
import net.eduard.redemikael.parkour.isPlaying
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.entity.*
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class FlyingCarGadget : Gadget(
    "Carro Voador",
    "epico",
    listOf(
        "§7Em que ano estamos? Já tem",
        "§7até carro voador! OMG!"
    ), ItemBuilder(Material.MINECART), 60, "rmcosmetics.gadget.flyingcar"
) {

    val cooldown = CooldownManager(20 * 60)

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

            val local = event.clickedBlock.location
            val localminecart = local.clone().add(0.0, 1.0, 0.0)

            val minecart = local.world.spawn(localminecart, Minecart::class.java)
            minecart.passenger = player

            val bat = local.world.spawn(localminecart, Bat::class.java)
            bat.isAwake = true
            bat.passenger = minecart
            bat.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20 * 35, 1))

            player.sendMessage("§aVocê ativou a engenhoca Carro Voador! Duração: §f35s")
            GadgetSystem.putActiveGadget(player)

            object : BukkitRunnable() {
                override fun run() {

                    val direcaoVetorial = player.location.direction
                    bat.velocity = direcaoVetorial
                    if (minecart.isDead) {
                        cancel()
                    }
                }
            }.runTaskTimer(MiftCosmetics.instance, 0, 0)

            var runTimer = true
            object : BukkitRunnable() {
                override fun run() {

                    if (minecart.passenger == player) return
                    player.sendMessage("§cVocê saiu de seu Carro Voador e por isso ele foi removido.")
                    GadgetSystem.removeActiveGadget(player)
                    if (player.isOnline) {
                        player.chat("/spawn")
                    }
                    minecart.remove()
                    bat.remove()
                    runTimer = false

                    if (minecart.isDead) {
                        cancel()
                    }
                }
            }.runTaskTimer(MiftCosmetics.instance, 0, 0)

            object : BukkitRunnable() {
                override fun run() {
                    if (!runTimer) return

                    player.sendMessage("§cSeu Carro Voador foi removido.")
                    GadgetSystem.removeActiveGadget(player)
                    if (player.isOnline) {
                        player.chat("/spawn")
                    }
                    minecart.remove()
                    bat.remove()
                }
            }.runTaskLater(MiftCosmetics.instance, 20 * 35)
        }
    }

    init {
        icon = ItemBuilder(Material.MINECART).name("§aEngenhoca: §eCarro Voador")
            .lore(
                "§7Em que ano estamos? Já tem",
                "§7até carro voador! OMG!"
            )
    }
}