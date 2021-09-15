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
import org.bukkit.Material
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class ThunderGadget : Gadget(
    "Trovão",
    "raro",
    listOf(
        "§7Invoque um mega trovão no meio dos",
        "§7lobbies que gera uma orda de mobs!"
    ), ItemBuilder(Material.BLAZE_ROD), 35, "rmcosmetics.gadget.thunder"
) {
    companion object {
        lateinit var instance: ThunderGadget
    }

    val listaDeEntityParaRemover = mutableListOf<Entity>()

    val cooldown = CooldownManager(20 * 35)

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
            player.sendMessage("§aVocê invocou um mega Trovão! Duração dos mobs: §f10s")
            GadgetSystem.putActiveGadget(player)

            val local = event.clickedBlock.location
            val mundo = player.world

            val localbat = local.clone().add(0.0, 3.5, 0.0)

            mundo.strikeLightning(local)
            mundo.strikeLightning(local)
            mundo.strikeLightning(local)
            mundo.strikeLightning(local)
            mundo.strikeLightning(local)
            Mine.broadcast("§6[Cosméticos] §aO jogador ${user.nick} §ainvocou um mega Trovão neste lobby!")
            player.velocity = Vector(0.0, 1.5, 0.0)
            mundo.time = 16000

            object : BukkitRunnable() {
                override fun run() {
                    mundo.time = 1000

                    val bruxa = local.world.spawn(localbat, Witch::class.java)
                    listaDeEntityParaRemover.add(bruxa)
                    bruxa.customName = "§dBruxa §7do trovão de ${user.nick}"

                    val morcego = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego)
                    morcego.isAwake = true
                    morcego.passenger = player

                    val morcego2 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego2)
                    morcego2.isAwake = true

                    val morcego3 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego3)
                    morcego3.isAwake = true

                    val morcego4 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego4)
                    morcego4.isAwake = true

                    val morcego5 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego5)
                    morcego5.isAwake = true

                    val morcego6 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego6)
                    morcego6.isAwake = true

                    val morcego7 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego7)
                    morcego7.isAwake = true

                    val morcego8 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego8)
                    morcego8.isAwake = true

                    val morcego9 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego9)
                    morcego9.isAwake = true

                    val morcego10 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego10)
                    morcego10.isAwake = true


                    object : BukkitRunnable() {
                        override fun run() {
                            player.sendMessage("§cA orda de mobs do seu Trovão foi removida.")
                            GadgetSystem.removeActiveGadget(player)
                            bruxa.remove()
                            listaDeEntityParaRemover.remove(bruxa)
                            morcego.remove()
                            listaDeEntityParaRemover.remove(morcego)
                            morcego2.remove()
                            listaDeEntityParaRemover.remove(morcego2)
                            morcego3.remove()
                            listaDeEntityParaRemover.remove(morcego3)
                            morcego4.remove()
                            listaDeEntityParaRemover.remove(morcego4)
                            morcego5.remove()
                            listaDeEntityParaRemover.remove(morcego5)
                            morcego6.remove()
                            listaDeEntityParaRemover.remove(morcego6)
                            morcego7.remove()
                            listaDeEntityParaRemover.remove(morcego)
                            morcego8.remove()
                            listaDeEntityParaRemover.remove(morcego8)
                            morcego9.remove()
                            listaDeEntityParaRemover.remove(morcego9)
                            morcego10.remove()
                            listaDeEntityParaRemover.remove(morcego10)
                        }
                    }.runTaskLater(MiftCosmetics.instance, 20 * 10)
                }
            }.runTaskLater(MiftCosmetics.instance, 20)
        }
    }

    fun removeActiveGadgets() {
        for (itemLoop in listaDeEntityParaRemover) {
            itemLoop.remove()
        }
    }

    init {
        instance = this@ThunderGadget
        icon = ItemBuilder(Material.BLAZE_ROD).name("§aEngenhoca: §eTrovão")
            .lore(
                "§7Invoque um mega trovão no meio dos",
                "§7lobbies que gera uma orda de mobs!"
            )
    }
}