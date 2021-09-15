package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.redemikael.core.spigot.CoreMain
import net.eduard.redemikael.parkour.isPlaying
import org.bukkit.DyeColor
import org.bukkit.Effect
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.BlockFace
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable

class ExplosiveSheepGadget : Gadget(
    "Ovelha Explosiva",
    "divino",
    listOf(
        "§7Esta ovelha está piscando de mais... Meu deus,",
        "§7ta saindo fomaça, ela vai explodir!",
    ), ItemBuilder(Material.SHEARS), 15, "rmcosmetics.gadget.explosivesheep"
) {
    companion object {
        lateinit var instance: ExplosiveSheepGadget
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
            player.sendMessage("§aVocê ativou a engenhoca Ovelha Explosiva!")
            GadgetSystem.putActiveGadget(player)

            val local = event.clickedBlock.location.add(0.0, 1.0, 0.0)

            val sheep = local.world.spawn(local, Sheep::class.java)
            listaDeEntityParaRemover.add(sheep)
            sheep.isSheared = false
            sheep.customName = "§cOvelha Explosiva"
            sheep.isCustomNameVisible = true
            sheep.color = DyeColor.values().random()

            object : BukkitRunnable() {
                override fun run() {
                    val mundo = sheep.world
                    mundo.playEffect(sheep.eyeLocation, Effect.EXPLOSION_HUGE, 1)
                    mundo.playSound(sheep.eyeLocation, Sound.EXPLODE, 2f, 1f)
                    sheep.remove()
                    listaDeEntityParaRemover.remove(sheep)
                    player.sendMessage("§cSua ovelha explodiu, engenhoca desativada.")
                    GadgetSystem.removeActiveGadget(player)
                }
            }.runTaskLater(MiftCosmetics.instance, 20 * 5)

            object : BukkitRunnable() {
                override fun run() {
                    if (sheep.isDead) {
                        cancel()
                    }
                    val mundo = sheep.world
                    mundo.playEffect(sheep.eyeLocation, Effect.LARGE_SMOKE, 0)
                    mundo.playEffect(sheep.eyeLocation, Effect.LARGE_SMOKE, 0)
                    mundo.playEffect(sheep.location, Effect.LARGE_SMOKE, 0)
                    mundo.playEffect(sheep.location, Effect.LARGE_SMOKE, 0)
                    mundo.playSound(sheep.location, Sound.ITEM_PICKUP, 2f, 2f)
                    sheep.color = DyeColor.values().random()
                }
            }.runTaskTimer(MiftCosmetics.instance, 1, 1)
        }
    }

    fun removeActiveGadgets() {
        for (entity in listaDeEntityParaRemover) {
            entity.remove()
        }
    }

    init {
        instance = this@ExplosiveSheepGadget
        icon = ItemBuilder(Material.SHEARS).name("§aEngenhoca: §eOvelha Explosiva")
            .lore(
                "§7Esta ovelha está piscando de mais... Meu deus,",
                "§7ta saindo fomaça, ela vai explodir!",
            )
    }
}