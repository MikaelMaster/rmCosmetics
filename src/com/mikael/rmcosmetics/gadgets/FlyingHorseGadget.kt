package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.user
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.entity.*
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class FlyingHorseGadget : Gadget(
    "Cavalo Voador", listOf(
        "§7Acho que tem algo de errado com esse",
        "§7cavalo... parece que ele sabe voar!"
    ), ItemBuilder(Material.SADDLE), 60, "rmcosmetics.gadget.flyinghorse"
) {
    companion object {
        lateinit var instance: FlyingHorseGadget
    }

    val listaDeEntityParaRemover = mutableListOf<Entity>()

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
        if (GadgetSystem.hasActiveGadget(player)) {
            player.sendMessage("§cVocê já possui uma engenhoca ativa no momento!")
            return
        }

        if (cooldown.cooldown(player)) {
            val user = player.user
            val local = event.clickedBlock.location
            val localhorse = local.clone().add(0.0, 2.0, 0.0)

            val horse = local.world.spawn(localhorse, Horse::class.java)
            listaDeEntityParaRemover.add(horse)
            horse.customName = "§eCavalo Voador §7de ${user.nick}"
            horse.owner = player
            horse.isTamed = true
            horse.setAdult()
            horse.variant = Horse.Variant.HORSE
            horse.inventory.saddle = ItemBuilder(Material.SADDLE)
            horse.passenger = player

            val bat = local.world.spawn(localhorse, Bat::class.java)
            listaDeEntityParaRemover.add(horse)
            bat.isAwake = true
            bat.passenger = horse
            bat.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20 * 35, 1))

            Mine.newFirework(player.location, 0, Color.YELLOW, Color.MAROON, true, true, FireworkEffect.Type.BURST)
            Mine.newFirework(player.location, 1, Color.YELLOW, Color.MAROON, true, true, FireworkEffect.Type.BURST)
            player.sendMessage("§aVocê ativou a engenhoca Cavalo Voador! Duração: §f35s")
            GadgetSystem.putActiveGadget(player)

            object : BukkitRunnable() {
                override fun run() {
                    // yaw - Girando Horizonatalmente (Direita Esquerda)
                    // player.location.yaw
                    // pitch - Girando Verticalmente (Cima baixo)
                    // player.location.pitch
                    // pegar direção vetorial
                    // bat.vector

                    val direcaoVetorial = player.location.direction
                    bat.velocity = direcaoVetorial
                    if (bat.isDead) {
                        cancel()
                    }
                }
            }.runTaskTimer(MiftCosmetics.instance, 0, 0)

            var runTimer = true
            object : BukkitRunnable() {
                override fun run() {

                    if (horse.passenger == player) return
                    player.sendMessage("§cVocê saiu de seu Cavalo Voador e por isso ele foi removido.")
                    GadgetSystem.removeActiveGadget(player)
                    if (player.isOnline) {
                        player.chat("/spawn")
                    }
                    horse.remove()
                    listaDeEntityParaRemover.remove(horse)
                    bat.remove()
                    listaDeEntityParaRemover.remove(bat)
                    runTimer = false

                    if (horse.isDead) {
                        cancel()
                    }
                }
            }.runTaskTimer(MiftCosmetics.instance, 0, 0)

            object : BukkitRunnable() {
                override fun run() {
                    if (!runTimer) return

                    player.sendMessage("§cSeu Cavalo Voador foi removido.")
                    GadgetSystem.removeActiveGadget(player)
                    if (player.isOnline) {
                        player.chat("/spawn")
                    }
                    horse.remove()
                    listaDeEntityParaRemover.remove(horse)
                    bat.remove()
                    listaDeEntityParaRemover.remove(bat)
                }
            }.runTaskLater(MiftCosmetics.instance, 20 * 35)
        }
    }

    fun removeActiveGadgets() {
        for (entity in listaDeEntityParaRemover) {
            entity.remove()
        }
    }

    init {
        instance = this@FlyingHorseGadget
        icon = ItemBuilder(Material.SADDLE).name("§aEngenhoca: §eCavalo Voador")
            .lore(
                "§7Parece que tem algo de errado com esse",
                "§7cavalo... parece que ele sabe voar!"
            )
    }
}