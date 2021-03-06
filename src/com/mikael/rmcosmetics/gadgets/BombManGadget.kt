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
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class BombManGadget : Gadget(
    "Homem Bomba",
    "raro",
    listOf(
        "§7Tenha o poder de invocar TNTs que",
        "§7causarão uma enorme explosão no",
        "§7meio dos lobbies e jogará todos",
        "§7jogadores próximos para os ares!"
    ), ItemBuilder(Material.TNT), 35, "rmcosmetics.gadget.bombman"
) {
    companion object {
        lateinit var instance: BombManGadget
    }

    val listaDeEntityParaRemover = mutableListOf<Entity>()

    val cooldown = CooldownManager(20 * 35)

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
            val user = player.user
            player.sendMessage("§aVocê ativou a engenhoca Homem Bomba!")
            GadgetSystem.putActiveGadget(player)

            val local = event.clickedBlock.location
            val localtnt = local.clone().add(0.5, 1.0, 0.5)
            val localtnt2 = local.clone().add(1.5, 1.0, 0.5)
            val localtnt3 = local.clone().add(-1.5, 1.0, 0.5)
            val localtnt4 = local.clone().add(0.5, 1.0, 1.5)
            val localtnt5 = local.clone().add(0.5, 1.0, -1.5)
            val localstand = local.clone().add(0.5, 0.5, 0.5)

            val stand = local.world.spawn(localstand, ArmorStand::class.java)
            listaDeEntityParaRemover.add(stand)
            stand.isVisible = false
            stand.customName = "§cTNTs §7de ${user.nick}"
            stand.isCustomNameVisible = true
            stand.setGravity(false)

            local.world.spawn(localtnt, TNTPrimed::class.java)
            local.world.spawn(localtnt2, TNTPrimed::class.java)
            local.world.spawn(localtnt3, TNTPrimed::class.java)
            local.world.spawn(localtnt4, TNTPrimed::class.java)
            local.world.spawn(localtnt5, TNTPrimed::class.java)
            object : BukkitRunnable() {
                override fun run() {
                    Mine.newFirework(localtnt, 0, Color.GRAY, Color.GRAY, true, true, FireworkEffect.Type.BURST)
                    Mine.newFirework(localtnt, 0, Color.GRAY, Color.WHITE, true, true, FireworkEffect.Type.BALL)
                    Mine.newFirework(localtnt2, 0, Color.GRAY, Color.GRAY, true, true, FireworkEffect.Type.BURST)
                    Mine.newFirework(localtnt2, 0, Color.GRAY, Color.WHITE, true, true, FireworkEffect.Type.BALL)
                    Mine.newFirework(localtnt3, 0, Color.GRAY, Color.GRAY, true, true, FireworkEffect.Type.BURST)
                    Mine.newFirework(localtnt3, 0, Color.GRAY, Color.WHITE, true, true, FireworkEffect.Type.BALL)
                    Mine.newFirework(localtnt4, 0, Color.GRAY, Color.GRAY, true, true, FireworkEffect.Type.BURST)
                    Mine.newFirework(localtnt4, 0, Color.GRAY, Color.WHITE, true, true, FireworkEffect.Type.BALL)
                    Mine.newFirework(localtnt5, 0, Color.GRAY, Color.GRAY, true, true, FireworkEffect.Type.BURST)
                    Mine.newFirework(localtnt5, 0, Color.GRAY, Color.WHITE, true, true, FireworkEffect.Type.BALL)
                    for (entity in player.world.getNearbyEntities(localstand, 5.0, 5.0, 5.0)) {
                        if (entity.type == EntityType.PLAYER) {
                            entity.sendMessage("§6BUUUM! Você foi jogado para os ares pelas TNTs de ${player.name}!")
                            entity.velocity = Vector(0.0, 3.0, 0.0)
                        }
                    }
                    GadgetSystem.removeActiveGadget(player)
                    stand.remove()
                    listaDeEntityParaRemover.remove(stand)
                }

            }.runTaskLater(MiftCosmetics.instance, 75);
        }
    }

    fun removeActiveGadgets() {
        for (itemLoop in listaDeEntityParaRemover) {
            itemLoop.remove()
        }
    }

    init {
        instance = this@BombManGadget
        icon = ItemBuilder(Material.TNT).name("§aEngenhoca: §eHomem Bomba")
            .lore(
                "§7Tenha o poder de invocar TNTs que",
                "§7causarão uma enorme explosão no",
                "§7meio dos lobbies e jogará todos",
                "§7jogadores próximos para os ares!"
            )
    }
}