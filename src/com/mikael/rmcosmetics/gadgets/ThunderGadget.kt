package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.user
import org.bukkit.Material
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable

class ThunderGadget : Gadget(
    "Trovão", listOf(
        "§7Invoque um mega trovão no meio dos",
        "§7lobbies que gera uma orda de mobs!"
    ), ItemBuilder(Material.BLAZE_ROD), 35, "rmcosmetics.gadget.thunder"
) {

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
        if (GadgetSystem.hasActiveGadget(player)) {
            player.sendMessage("§cVocê já possui uma engenhoca ativa no momento!")
            return
        }

        if (cooldown.cooldown(player)) {
            val user = player.user
            player.sendMessage("§aVocê invocou um mega Trovão! Duração dos mobs: §f15s")
            GadgetSystem.putActiveGadget(player)

            val local = event.clickedBlock.location
            val mundo = player.world
            val mundotime = mundo.time

            val localbat = local.clone().add(0.0, 3.5, 0.0)

            mundo.strikeLightning(local)
            mundo.strikeLightning(local)
            mundo.strikeLightning(local)
            mundo.strikeLightning(local)
            mundo.strikeLightning(local)
            Mine.broadcast("§6[Cosméticos] §aO jogador ${user.nick} §ainvocou um mega Trovão neste lobby!")
            mundo.time = 140000

            object : BukkitRunnable() {
                override fun run() {
                    mundo.time = mundotime

                    val bruxa = local.world.spawn(localbat, Witch::class.java)
                    bruxa.customName = "§dBruxa §7do trovão de ${user.nick}"

                    val morcego = local.world.spawn(localbat, Bat::class.java)
                    morcego.isAwake = true
                    morcego.passenger = player

                    val morcego2 = local.world.spawn(localbat, Bat::class.java)
                    morcego2.isAwake = true

                    val morcego3 = local.world.spawn(localbat, Bat::class.java)
                    morcego3.isAwake = true

                    val morcego4 = local.world.spawn(localbat, Bat::class.java)
                    morcego4.isAwake = true

                    val morcego5 = local.world.spawn(localbat, Bat::class.java)
                    morcego5.isAwake = true

                    val morcego6 = local.world.spawn(localbat, Bat::class.java)
                    morcego6.isAwake = true

                    val morcego7 = local.world.spawn(localbat, Bat::class.java)
                    morcego7.isAwake = true

                    val morcego8 = local.world.spawn(localbat, Bat::class.java)
                    morcego8.isAwake = true

                    val morcego9 = local.world.spawn(localbat, Bat::class.java)
                    morcego9.isAwake = true

                    val morcego10 = local.world.spawn(localbat, Bat::class.java)
                    morcego10.isAwake = true


                    object : BukkitRunnable() {
                        override fun run() {
                            player.sendMessage("§cA orda de mobs do seu Trovão foi removida.")
                            GadgetSystem.removeActiveGadget(player)
                            morcego.remove()
                            morcego2.remove()
                            morcego3.remove()
                            morcego4.remove()
                            morcego5.remove()
                            morcego6.remove()
                            morcego7.remove()
                            morcego8.remove()
                            morcego9.remove()
                            morcego10.remove()
                            bruxa.remove()
                        }
                    }.runTaskLater(MiftCosmetics.instance, 20 * 15);
                }
            }.runTaskLater(MiftCosmetics.instance, 20);
        }
    }

    init {
        icon = ItemBuilder(Material.BLAZE_ROD).name("§aEngenhoca: §eTrovão")
            .lore(
                "§7Invoque um mega trovão no meio dos",
                "§7lobbies que gera uma orda de mobs!"
            )
    }
}