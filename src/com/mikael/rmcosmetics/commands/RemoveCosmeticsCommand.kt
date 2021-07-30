package com.mikael.rmcosmetics.commands

import com.kirelcodes.miniaturepets.pets.PetManager
import com.mikael.rmcosmetics.core.*
import com.mikael.rmcosmetics.menu.MenuParticles
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.manager.CommandManager
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.soundWhenEffect
import net.eduard.redemikael.core.user
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class RemoveCosmeticsCommand : CommandManager("removeallcosmetics") {


    init {
        permission = "rmcosmetics.cmd.removeall"
        permissionMessage = "§cVocê precisa do Grupo §4Gerente §cou superior para utilizar este comando!"
    }

    override fun playerCommand(player: Player, args: Array<String>) {

        val user = player.user

        player.sendMessage("§aVocê desativou todos os cosméticos de todos os jogadores deste servidor!")
        Mine.broadcast("")
        Mine.broadcast(" §6[Cosméticos] §cTodos os cosméticos de todos os jogadores deste lobby foram desativados por ${user.visual}§c.")
        Mine.broadcast("")

        for (player in Bukkit.getOnlinePlayers()) {

            player.playSound(player.location, Sound.ANVIL_BREAK, 2f, 1f)

            if (PetManager.hasPet(player)) {
                player.sendMessage("§cSeu companheiro atual foi removido.")
                PetManager.getPet(player).remove()
                CompanionSystem.deselect(player)
            }

            if (HatSystem.hasSelected(player)) {
                player.sendMessage("§cSeu chapéu atual foi removido.")
                player.inventory.helmet = null
                HatSystem.deselect(player)
            }

            if (HatAnimatedSystem.hasSelected(player)) {
                player.sendMessage("§cSeu chapéu animado atual foi removido.")
                player.inventory.helmet = null
                HatAnimatedSystem.deselect(player)
            }

            val closet = ClosetSystem.getPlayerCloset(player)
            player.sendMessage("§cToda a sua armadura foi removida.")

            if (closet.helmet != null) {
                player.inventory.helmet = null
                closet.helmet = null
                closet.helmetName = null
                closet.helmetBright = false
            }
            if (closet.chestplate != null) {
                player.inventory.chestplate = null
                closet.chestplate = null
                closet.chestplateName = null
                closet.chestplateBright = false
            }
            if (closet.leggings != null) {
                player.inventory.leggings = null
                closet.leggings = null
                closet.leggingsName = null
                closet.leggingsBright = false
            }
            if (closet.boots != null) {
                player.inventory.boots = null
                closet.boots = null
                closet.bootsName = null
                closet.bootsBright = false
            }
            closet.updateQueue()

            if (BannerSystem.hasSelected(player)) {
                player.sendMessage("§cSeu banner atual foi removido.")
                player.inventory.helmet = null
                BannerSystem.deselect(player)
            }

            if (ParticleSystem.hasSelected(player)) {
                player.sendMessage("§cSua partícula atual foi removida.")
                MenuParticles.usingEffect.remove(player)
                ParticleSystem.deselect(player)
            }

            if (GadgetSystem.hasSelected(player)) {
                player.sendMessage("§cSua engenhoca atual foi removida.")
                player.inventory.setItem(5, ItemBuilder(Material.AIR))
                GadgetSystem.deselect(player)
            }
        }
    }
}