package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.redemikael.core.spigot.CoreMain
import net.eduard.redemikael.parkour.isPlaying
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class GhostsGadget : Gadget(
    "Orda de Fantasmas",
    "epico",
    listOf(
        "§7Buuuu! Assuste a todos em seu",
        "§7lobby com estes fantasmas!"
    ),
    ItemBuilder().skin("http://textures.minecraft.net/texture/dfa13032fa939f184daa6a4e11e6f3a913e48f250498165c5665ccf49c72a140"),
    45,
    "rmcosmetics.gadget.ghosts"
) {
    companion object {
        lateinit var instance: GhostsGadget
    }

    val listaDeEntityParaRemover = mutableListOf<Entity>()

    val cooldown = CooldownManager(20 * 45)

    init {
        cooldown.msgCooldown = "§cVocê precisa esperar mais %times para utilizar esta engenhoca novamente!"
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
        if (cooldown.cooldown(player)) {
            player.sendMessage("§aVocê invocou uma orda de Fantasmas! Duração dos fantasmas: §f15s")
            GadgetSystem.putActiveGadget(player)

            val local = event.clickedBlock.location
            val mundo = player.world

            val localbat = local.clone().add(0.0, 3.5, 0.0)

            mundo.strikeLightning(local)
            mundo.strikeLightning(local)
            mundo.strikeLightning(local)
            val stand = local.world.spawn(localbat, ArmorStand::class.java)
            listaDeEntityParaRemover.add(stand)
            stand.setGravity(true)
            stand.isVisible = false
            stand.passenger = player
            stand.helmet =
                ItemBuilder().skin("http://textures.minecraft.net/texture/dfa13032fa939f184daa6a4e11e6f3a913e48f250498165c5665ccf49c72a140")

            val stand2 = local.world.spawn(localbat, ArmorStand::class.java)
            listaDeEntityParaRemover.add(stand2)
            stand2.setGravity(true)
            stand2.isVisible = false
            stand2.helmet =
                ItemBuilder().skin("http://textures.minecraft.net/texture/dfa13032fa939f184daa6a4e11e6f3a913e48f250498165c5665ccf49c72a140")

            val stand3 = local.world.spawn(localbat, ArmorStand::class.java)
            listaDeEntityParaRemover.add(stand3)
            stand3.setGravity(true)
            stand3.isVisible = false
            stand3.helmet =
                ItemBuilder().skin("http://textures.minecraft.net/texture/dfa13032fa939f184daa6a4e11e6f3a913e48f250498165c5665ccf49c72a140")

            val stand4 = local.world.spawn(localbat, ArmorStand::class.java)
            listaDeEntityParaRemover.add(stand4)
            stand4.setGravity(true)
            stand4.isVisible = false
            stand4.helmet =
                ItemBuilder().skin("http://textures.minecraft.net/texture/dfa13032fa939f184daa6a4e11e6f3a913e48f250498165c5665ccf49c72a140")

            val stand5 = local.world.spawn(localbat, ArmorStand::class.java)
            listaDeEntityParaRemover.add(stand5)
            stand5.setGravity(true)
            stand5.isVisible = false
            stand5.helmet =
                ItemBuilder().skin("http://textures.minecraft.net/texture/dfa13032fa939f184daa6a4e11e6f3a913e48f250498165c5665ccf49c72a140")

            val stand6 = local.world.spawn(localbat, ArmorStand::class.java)
            listaDeEntityParaRemover.add(stand6)
            stand6.setGravity(true)
            stand6.isVisible = false
            stand6.helmet =
                ItemBuilder().skin("http://textures.minecraft.net/texture/dfa13032fa939f184daa6a4e11e6f3a913e48f250498165c5665ccf49c72a140")

            val stand7 = local.world.spawn(localbat, ArmorStand::class.java)
            listaDeEntityParaRemover.add(stand7)
            stand7.setGravity(true)
            stand7.isVisible = false
            stand7.helmet =
                ItemBuilder().skin("http://textures.minecraft.net/texture/dfa13032fa939f184daa6a4e11e6f3a913e48f250498165c5665ccf49c72a140")

            val stand8 = local.world.spawn(localbat, ArmorStand::class.java)
            listaDeEntityParaRemover.add(stand8)
            stand8.setGravity(true)
            stand8.isVisible = false
            stand8.helmet =
                ItemBuilder().skin("http://textures.minecraft.net/texture/dfa13032fa939f184daa6a4e11e6f3a913e48f250498165c5665ccf49c72a140")

            val stand9 = local.world.spawn(localbat, ArmorStand::class.java)
            listaDeEntityParaRemover.add(stand9)
            stand9.setGravity(true)
            stand9.isVisible = false
            stand9.helmet =
                ItemBuilder().skin("http://textures.minecraft.net/texture/dfa13032fa939f184daa6a4e11e6f3a913e48f250498165c5665ccf49c72a140")

            val stand10 = local.world.spawn(localbat, ArmorStand::class.java)
            listaDeEntityParaRemover.add(stand10)
            stand10.setGravity(true)
            stand10.isVisible = false
            stand10.helmet =
                ItemBuilder().skin("http://textures.minecraft.net/texture/dfa13032fa939f184daa6a4e11e6f3a913e48f250498165c5665ccf49c72a140")

            val morcego = local.world.spawn(localbat, Bat::class.java)
            listaDeEntityParaRemover.add(morcego)
            morcego.isAwake = true
            morcego.passenger = stand
            morcego.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20 * 35, 1))

            val morcego2 = local.world.spawn(localbat, Bat::class.java)
            listaDeEntityParaRemover.add(morcego2)
            morcego2.isAwake = true
            morcego2.passenger = stand2
            morcego2.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20 * 35, 1))

            val morcego3 = local.world.spawn(localbat, Bat::class.java)
            listaDeEntityParaRemover.add(morcego3)
            morcego3.isAwake = true
            morcego3.passenger = stand3
            morcego3.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20 * 35, 1))

            val morcego4 = local.world.spawn(localbat, Bat::class.java)
            listaDeEntityParaRemover.add(morcego4)
            morcego4.isAwake = true
            morcego4.passenger = stand4
            morcego4.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20 * 35, 1))

            val morcego5 = local.world.spawn(localbat, Bat::class.java)
            listaDeEntityParaRemover.add(morcego5)
            morcego5.isAwake = true
            morcego5.passenger = stand5
            morcego5.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20 * 35, 1))

            val morcego6 = local.world.spawn(localbat, Bat::class.java)
            listaDeEntityParaRemover.add(morcego6)
            morcego6.isAwake = true
            morcego6.passenger = stand6
            morcego6.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20 * 35, 1))

            val morcego7 = local.world.spawn(localbat, Bat::class.java)
            listaDeEntityParaRemover.add(morcego7)
            morcego7.isAwake = true
            morcego7.passenger = stand7
            morcego7.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20 * 35, 1))

            val morcego8 = local.world.spawn(localbat, Bat::class.java)
            listaDeEntityParaRemover.add(morcego8)
            morcego8.isAwake = true
            morcego8.passenger = stand8
            morcego8.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20 * 35, 1))

            val morcego9 = local.world.spawn(localbat, Bat::class.java)
            listaDeEntityParaRemover.add(morcego9)
            morcego9.isAwake = true
            morcego9.passenger = stand9
            morcego9.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20 * 35, 1))

            val morcego10 = local.world.spawn(localbat, Bat::class.java)
            listaDeEntityParaRemover.add(morcego10)
            morcego10.isAwake = true
            morcego10.passenger = stand10
            morcego10.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20 * 35, 1))

            MiftCosmetics.instance.syncDelay(20 * 15) {
                player.sendMessage("§cSua Orda de Fantasmas foi removida.")
                GadgetSystem.removeActiveGadget(player)
                morcego.remove()
                listaDeEntityParaRemover.remove(morcego)
                morcego2.remove()
                listaDeEntityParaRemover.remove(morcego2)
                morcego3.remove()
                listaDeEntityParaRemover.remove(morcego3)
                morcego4.remove()
                listaDeEntityParaRemover.remove(morcego4)
                morcego5.remove()
                listaDeEntityParaRemover.remove(morcego5)
                morcego6.remove()
                listaDeEntityParaRemover.remove(morcego6)
                morcego7.remove()
                listaDeEntityParaRemover.remove(morcego7)
                morcego8.remove()
                listaDeEntityParaRemover.remove(morcego8)
                morcego9.remove()
                listaDeEntityParaRemover.remove(morcego9)
                morcego10.remove()
                listaDeEntityParaRemover.remove(morcego10)
                stand.remove()
                listaDeEntityParaRemover.remove(stand)
                stand2.remove()
                listaDeEntityParaRemover.remove(stand2)
                stand3.remove()
                listaDeEntityParaRemover.remove(stand3)
                stand4.remove()
                listaDeEntityParaRemover.remove(stand4)
                stand5.remove()
                listaDeEntityParaRemover.remove(stand5)
                stand6.remove()
                listaDeEntityParaRemover.remove(stand6)
                stand7.remove()
                listaDeEntityParaRemover.remove(stand7)
                stand8.remove()
                listaDeEntityParaRemover.remove(stand8)
                stand9.remove()
                listaDeEntityParaRemover.remove(stand9)
                stand10.remove()
                listaDeEntityParaRemover.remove(stand10)
            }
        }
    }

    fun removeActiveGadgets() {
        for (itemLoop in listaDeEntityParaRemover) {
            itemLoop.remove()
        }
    }

    init {
        instance = this@GhostsGadget
        icon =
            ItemBuilder().skin("http://textures.minecraft.net/texture/dfa13032fa939f184daa6a4e11e6f3a913e48f250498165c5665ccf49c72a140")
                .name("§aEngenhoca: §eOrda de Fantasmas")
                .lore(
                    "§7Buuuu! Assuste a todos em seu",
                    "§7lobby com estes fantasmas!"
                )
    }
}