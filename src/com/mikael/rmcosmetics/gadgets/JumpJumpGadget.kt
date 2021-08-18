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

class JumpJumpGadget : Gadget(
    "Pula-Pula", listOf(
        "§7Um Pula-Pula será gerado em seu",
        "§7lobby e você poderá pular com todos!"
    ), ItemBuilder(Material.SLIME_BLOCK), 80, "rmcosmetics.gadget.jump-jump"
) {

    val cooldown = CooldownManager(20 * 80)

    init {
        cooldown.msgCooldown = "§cVocê precisa esperar mais %times para utilizar esta engenhoca novamente!"
    }

    @EventHandler
    fun pulando(event: PlayerMoveEvent) {
        if (event.to.block.getRelative(BlockFace.DOWN).type !=
            Material.WOOL
        ) return
        if (event.to.block.getRelative(BlockFace.DOWN).data.toInt() != 15
        ) return
        event.player.velocity = Vector(0.0, 2.0, 0.0)
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

        val local = event.clickedBlock.location
        val fence1 = local.clone().add(2.0, 1.0, 2.0)
        val fence2 = local.clone().add(2.0, 1.0, -2.0)
        val fence3 = local.clone().add(-2.0, 1.0, 2.0)
        val fence4 = local.clone().add(-2.0, 1.0, -2.0)
        val stair1 = local.clone().add(0.0, 1.0, 4.0)
        val stair2 = local.clone().add(0.0, 2.0, 3.0)
        val centerwool = local.clone().add(0.0, 2.0, 0.0)
        val wool1 = local.clone().add(1.0, 2.0, 0.0)
        val wool2 = local.clone().add(-1.0, 2.0, 0.0)
        val wool3 = local.clone().add(0.0, 2.0, 1.0)
        val wool4 = local.clone().add(1.0, 2.0, 1.0)
        val wool5 = local.clone().add(-1.0, 2.0, 1.0)
        val wool6 = local.clone().add(0.0, 2.0, -1.0)
        val wool7 = local.clone().add(1.0, 2.0, -1.0)
        val wool8 = local.clone().add(-1.0, 2.0, -1.0)
        val bluewool1 = local.clone().add(2.0, 2.0, 0.0)
        val bluewool2 = local.clone().add(-2.0, 2.0, 0.0)
        val bluewool3 = local.clone().add(-2.0, 2.0, -1.0)
        val bluewool4 = local.clone().add(-2.0, 2.0, 1.0)
        val bluewool5 = local.clone().add(0.0, 2.0, 2.0)
        val bluewool6 = local.clone().add(-1.0, 2.0, 2.0)
        val bluewool7 = local.clone().add(1.0, 2.0, 2.0)
        val bluewool8 = local.clone().add(2.0, 2.0, 2.0)
        val bluewool9 = local.clone().add(-2.0, 2.0, 2.0)
        val bluewool10 = local.clone().add(2.0, 2.0, 1.0)
        val bluewool11 = local.clone().add(2.0, 2.0, -1.0)
        val bluewool12 = local.clone().add(0.0, 2.0, -2.0)
        val bluewool13 = local.clone().add(1.0, 2.0, -2.0)
        val bluewool14 = local.clone().add(-1.0, 2.0, -2.0)
        val bluewool15 = local.clone().add(2.0, 2.0, -2.0)
        val bluewool16 = local.clone().add(-2.0, 2.0, -2.0)
        val bluewool17 = local.clone().add(-2.0, 2.0, -2.0)
        val bluewool18 = local.clone().add(-2.0, 2.0, 2.0)

        if (fence1.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (fence2.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (fence3.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (fence4.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (stair1.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (stair2.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (centerwool.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (wool1.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (wool2.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (wool3.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (wool4.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (wool5.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (wool6.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (wool7.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (wool8.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (bluewool1.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (bluewool2.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (bluewool3.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (bluewool4.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (bluewool5.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (bluewool6.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (bluewool7.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (bluewool8.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (bluewool9.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (bluewool10.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (bluewool11.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (bluewool12.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (bluewool13.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (bluewool14.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (bluewool15.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (bluewool16.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (bluewool17.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (bluewool18.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }

        if (cooldown.cooldown(player)) {
            val user = player.user
            player.sendMessage("§aVocê ativou a engenhoca Pula-Pula! Duração: §f35s")
            GadgetSystem.putActiveGadget(player)
            Mine.broadcast("§6[Cosméticos] §aO jogador ${user.nick} §aativou um Pula-Pula neste lobby!")

            val localstand = local.clone().add(0.5, 4.0, 0.5)
            fence1.block.type = Material.FENCE
            fence2.block.type = Material.FENCE
            fence3.block.type = Material.FENCE
            fence4.block.type = Material.FENCE
            stair1.block.type = Material.WOOD_STAIRS
            stair2.block.type = Material.WOOD_STAIRS

            centerwool.block.type = Material.WOOL
            centerwool.block.data = 15.toByte()
            wool1.block.type = Material.WOOL
            wool1.block.data = 15.toByte()
            wool2.block.type = Material.WOOL
            wool2.block.data = 15.toByte()
            wool3.block.type = Material.WOOL
            wool3.block.data = 15.toByte()
            wool4.block.type = Material.WOOL
            wool4.block.data = 15.toByte()
            wool5.block.type = Material.WOOL
            wool5.block.data = 15.toByte()
            wool6.block.type = Material.WOOL
            wool6.block.data = 15.toByte()
            wool7.block.type = Material.WOOL
            wool7.block.data = 15.toByte()
            wool8.block.type = Material.WOOL
            wool8.block.data = 15.toByte()

            bluewool1.block.type = Material.WOOL
            bluewool1.block.data = 11.toByte()
            bluewool2.block.type = Material.WOOL
            bluewool2.block.data = 11.toByte()
            bluewool3.block.type = Material.WOOL
            bluewool3.block.data = 11.toByte()
            bluewool4.block.type = Material.WOOL
            bluewool4.block.data = 11.toByte()
            bluewool5.block.type = Material.WOOL
            bluewool5.block.data = 11.toByte()
            bluewool6.block.type = Material.WOOL
            bluewool6.block.data = 11.toByte()
            bluewool7.block.type = Material.WOOL
            bluewool7.block.data = 11.toByte()
            bluewool8.block.type = Material.WOOL
            bluewool8.block.data = 11.toByte()
            bluewool9.block.type = Material.WOOL
            bluewool9.block.data = 11.toByte()
            bluewool10.block.type = Material.WOOL
            bluewool10.block.data = 11.toByte()
            bluewool11.block.type = Material.WOOL
            bluewool11.block.data = 11.toByte()
            bluewool12.block.type = Material.WOOL
            bluewool12.block.data = 11.toByte()
            bluewool13.block.type = Material.WOOL
            bluewool13.block.data = 11.toByte()
            bluewool14.block.type = Material.WOOL
            bluewool14.block.data = 11.toByte()
            bluewool15.block.type = Material.WOOL
            bluewool15.block.data = 11.toByte()
            bluewool16.block.type = Material.WOOL
            bluewool16.block.data = 11.toByte()
            bluewool17.block.type = Material.WOOL
            bluewool17.block.data = 11.toByte()
            bluewool18.block.type = Material.WOOL
            bluewool18.block.data = 11.toByte()

            val stand = local.world.spawn(localstand, ArmorStand::class.java)
            stand.isVisible = false
            stand.customName = "§9Pula-Pula §7de ${user.nick}"
            stand.isCustomNameVisible = true
            stand.setGravity(false)
            player.teleport(localstand.clone().subtract(0.0, 0.8, 0.0))

            object : BukkitRunnable() {
                override fun run() {
                    player.sendMessage("§cSeu Pula-Pula foi removido.")
                    GadgetSystem.removeActiveGadget(player)
                    Mine.broadcast("§6[Cosméticos] §cO Pula-Pula de ${user.nick} §cfoi removido.")
                    fence1.block.type = Material.AIR
                    fence2.block.type = Material.AIR
                    fence3.block.type = Material.AIR
                    fence4.block.type = Material.AIR
                    stair1.block.type = Material.AIR
                    stair2.block.type = Material.AIR

                    centerwool.block.type = Material.AIR
                    wool1.block.type = Material.AIR
                    wool2.block.type = Material.AIR
                    wool3.block.type = Material.AIR
                    wool4.block.type = Material.AIR
                    wool5.block.type = Material.AIR
                    wool6.block.type = Material.AIR
                    wool7.block.type = Material.AIR
                    wool8.block.type = Material.AIR

                    bluewool1.block.type = Material.AIR
                    bluewool2.block.type = Material.AIR
                    bluewool3.block.type = Material.AIR
                    bluewool4.block.type = Material.AIR
                    bluewool5.block.type = Material.AIR
                    bluewool6.block.type = Material.AIR
                    bluewool7.block.type = Material.AIR
                    bluewool8.block.type = Material.AIR
                    bluewool9.block.type = Material.AIR
                    bluewool10.block.type = Material.AIR
                    bluewool11.block.type = Material.AIR
                    bluewool12.block.type = Material.AIR
                    bluewool13.block.type = Material.AIR
                    bluewool14.block.type = Material.AIR
                    bluewool15.block.type = Material.AIR
                    bluewool16.block.type = Material.AIR
                    bluewool17.block.type = Material.AIR
                    bluewool18.block.type = Material.AIR

                    stand.remove()
                }
            }.runTaskLater(MiftCosmetics.instance, 20 * 35)
        }
    }

    init {
        icon = ItemBuilder(Material.SLIME_BLOCK).name("§aEngenhoca: §ePula-Pula")
            .lore(
                "§7Um Pula-Pula será gerado em seu",
                "§7lobby e você poderá pular com todos!"
            )
    }
}