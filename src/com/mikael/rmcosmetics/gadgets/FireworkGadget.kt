package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.api.lib.modules.Mine
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class FireworkGadget : Gadget(
    "Lançador", listOf(
        "§7Seja lançado para os ares em",
        "§7cima de fogos de artifício!"
    ), ItemBuilder(Material.FIREWORK), 15, "rmcosmetics.gadget.firework"
) {

    val cooldown = CooldownManager(20 * 15)

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
            player.sendMessage("§aVocê ativou seu Lançador!")
            val localplayer = event.player.location
            val localfirework = localplayer

            val firework1 =
                Mine.newFirework(localfirework, 2, Color.ORANGE, Color.YELLOW, true, true, FireworkEffect.Type.BURST)
            val firework2 =
                Mine.newFirework(localfirework, 3, Color.ORANGE, Color.YELLOW, true, true, FireworkEffect.Type.BALL)
            val firework3 = Mine.newFirework(
                localfirework,
                3,
                Color.ORANGE,
                Color.YELLOW,
                true,
                true,
                FireworkEffect.Type.BALL_LARGE
            )
            val firework4 = Mine.newFirework(
                localfirework,
                3,
                Color.ORANGE,
                Color.YELLOW,
                true,
                true,
                FireworkEffect.Type.BALL_LARGE
            )

            firework1.passenger = player
        }
    }

    init {

        icon = ItemBuilder(Material.FIREWORK).name("§aEngenhoca: §eLançador")
            .lore(
                "§7Seja lançado para os ares em",
                "§7cima de fogos de artifício!"
            )

    }

}