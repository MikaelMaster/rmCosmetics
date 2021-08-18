package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.game.Particle
import net.eduard.api.lib.game.ParticleType
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.api.lib.modules.Mine
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class CreeperGadget : Gadget(
    "Creeper Voador", listOf(
        "§7Lance um creeper para os ares",
        "§7e aproveite um maravilhoso show",
        "§7quando o mesmo chegar no céu!"
    ), ItemBuilder(Material.MONSTER_EGG).data(50), 15, "rmcosmetics.gadget.creeper"
) {
    companion object {
        lateinit var instace: CreeperGadget
    }

    val listaDeEntityParaRemover = mutableListOf<Entity>()

    val cooldown = CooldownManager(20 * 15)

    init {
        cooldown.msgCooldown = "§cVocê precisa esperar mais %times para utilizar esta engenhoca novamente!"
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

        if (cooldown.cooldown(player)) {
            player.sendMessage("§aVocê ativou a engenhoca Creeper Voador!")
            player.sendMessage("§c§lDecolando Creeper!")
            GadgetSystem.putActiveGadget(player)

            val local = event.clickedBlock.location.add(0.0, 1.0, 0.0)

            val creeper = local.world.spawn(local, Creeper::class.java)
            listaDeEntityParaRemover.add(creeper)
            creeper.equipment.helmet = ItemStack(Material.GLASS)
            creeper.customName = "§2Creeper Voador"
            creeper.isPowered = false
            creeper.isCustomNameVisible = true
            creeper.velocity = Vector(0.0, 2.0, 0.0)

            object : BukkitRunnable() {
                override fun run() {
                    val loc = creeper.location

                    val tnt = local.world.spawn(loc, TNTPrimed::class.java)
                    tnt.fuseTicks = 0
                    Particle(ParticleType.LARGE_SMOKE, 100, 0.5f, 1f, 1f, 1f)
                        .create(loc)
                    Mine.newFirework(loc, 0, Color.GRAY, Color.GRAY, true, true, FireworkEffect.Type.BURST)
                    Mine.newFirework(loc, 0, Color.GRAY, Color.WHITE, true, true, FireworkEffect.Type.BURST)
                    Mine.newFirework(loc, 0, Color.GRAY, Color.GRAY, true, true, FireworkEffect.Type.BURST)
                    Mine.newFirework(loc, 0, Color.GRAY, Color.WHITE, true, true, FireworkEffect.Type.BURST)
                    Mine.newFirework(loc, 0, Color.GRAY, Color.GRAY, true, true, FireworkEffect.Type.BURST)
                    Mine.newFirework(loc, 0, Color.GRAY, Color.WHITE, true, true, FireworkEffect.Type.BURST)
                    Mine.newFirework(loc, 0, Color.GRAY, Color.GRAY, true, true, FireworkEffect.Type.BURST)
                    Mine.newFirework(loc, 0, Color.GRAY, Color.WHITE, true, true, FireworkEffect.Type.BURST)
                    Mine.newFirework(loc, 0, Color.GRAY, Color.GRAY, true, true, FireworkEffect.Type.BURST)
                    Mine.newFirework(loc, 0, Color.GRAY, Color.WHITE, true, true, FireworkEffect.Type.BURST)
                    creeper.remove()
                }
            }.runTaskLater(MiftCosmetics.instance, 20)

            object : BukkitRunnable() {
                override fun run() {
                    if (creeper.isDead) {
                        listaDeEntityParaRemover.remove(creeper)
                        GadgetSystem.removeActiveGadget(player)
                        cancel()
                    }
                }
            }.runTaskTimer(MiftCosmetics.instance, 0, 0)
        }
    }

    fun removeActiveGadgets() {
        for (itemLoop in listaDeEntityParaRemover) {
            itemLoop.remove()
        }
    }

    init {
        instace = this@CreeperGadget
        icon = ItemBuilder(Material.MONSTER_EGG).data(50).name("§aEngenhoca: §eCreeper Voador")
            .lore(
                "§7Lance um creeper para os ares",
                "§7e aproveite um maravilhoso show",
                "§7quando o mesmo chegar no céu!"
            )
    }
}