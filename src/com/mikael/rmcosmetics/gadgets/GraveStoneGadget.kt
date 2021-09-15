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

class GraveStoneGadget : Gadget(
    "Tumba Amaldiçoada",
    "comum",
    listOf(
        "§7Com essa engenhoca, você dará vida",
        "§7a um corpo inanimado!",
    ), ItemBuilder(Material.SOUL_SAND), 60, "rmcosmetics.gadget.gravestone"
) {

    val cooldown = CooldownManager(20 * 5)

    init {
        cooldown.msgCooldown = "§cVocê precisa esperar mais %times para utilizar esta engenhoca novamente!"
    }

    @EventHandler
    fun clicando(event: PlayerInteractEvent) {
        if (event.item == null) return
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        if (icon != event.item) return
        val player = event.player
        if (cooldown.cooldown(player)) {

            val local = event.clickedBlock.location
            val localstand = local.clone().add(0.5, 1.3, 0.5)

            val soul1 = local.clone().add(0.0, 0.0, 0.0)
            val soul2 = local.clone().add(1.0, 0.0, 0.0)
            val soul3 = local.clone().add(-1.0, 0.0, 0.0)
            val soul4 = local.clone().add(-1.0, 0.0, -1.0)
            val soul5 = local.clone().add(-1.0, 0.0, -2.0)
            val soul6 = local.clone().add(-1.0, 0.0, -3.0)
            val soul7 = local.clone().add(0.0, 0.0, -3.0)
            val soul8 = local.clone().add(1.0, 0.0, -3.0)
            val soul9 = local.clone().add(1.0, 0.0, -1.0)
            val soul10 = local.clone().add(1.0, 0.0, -2.0)

            val areiaalmas1 = soul1.block.setType(Material.SOUL_SAND)
            val areiaalmas2 = soul2.block.setType(Material.SOUL_SAND)
            val areiaalmas3 = soul3.block.setType(Material.SOUL_SAND)
            val areiaalmas4 = soul4.block.setType(Material.SOUL_SAND)
            val areiaalmas5 = soul5.block.setType(Material.SOUL_SAND)
            val areiaalmas6 = soul6.block.setType(Material.SOUL_SAND)
            val areiaalmas7 = soul7.block.setType(Material.SOUL_SAND)
            val areiaalmas8 = soul8.block.setType(Material.SOUL_SAND)
            val areiaalmas9 = soul9.block.setType(Material.SOUL_SAND)
            val areiaalmas10 = soul10.block.setType(Material.SOUL_SAND)

            val stand = local.world.spawn(localstand, ArmorStand::class.java)
            stand.isVisible = false
            stand.customName = "§6Tumba Amaldiçoada §7de ${player.name}"
            stand.isCustomNameVisible = true
            stand.setGravity(false)

            object : BukkitRunnable() {
                override fun run() {

                    val areiaalmas1remove = soul1.block.setType(Material.AIR)
                    val areiaalmas2remove = soul2.block.setType(Material.AIR)
                    val areiaalmas3remove = soul3.block.setType(Material.AIR)
                    val areiaalmas4remove = soul4.block.setType(Material.AIR)
                    val areiaalmas5remove = soul5.block.setType(Material.AIR)
                    val areiaalmas6remove = soul6.block.setType(Material.AIR)
                    val areiaalmas7remove = soul7.block.setType(Material.AIR)
                    val areiaalmas8remove = soul8.block.setType(Material.AIR)
                    val areiaalmas9remove = soul9.block.setType(Material.AIR)
                    val areiaalmas10remove = soul10.block.setType(Material.AIR)

                    stand.remove()
                }

            }.runTaskLater(MiftCosmetics.instance, 60);

        }
    }

    init {

        icon = ItemBuilder(Material.SOUL_SAND).name("§aEngenhoca: §eTumba Amaldiçoada")
            .lore(
                "§7Com essa engenhoca, você dará vida",
                "§7a um corpo inanimado!"
            )

    }

}