package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.user
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.entity.*

class EnderDragonGadget : Gadget(
    "Dragão do Fim",
    listOf(
        "§7Desfile em seu dragão que veio te",
        "§7visitar lá do The End!",
    ),
    ItemBuilder().skin("http://textures.minecraft.net/texture/ffcdae586b52403b92b1857ee4331bac636af08bab92ba5750a54a83331a6353"),
    120,
    "rmcosmetics.gadget.enderdragon"
) {

    val cooldown = CooldownManager(20 * 120)

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
        val user = player.user
        if (cooldown.cooldown(player)) {
            val local = event.clickedBlock.location
            val mundo = player.world
            val localenderdragon = local.clone().add(0.0, 10.0, 0.0)

            mundo.strikeLightning(local)

            val enderdragon = local.world.spawn(localenderdragon, EnderDragon::class.java)
            enderdragon.customName = "§5Dragão do Fim §7de ${user.nick}"
            enderdragon.passenger = player

            player.sendMessage("§aVocê ativou a engenhoca Dragão do Fim! Duração: §f35s")
            mundo.time = 16000
            GadgetSystem.putActiveGadget(player)
            Mine.broadcast("§6[Cosméticos] §aO jogador ${user.nick} §ainvocou seu Dragão do Fim neste lobby!")

            var runTimer = true
            object : BukkitRunnable() {
                override fun run() {

                    if (player.isOnline) {
                        if (enderdragon.passenger == player) return
                        player.sendMessage("§cVocê saiu de seu Dragão do Fim e por isso ele foi removido.")
                        GadgetSystem.removeActiveGadget(player)
                        player.chat("/spawn")
                        mundo.time = 1000
                        enderdragon.remove()
                        runTimer = false

                        if (enderdragon.isDead) {
                            cancel()
                        }
                    }
                }
            }.runTaskTimer(MiftCosmetics.instance, 0, 0)

            object : BukkitRunnable() {
                override fun run() {
                    if (!runTimer) return

                    if (player.isOnline) {
                        player.sendMessage("§cSeu Dragão do Fim foi removido.")
                        player.chat("/spawn")
                    }
                    Mine.broadcast("§6[Cosméticos] §cO Dragão do Fim de ${user.nick} §cviajou de volta para o The End.")
                    mundo.time = 1000
                    GadgetSystem.removeActiveGadget(player)
                    enderdragon.remove()
                }
            }.runTaskLater(MiftCosmetics.instance, 20 * 35)
        }
    }

    init {
        icon =
            ItemBuilder().skin("http://textures.minecraft.net/texture/ffcdae586b52403b92b1857ee4331bac636af08bab92ba5750a54a83331a6353")
                .name("§aEngenhoca: §eDragão do Fim")
                .lore(
                    "§7Desfile em seu dragão que veio te",
                    "§7visitar lá do The End!",
                )
    }
}