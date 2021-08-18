package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.api.lib.modules.Mine
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.entity.ItemMergeEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class BreadsGadget : Gadget(
    "Pães!", listOf(
        "§7Você gosta de pães? Então esta",
        "§7engenhoca é para você!"
    ), ItemBuilder(Material.BREAD), 30, "rmcosmetics.gadget.bread"
) {
    companion object {
        lateinit var instace: BreadsGadget
    }

    val listaDeEntityParaRemover = mutableListOf<Entity>()

    val cooldown = CooldownManager(20 * 30)

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
            player.sendMessage("§aVocê ativou a engenhoca Pães! Duração: §f10s")
            GadgetSystem.putActiveGadget(player)

            val mundo = player.world

            object : BukkitRunnable() {
                var tempo = 45
                override fun run() {
                    val local1 = player.eyeLocation.add(0.0, 0.5, 0.0)
                    val local2 = player.eyeLocation.add(1.0, 0.5, 0.0)
                    val local3 = player.eyeLocation.add(-1.0, 0.5, 0.0)
                    val local4 = player.eyeLocation.add(0.0, 0.5, 1.0)
                    val local5 = player.eyeLocation.add(0.0, 0.5, -1.0)
                    player.playSound(player.location, Sound.EAT, 2f, 2f)

                    val goldDropado1 = mundo.dropItem(local1, ItemStack(Material.BREAD))
                    goldDropado1.velocity = Vector(0.0, 0.5, 0.0)
                    listaDeEntityParaRemover.add(goldDropado1)

                    val goldDropado2 = mundo.dropItem(local2, ItemStack(Material.BREAD))
                    goldDropado2.velocity = Vector(0.1, 0.5, 0.0)
                    listaDeEntityParaRemover.add(goldDropado2)

                    val goldDropado3 = mundo.dropItem(local3, ItemStack(Material.BREAD))
                    goldDropado3.velocity = Vector(-0.1, 0.5, 0.0)
                    listaDeEntityParaRemover.add(goldDropado3)

                    val goldDropado4 = mundo.dropItem(local4, ItemStack(Material.BREAD))
                    goldDropado4.velocity = Vector(0.0, 0.5, 0.1)
                    listaDeEntityParaRemover.add(goldDropado4)

                    val goldDropado5 = mundo.dropItem(local5, ItemStack(Material.BREAD))
                    goldDropado5.velocity = Vector(0.0, 0.5, -0.1)
                    listaDeEntityParaRemover.add(goldDropado5)

                    val nuggetDropado1 = mundo.dropItem(local1, ItemStack(Material.WHEAT))
                    nuggetDropado1.velocity = Vector(0.0, 0.4, 0.0)
                    listaDeEntityParaRemover.add(nuggetDropado1)

                    val nuggetDropado2 = mundo.dropItem(local2, ItemStack(Material.WHEAT))
                    nuggetDropado2.velocity = Vector(0.1, 0.4, 0.0)
                    listaDeEntityParaRemover.add(nuggetDropado2)

                    val nuggetDropado3 = mundo.dropItem(local3, ItemStack(Material.WHEAT))
                    nuggetDropado3.velocity = Vector(-0.1, 0.4, 0.0)
                    listaDeEntityParaRemover.add(nuggetDropado3)

                    val nuggetDropado4 = mundo.dropItem(local4, ItemStack(Material.WHEAT))
                    nuggetDropado4.velocity = Vector(0.0, 0.4, 0.1)
                    listaDeEntityParaRemover.add(nuggetDropado4)

                    val nuggetDropado5 = mundo.dropItem(local5, ItemStack(Material.WHEAT))
                    nuggetDropado5.velocity = Vector(0.0, 0.4, -0.1)
                    listaDeEntityParaRemover.add(nuggetDropado5)

                    object : BukkitRunnable() {
                        override fun run() {
                            goldDropado1.remove()
                            listaDeEntityParaRemover.remove(goldDropado1)
                            goldDropado2.remove()
                            listaDeEntityParaRemover.remove(goldDropado2)
                            goldDropado3.remove()
                            listaDeEntityParaRemover.remove(goldDropado3)
                            goldDropado4.remove()
                            listaDeEntityParaRemover.remove(goldDropado4)
                            goldDropado5.remove()
                            listaDeEntityParaRemover.remove(goldDropado5)
                            nuggetDropado1.remove()
                            listaDeEntityParaRemover.remove(nuggetDropado1)
                            nuggetDropado2.remove()
                            listaDeEntityParaRemover.remove(nuggetDropado2)
                            nuggetDropado3.remove()
                            listaDeEntityParaRemover.remove(nuggetDropado3)
                            nuggetDropado4.remove()
                            listaDeEntityParaRemover.remove(nuggetDropado4)
                            nuggetDropado5.remove()
                            listaDeEntityParaRemover.remove(nuggetDropado5)
                        }
                    }.runTaskLater(MiftCosmetics.instance, 30)

                    tempo -= 1
                    if (tempo == 0) {
                        player.sendMessage("§cSeus Pães foram removidos.")
                        GadgetSystem.removeActiveGadget(player)
                        cancel()
                    }
                }
            }.runTaskTimer(MiftCosmetics.instance, 5, 5)
        }
    }

    fun removeActiveGadgets() {
        for (itemLoop in listaDeEntityParaRemover) {
            itemLoop.remove()
        }
    }

    init {
        instace = this@BreadsGadget
        icon = ItemBuilder(Material.BREAD).name("§aEngenhoca: §ePães!")
            .lore(
                "§7Você gosta de pães? Então esta",
                "§7engenhoca é para você!"
            )
    }
}