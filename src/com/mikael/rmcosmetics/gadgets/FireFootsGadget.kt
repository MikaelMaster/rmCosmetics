package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.spigot.CoreMain
import net.eduard.redemikael.parkour.isPlaying
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBurnEvent
import org.bukkit.event.block.BlockIgniteEvent
import org.bukkit.event.entity.EntityCombustEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable

class FireFootsGadget : Gadget(
    "Passos Ferventes",
    "epico",
    listOf(
        "§7Ta pegando fogo! Deixe uma trilha",
        "§7temporária de fogo por onde passar.",
    ), ItemBuilder(Material.BLAZE_POWDER), 35, "rmcosmetics.gadget.firefoots"
) {

    val cooldown = CooldownManager(20 * 35)

    init {
        cooldown.msgCooldown = "§cVocê precisa esperar mais %times para utilizar esta engenhoca novamente!"
    }

    @EventHandler
    fun noTnTExplode(e: BlockIgniteEvent) {
        e.isCancelled = true
    }

    @EventHandler
    fun noBlockBurn(e: BlockBurnEvent) {
        e.isCancelled = true
    }

    @EventHandler
    fun noFireOnEntity(e: EntityCombustEvent) {
        e.isCancelled = true
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
            player.sendMessage("§aVocê ativou a engenhoca Passos Ferventes! Duração: §f15s")
            GadgetSystem.putActiveGadget(player)

            var time = 15

            object : BukkitRunnable() {
                override fun run() {
                    time -= 1
                    if (time == 0) {
                        cancel()
                    }
                }
            }.runTaskTimer(MiftCosmetics.instance, 20, 20)

            object : BukkitRunnable() {
                override fun run() {
                    val listaDeBlocosParaResetar = mutableListOf<Location>()
                    if (player.location.block.type == Material.AIR) {
                        player.location.block.type = Material.FIRE
                        listaDeBlocosParaResetar.add(player.location)
                        GadgetSystem.addBlockToList(player.location)

                        object : BukkitRunnable() {
                            override fun run() {
                                for (blockToReset in listaDeBlocosParaResetar) {
                                    if (blockToReset.block.type == Material.FIRE) {
                                        blockToReset.block.type = Material.AIR
                                        GadgetSystem.removeBlockToList(blockToReset)
                                    }
                                }
                            }
                        }.runTaskLater(MiftCosmetics.instance, 20 * 2)
                    }
                    if (time == 0) {
                        player.sendMessage("§cSeus Passos Ferventes foram removidos.")
                        MiftCosmetics.instance.syncDelay(20) {
                            Mine.removeEffects(player)
                        }
                        listaDeBlocosParaResetar.clear()
                        GadgetSystem.removeActiveGadget(player)
                        cancel()
                    }
                }
            }.runTaskTimer(MiftCosmetics.instance, 1, 1)
        }
    }

    init {

        icon = ItemBuilder(Material.BLAZE_POWDER).name("§aEngenhoca: §ePassos Ferventes")
            .lore(
                "§7Ta pegando fogo! Deixe uma trilha",
                "§7temporária de fogo por onde passar."
            )

    }

}