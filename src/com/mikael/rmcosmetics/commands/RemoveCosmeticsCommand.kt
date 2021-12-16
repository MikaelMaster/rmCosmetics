package com.mikael.rmcosmetics.commands

import com.kirelcodes.miniaturepets.pets.PetManager
import com.mikael.rmcosmetics.core.*
import com.mikael.rmcosmetics.menu.MenuParticles
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.api.MiftCommand
import net.eduard.redemikael.core.api.MiftGroup
import net.eduard.redemikael.core.spigot.CoreMain
import net.eduard.redemikael.core.user
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class RemoveCosmeticsCommand : MiftCommand("removeall", "disableall") {

    init {
        group = MiftGroup.GERENTE
    }

    override fun playerCommand(player: Player, args: Array<String>) {
        for (playerLoop in Bukkit.getOnlinePlayers()) {
            playerLoop.playSound(playerLoop.location, Sound.ANVIL_BREAK, 2f, 1f)
            if (PetManager.hasPet(playerLoop)) {
                playerLoop.sendMessage("§cSeu companheiro atual foi removido.")
                PetManager.getPet(playerLoop).remove()
                CompanionSystem.deselect(playerLoop)
            }
            if (HatSystem.hasSelected(playerLoop)) {
                playerLoop.sendMessage("§cSeu chapéu atual foi removido.")
                HatSystem.deselect(playerLoop)
                playerLoop.inventory.helmet = null
            }
            if (HatAnimatedSystem.hasSelected(playerLoop)) {
                playerLoop.sendMessage("§cSeu chapéu animado atual foi removido.")
                HatAnimatedSystem.deselect(playerLoop)
                playerLoop.inventory.helmet = null
            }

            val closet = ClosetSystem.getPlayerCloset(playerLoop)
            playerLoop.sendMessage("§cToda a sua armadura foi removida.")
            if (closet.helmet != null) {
                playerLoop.inventory.helmet = null
                closet.helmet = null
                closet.helmetName = null
                closet.helmetBright = false
            }
            if (closet.chestplate != null) {
                playerLoop.inventory.chestplate = null
                closet.chestplate = null
                closet.chestplateName = null
                closet.chestplateBright = false
            }
            if (closet.leggings != null) {
                playerLoop.inventory.leggings = null
                closet.leggings = null
                closet.leggingsName = null
                closet.leggingsBright = false
            }
            if (closet.boots != null) {
                playerLoop.inventory.boots = null
                closet.boots = null
                closet.bootsName = null
                closet.bootsBright = false
            }
            closet.updateQueue()
            if (BannerSystem.hasSelected(playerLoop)) {
                playerLoop.sendMessage("§cSeu banner atual foi removido.")
                BannerSystem.deselect(playerLoop)
                playerLoop.inventory.helmet = null
            }
            if (ParticleSystem.hasSelected(playerLoop)) {
                playerLoop.sendMessage("§cSua partícula atual foi removida.")
                MenuParticles.usingEffect.remove(playerLoop)
                ParticleSystem.deselect(playerLoop)
            }
            if (GadgetSystem.hasSelected(playerLoop)) {
                playerLoop.sendMessage("§cSua engenhoca atual foi removida.")
                var slot = 5
                if (CoreMain.instance.getBoolean("is-minigame-lobby")) {
                    slot = 6
                }
                playerLoop.inventory.setItem(slot, ItemBuilder(Material.AIR))
                GadgetSystem.deselect(playerLoop)
            }
        }
        Mine.broadcast("")
        Mine.broadcast(" §6[Cosméticos] §cTodos os cosméticos de todos os jogadores deste lobby foram desativados por ${player.user.visual}§c.")
        Mine.broadcast("")
    }
}