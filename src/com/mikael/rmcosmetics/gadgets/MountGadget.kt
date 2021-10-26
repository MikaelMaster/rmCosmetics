package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.soundWhenNoSuccess
import net.eduard.redemikael.core.spigot.CoreMain
import net.eduard.redemikael.core.user
import net.eduard.redemikael.parkour.isPlaying
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.entity.*
import org.bukkit.event.player.PlayerInteractAtEntityEvent

class MountGadget : Gadget(
    "Vaqueiro",
    "divino",
    listOf(
        "§7Você já pensou em como seria andar",
        "§7em outro jogador? Vamos tentar?"
    ), ItemBuilder(Material.SADDLE), 15, "rmcosmetics.gadget.mount"
) {

    val cooldown = CooldownManager(20 * 15)

    init {
        cooldown.msgCooldown = "§cVocê precisa esperar mais %times para utilizar esta engenhoca novamente!"
    }

    @EventHandler
    fun clicando(event: PlayerInteractAtEntityEvent) {
        val player = event.player
        if (event.rightClicked.type != EntityType.PLAYER) return
        val jogadorClicadoToCheck = event.rightClicked as Player
        if (!Bukkit.getOnlinePlayers().contains(jogadorClicadoToCheck)) return
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
            GadgetSystem.putActiveGadget(player)
            player.sendMessage("§aMontado agora em: ${clickedUser.visual}")
            playerClicado.playSound(playerClicado.location, Sound.ORB_PICKUP, 2f, 1f)
            playerClicado.sendMessage("§6O jogador ${user.nick} §6ativou a engenhoca Vaqueiro em você!")
            playerClicado.passenger = player

            object : BukkitRunnable() {
                override fun run() {
                    if (playerClicado.passenger != player) {
                        GadgetSystem.removeActiveGadget(player)
                        player.sendMessage("§cVocê se demontou de ${playerClicado.user.visual}§c.")
                        playerClicado.sendMessage("§cO jogador ${player.user.visual} §cse desmontou de você.")
                        cancel()
                    }
                    if (playerClicado.isSneaking) {
                        GadgetSystem.removeActiveGadget(player)
                        player.soundWhenNoSuccess()
                        player.sendMessage("§cO jogador ${playerClicado.user.visual} §cdesmontou você dele.")
                        playerClicado.sendMessage("§cVocê desmontou o jogador ${player.user.visual} §cde você.")
                        playerClicado.eject()
                        cooldown.stopCooldown(player)
                        cancel()
                    }
                }
            }.runTaskTimerAsynchronously(MiftCosmetics.instance, 0, 0)
        }
    }

    init {
        icon = ItemBuilder(Material.SADDLE).name("§aEngenhoca: §eVaqueiro")
            .lore(
                "§7Você já pensou em como seria andar",
                "§7em outro jogador? Vamos tentar?"
            )
    }
}