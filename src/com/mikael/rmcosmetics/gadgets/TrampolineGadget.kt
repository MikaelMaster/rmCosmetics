package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.user
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.entity.ArmorStand
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class TrampolineGadget : Gadget(
    "Trampolim", listOf(
        "§7Um Pula-Pula será gerado em seu",
        "§7lobby e você poderá pular com todos!"
    ), ItemBuilder(Material.SLIME_BLOCK), 120, "rmcosmetics.gadget.trampoline"
) {

    val cooldown = CooldownManager(20 * 5)

    init {
        cooldown.msgCooldown = "§cVocê precisa esperar mais %times para utilizar esta engenhoca novamente!"
    }

    @EventHandler
    fun pulando(event: PlayerMoveEvent) {
        if (event.to.block.getRelative(BlockFace.DOWN).type !=
            Material.WOOL
        ) return
        if (event.to.block.getRelative(BlockFace.DOWN).data.toInt() !=
            15
        ) return
        event.player.velocity = Vector(0.0, 2.5, 0.0)
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
            player.sendMessage("§aVocê ativou a engenhoca Trampolim! Duração: §f45s")
            GadgetSystem.putActiveGadget(player)
            Mine.broadcast("§6[Cosméticos] §a${user.nick} §aativou um Trampolim neste lobby!")

            val local = event.clickedBlock.location
            val localblock = local.clone().add(0.0, 1.0, 0.0)
            val localstand = local.clone().add(0.5, 0.5, 0.5)

            val stand = local.world.spawn(localstand, ArmorStand::class.java)
            stand.isVisible = false
            stand.customName = "§9Trampolim §7de ${player.name}"
            stand.isCustomNameVisible = true
            stand.setGravity(false)

            localblock.block.type = Material.WOOL
            localblock.block.data = 15.toByte()

            object : BukkitRunnable() {
                override fun run() {
                    player.sendMessage("§cSeu Trampolim foi removido.")
                    GadgetSystem.removeActiveGadget(player)
                    Mine.broadcast("§6[Cosméticos] §cO Trampolim de ${user.nick} §cfoi removido.")
                    localblock.block.type = Material.AIR
                    stand.remove()
                }
            }.runTaskLater(MiftCosmetics.instance, 20 * 45)
        }
    }

    init {
        icon = ItemBuilder(Material.SLIME_BLOCK).name("§aEngenhoca: §eTrampolim")
            .lore(
                "§7Um Pula-Pula será gerado em seu",
                "§7lobby e você poderá pular com todos!"
            )
    }
}