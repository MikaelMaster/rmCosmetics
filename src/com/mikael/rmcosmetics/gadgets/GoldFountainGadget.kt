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

class GoldFountainGadget : Gadget(
    "Fonte Dourada", listOf(
        "§7Qual o jeito melhor de exibir sua",
        "§7riqueza do que fazendo ela chover?"
    ), ItemBuilder(Material.GOLD_INGOT), 30, "rmcosmetics.gadget.goldfountain"
) {
    companion object {
        lateinit var instance: GoldFountainGadget
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
            player.sendMessage("§aVocê ativou a engenhoca Fonte Dourada! Duração: §f10s")
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
                    player.playSound(player.location, Sound.ITEM_PICKUP, 2f, 2f)

                    val goldDropado1 = mundo.dropItem(local1, ItemStack(Material.GOLD_INGOT))
                    goldDropado1.velocity = Vector(0.0, 0.5, 0.0)
                    listaDeEntityParaRemover.add(goldDropado1)

                    val goldDropado2 = mundo.dropItem(local2, ItemStack(Material.GOLD_INGOT))
                    goldDropado2.velocity = Vector(0.1, 0.5, 0.0)
                    listaDeEntityParaRemover.add(goldDropado2)

                    val goldDropado3 = mundo.dropItem(local3, ItemStack(Material.GOLD_INGOT))
                    goldDropado3.velocity = Vector(-0.1, 0.5, 0.0)
                    listaDeEntityParaRemover.add(goldDropado3)

                    val goldDropado4 = mundo.dropItem(local4, ItemStack(Material.GOLD_INGOT))
                    goldDropado4.velocity = Vector(0.0, 0.5, 0.1)
                    listaDeEntityParaRemover.add(goldDropado4)

                    val goldDropado5 = mundo.dropItem(local5, ItemStack(Material.GOLD_INGOT))
                    goldDropado5.velocity = Vector(0.0, 0.5, -0.1)
                    listaDeEntityParaRemover.add(goldDropado5)

                    val nuggetDropado1 = mundo.dropItem(local1, ItemStack(Material.GOLD_NUGGET))
                    nuggetDropado1.velocity = Vector(0.0, 0.4, 0.0)
                    listaDeEntityParaRemover.add(nuggetDropado1)

                    val nuggetDropado2 = mundo.dropItem(local2, ItemStack(Material.GOLD_NUGGET))
                    nuggetDropado2.velocity = Vector(0.1, 0.4, 0.0)
                    listaDeEntityParaRemover.add(nuggetDropado2)

                    val nuggetDropado3 = mundo.dropItem(local3, ItemStack(Material.GOLD_NUGGET))
                    nuggetDropado3.velocity = Vector(-0.1, 0.4, 0.0)
                    listaDeEntityParaRemover.add(nuggetDropado3)

                    val nuggetDropado4 = mundo.dropItem(local4, ItemStack(Material.GOLD_NUGGET))
                    nuggetDropado4.velocity = Vector(0.0, 0.4, 0.1)
                    listaDeEntityParaRemover.add(nuggetDropado4)

                    val nuggetDropado5 = mundo.dropItem(local5, ItemStack(Material.GOLD_NUGGET))
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
                        player.sendMessage("§cSua Fonte Dourada foi removida.")
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
        instance = this@GoldFountainGadget
        icon = ItemBuilder(Material.GOLD_INGOT).name("§aEngenhoca: §eFonte Dourada")
            .lore(
                "§7Qual o jeito melhor de exibir sua",
                "§7riqueza do que fazendo ela chover?"
            )
    }
}