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
import org.bukkit.block.BlockFace
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class JumpJumpGadget : Gadget(
    "Pula-Pula",
    "epico",
    listOf(
        "§7Um Pula-Pula será gerado em seu",
        "§7lobby e você poderá pular com todos!"
    ), ItemBuilder(Material.SLIME_BLOCK), 80, "rmcosmetics.gadget.jumpjump"
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

            fence1.block.type = Material.FENCE
            GadgetSystem.addBlockToList(fence1.block.location)
            fence2.block.type = Material.FENCE
            GadgetSystem.addBlockToList(fence2.block.location)
            fence3.block.type = Material.FENCE
            GadgetSystem.addBlockToList(fence3.block.location)
            fence4.block.type = Material.FENCE
            GadgetSystem.addBlockToList(fence4.block.location)
            stair1.block.type = Material.WOOD_STAIRS
            GadgetSystem.addBlockToList(stair1.block.location)
            stair2.block.type = Material.WOOD_STAIRS
            GadgetSystem.addBlockToList(stair2.block.location)

            centerwool.block.type = Material.WOOL
            centerwool.block.data = 15.toByte()
            GadgetSystem.addBlockToList(centerwool.block.location)
            wool1.block.type = Material.WOOL
            wool1.block.data = 15.toByte()
            GadgetSystem.addBlockToList(wool1.block.location)
            wool2.block.type = Material.WOOL
            wool2.block.data = 15.toByte()
            GadgetSystem.addBlockToList(wool2.block.location)
            wool3.block.type = Material.WOOL
            wool3.block.data = 15.toByte()
            GadgetSystem.addBlockToList(wool3.block.location)
            wool4.block.type = Material.WOOL
            wool4.block.data = 15.toByte()
            GadgetSystem.addBlockToList(wool4.block.location)
            wool5.block.type = Material.WOOL
            wool5.block.data = 15.toByte()
            GadgetSystem.addBlockToList(wool5.block.location)
            wool6.block.type = Material.WOOL
            wool6.block.data = 15.toByte()
            GadgetSystem.addBlockToList(wool6.block.location)
            wool7.block.type = Material.WOOL
            wool7.block.data = 15.toByte()
            GadgetSystem.addBlockToList(wool7.block.location)
            wool8.block.type = Material.WOOL
            wool8.block.data = 15.toByte()
            GadgetSystem.addBlockToList(wool8.block.location)

            bluewool1.block.type = Material.WOOL
            bluewool1.block.data = 11.toByte()
            GadgetSystem.addBlockToList(bluewool1.block.location)
            bluewool2.block.type = Material.WOOL
            bluewool2.block.data = 11.toByte()
            GadgetSystem.addBlockToList(bluewool2.block.location)
            bluewool3.block.type = Material.WOOL
            bluewool3.block.data = 11.toByte()
            GadgetSystem.addBlockToList(bluewool3.block.location)
            bluewool4.block.type = Material.WOOL
            bluewool4.block.data = 11.toByte()
            GadgetSystem.addBlockToList(bluewool4.block.location)
            bluewool5.block.type = Material.WOOL
            bluewool5.block.data = 11.toByte()
            GadgetSystem.addBlockToList(bluewool5.block.location)
            bluewool6.block.type = Material.WOOL
            bluewool6.block.data = 11.toByte()
            GadgetSystem.addBlockToList(bluewool6.block.location)
            bluewool7.block.type = Material.WOOL
            bluewool7.block.data = 11.toByte()
            GadgetSystem.addBlockToList(bluewool7.block.location)
            bluewool8.block.type = Material.WOOL
            bluewool8.block.data = 11.toByte()
            GadgetSystem.addBlockToList(bluewool8.block.location)
            bluewool9.block.type = Material.WOOL
            bluewool9.block.data = 11.toByte()
            GadgetSystem.addBlockToList(bluewool9.block.location)
            bluewool10.block.type = Material.WOOL
            bluewool10.block.data = 11.toByte()
            GadgetSystem.addBlockToList(bluewool10.block.location)
            bluewool11.block.type = Material.WOOL
            bluewool11.block.data = 11.toByte()
            GadgetSystem.addBlockToList(bluewool11.block.location)
            bluewool12.block.type = Material.WOOL
            bluewool12.block.data = 11.toByte()
            GadgetSystem.addBlockToList(bluewool12.block.location)
            bluewool13.block.type = Material.WOOL
            bluewool13.block.data = 11.toByte()
            GadgetSystem.addBlockToList(bluewool13.block.location)
            bluewool14.block.type = Material.WOOL
            bluewool14.block.data = 11.toByte()
            GadgetSystem.addBlockToList(bluewool14.block.location)
            bluewool15.block.type = Material.WOOL
            bluewool15.block.data = 11.toByte()
            GadgetSystem.addBlockToList(bluewool15.block.location)
            bluewool16.block.type = Material.WOOL
            bluewool16.block.data = 11.toByte()
            GadgetSystem.addBlockToList(bluewool16.block.location)
            bluewool17.block.type = Material.WOOL
            bluewool17.block.data = 11.toByte()
            GadgetSystem.addBlockToList(bluewool17.block.location)
            bluewool18.block.type = Material.WOOL
            bluewool18.block.data = 11.toByte()
            GadgetSystem.addBlockToList(bluewool18.block.location)

            val localPlayer = local.clone().add(0.5, 3.2, 0.5)
            player.teleport(localPlayer)

            object : BukkitRunnable() {
                override fun run() {
                    player.sendMessage("§cSeu Pula-Pula foi removido.")
                    GadgetSystem.removeActiveGadget(player)
                    Mine.broadcast("§6[Cosméticos] §cO Pula-Pula de ${user.nick} §cfoi removido.")
                    fence1.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(fence1.block.location)
                    fence2.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(fence2.block.location)
                    fence3.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(fence3.block.location)
                    fence4.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(fence4.block.location)
                    stair1.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(stair1.block.location)
                    stair2.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(stair2.block.location)

                    centerwool.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(centerwool.block.location)
                    wool1.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(wool1.block.location)
                    wool2.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(wool2.block.location)
                    wool3.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(wool3.block.location)
                    wool4.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(wool4.block.location)
                    wool5.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(wool5.block.location)
                    wool6.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(wool6.block.location)
                    wool7.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(wool7.block.location)
                    wool8.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(wool8.block.location)

                    bluewool1.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(bluewool1.block.location)
                    bluewool2.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(bluewool2.block.location)
                    bluewool3.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(bluewool3.block.location)
                    bluewool4.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(bluewool4.block.location)
                    bluewool5.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(bluewool5.block.location)
                    bluewool6.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(bluewool6.block.location)
                    bluewool7.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(bluewool7.block.location)
                    bluewool8.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(bluewool8.block.location)
                    bluewool9.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(bluewool9.block.location)
                    bluewool10.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(bluewool10.block.location)
                    bluewool11.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(bluewool11.block.location)
                    bluewool12.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(bluewool12.block.location)
                    bluewool13.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(bluewool13.block.location)
                    bluewool14.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(bluewool14.block.location)
                    bluewool15.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(bluewool15.block.location)
                    bluewool16.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(bluewool16.block.location)
                    bluewool17.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(bluewool17.block.location)
                    bluewool18.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(bluewool18.block.location)
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