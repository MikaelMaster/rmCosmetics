package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable

class ChristmasTreeGadget : Gadget(
    "Árvore de Natal",
    "comum",
    listOf(
    "§7O Natal já está chegando... E o que você",
    "§7está esperando? Monte logo a sua árvore!",
    "",
    "§c* É necessário ativar as partículas para",
    "§c* visualizar esta engenhoca corretamente."
), ItemBuilder(Material.GRASS).data(1),60, "rmcosmetics.gadget.christmastree") {

    val cooldown = CooldownManager(20 * 5)
    init {
        cooldown.msgCooldown = "§cVocê precisa esperar mais %times para utilizar esta engenhoca novamente!"
    }

    @EventHandler
    fun clicando (event : PlayerInteractEvent){
        if (event.item == null) return
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        if (icon != event.item) return
        val player = event.player
        if (cooldown.cooldown(player)) {

            val local = event.clickedBlock.location
            val localstand = local.clone().add(0.5, 1.8, -0.9)

            val stand = local.world.spawn(localstand, ArmorStand::class.java)
            stand.isVisible = false
            stand.customName = "§6Árvore de Natal §7de ${player.name}"
            stand.isCustomNameVisible = true
            stand.setGravity(false)

            player.sendMessage("§aVocê ativou a engenhoca Árvore de Natal! Duração: §f35s")

            object : BukkitRunnable() {
                override fun run() {

                    stand.remove()

                }

            }.runTaskLater(MiftCosmetics.instance, 700);

        }
    }

    init {

        icon = ItemBuilder(Material.LEAVES).name("§aEngenhoca: §eÁrvore de Natal")
            .lore(
                "§7O Natal já está chegando... E o que você",
                "§7está esperando? Monte logo a sua árvore!",
            )

    }

}