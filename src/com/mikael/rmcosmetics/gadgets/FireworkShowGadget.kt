package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.spigot.CoreMain
import net.eduard.redemikael.core.user
import net.eduard.redemikael.parkour.isPlaying
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable

class FireworkShowGadget : Gadget(
    "Show de Fogos",
    "raro",
    listOf(
        "§7Faça um show pirotécnico no",
        "§7meio de nossos lobbies!"
    ), ItemBuilder(Material.FIREWORK_CHARGE), 45, "rmcosmetics.gadget.fireworkshow"
) {
    companion object {
        lateinit var instance: FireworkShowGadget
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
        val user = player.user

        val local = event.clickedBlock.location
        val localtoverify = local.clone().add(0.0, 1.0, 0.0)
        val localstand = local.clone().add(0.5, -0.5, 0.5)
        val localstand2 = local.clone().add(0.9, -0.5, 0.5)
        val localfirework = local.clone().add(0.5, 0.0, 0.5)

        if (localtoverify.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }

        if (cooldown.cooldown(player)) {
            player.sendMessage("§aVocê ativou a engenhoca Show de Fogos! Duração: §f15s")
            Mine.broadcast("§6[Cosméticos] §aO jogador ${user.nick} §ainvocou um Show de Fogos neste lobby!")
            GadgetSystem.putActiveGadget(player)

            player.world.time = 16000
            val stand = local.world.spawn(localstand, ArmorStand::class.java)
            listaDeEntityParaRemover.add(stand)
            stand.isVisible = false
            stand.setGravity(false)
            stand.helmet =
                ItemBuilder(Material.SKULL).skin("http://textures.minecraft.net/texture/7419263f9ebc9317dc5abb879cd59738d76e2a23dc1acbcb94e9dc362ffc4b")

            val stand2 = local.world.spawn(localstand2, ArmorStand::class.java)
            listaDeEntityParaRemover.add(stand2)
            stand2.isVisible = false
            stand2.setGravity(false)
            stand2.customName = "§eShow de Fogos §7de ${user.nick}"
            stand2.isCustomNameVisible = true

            object : BukkitRunnable() {
                var tempo = 30
                override fun run() {
                    Mine.newFirework(localfirework, 0, Color.AQUA, Color.YELLOW, true, true, FireworkEffect.Type.BALL)
                    Mine.newFirework(
                        localfirework,
                        1,
                        Color.AQUA,
                        Color.YELLOW,
                        true,
                        true,
                        FireworkEffect.Type.BALL_LARGE
                    )
                    Mine.newFirework(
                        localfirework,
                        2,
                        Color.AQUA,
                        Color.YELLOW,
                        true,
                        true,
                        FireworkEffect.Type.BALL_LARGE
                    )
                    Mine.newFirework(
                        localfirework,
                        2,
                        Color.AQUA,
                        Color.YELLOW,
                        true,
                        true,
                        FireworkEffect.Type.BURST
                    )
                    Mine.newFirework(
                        localfirework,
                        2,
                        Color.AQUA,
                        Color.YELLOW,
                        true,
                        true,
                        FireworkEffect.Type.BURST
                    )
                    Mine.newFirework(
                        localfirework,
                        2,
                        Color.AQUA,
                        Color.YELLOW,
                        true,
                        true,
                        FireworkEffect.Type.BURST
                    )
                    Mine.newFirework(
                        localfirework,
                        3,
                        Color.AQUA,
                        Color.YELLOW,
                        true,
                        true,
                        FireworkEffect.Type.BALL_LARGE
                    )

                    tempo -= 1
                    if (tempo == 0) {
                        player.sendMessage("§cSeu Show de Fogos foi removido.")
                        Mine.broadcast("§6[Cosméticos] §cA pólvora do Show de Fogos invocado por ${user.nick} §cacabou.")
                        GadgetSystem.removeActiveGadget(player)
                        cancel()
                        stand.remove()
                        listaDeEntityParaRemover.remove(stand)
                        stand2.remove()
                        listaDeEntityParaRemover.remove(stand2)
                        player.world.time = 1000
                    }
                }
            }.runTaskTimer(MiftCosmetics.instance, 10, 10);
        }
    }

    fun removeActiveGadgets() {
        for (itemLoop in listaDeEntityParaRemover) {
            itemLoop.remove()
        }
    }

    init {
        instance = this@FireworkShowGadget
        icon = ItemBuilder(Material.FIREWORK_CHARGE).name("§aEngenhoca: §eShow de Fogos")
            .lore(
                "§7Faça um show pirotécnico no",
                "§7meio de nossos lobbies!"
            )
    }
}