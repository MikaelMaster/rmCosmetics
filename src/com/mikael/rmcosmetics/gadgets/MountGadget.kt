package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.redemikael.core.spigot.CoreMain
import net.eduard.redemikael.core.user
import net.eduard.redemikael.parkour.isPlaying
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.entity.*
import org.bukkit.event.player.PlayerInteractAtEntityEvent

class MountGadget : Gadget(
    "Vaqueiro",
    "comum",
    listOf(
        "§7Você já pensou em como seria andar",
        "§7em outro jogador? Vamos tentar?"
    ), ItemBuilder(Material.FISHING_ROD), 60, "rmcosmetics.gadget.mount"
) {

    val cooldown = CooldownManager(20 * 60)

    init {
        cooldown.msgCooldown = "§cVocê precisa esperar mais %times para utilizar esta engenhoca novamente!"
    }

    @EventHandler
    fun clicando(event: PlayerInteractAtEntityEvent) {
        val player = event.player
        if (event.rightClicked.type != EntityType.PLAYER) return
        if (icon != event.player.itemInHand) return
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
            val playerClicado = event.rightClicked as Player
            val clickedUser = playerClicado.user
            player.sendMessage("§aVocê ativou a engenhoca Vaqueiro! Duração: §f30s")
            GadgetSystem.putActiveGadget(player)
            player.sendMessage("§aMontado agora em: ${clickedUser.visual}")
            playerClicado.playSound(playerClicado.location, Sound.ORB_PICKUP, 2f, 1f)
            playerClicado.sendMessage("§6O jogador ${user.nick} §6ativou a engenhoca Vaqueiro em você!")
            playerClicado.passenger = player

            object : BukkitRunnable() {
                override fun run() {
                    player.sendMessage("§cVocê não está mais andando em ${clickedUser.nick}§c.")
                    playerClicado.sendMessage("§eO jogador ${user.nick} não está mais montado em você.")
                    GadgetSystem.removeActiveGadget(player)
                    playerClicado.passenger = null
                }
            }.runTaskLater(MiftCosmetics.instance, 20 * 30);
        }
    }

    init {
        icon = ItemBuilder(Material.FISHING_ROD).name("§aEngenhoca: §eVaqueiro")
            .lore(
                "§7Você já pensou em como seria andar",
                "§7em outro jogador? Vamos tentar?"
            )
    }
}