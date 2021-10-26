package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.api.lib.modules.Extra
import net.eduard.redemikael.core.spigot.CoreMain
import net.eduard.redemikael.core.user
import net.eduard.redemikael.parkour.isPlaying
import org.bukkit.entity.CaveSpider
import org.bukkit.entity.Entity
import org.bukkit.entity.Spider
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class SpidersGadget : Gadget(
    "Aranhas!",
    "epico",
    listOf(
        "§7Invoque diversas aranhas em nossos",
        "§7lobbies e assuste todos!",
    ),
    ItemBuilder().skin("http://textures.minecraft.net/texture/5617f7dd5ed16f3bd186440517cd440a170015b1cc6fcb2e993c05de33f"),
    45,
    "rmcosmetics.gadget.spiders"
) {
    companion object {
        lateinit var instance: SpidersGadget
    }

    val listaDeEntityParaRemover = mutableListOf<Entity>()

    val cooldown = CooldownManager(20 * 45)

    init {
        instance = this@SpidersGadget
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
            player.sendMessage("§aVocê ativou a engenhoca Aranhas! Duração: §f15s")
            GadgetSystem.putActiveGadget(player)
            val locToSpawn = player.eyeLocation.clone().add(0.0, 2.0, 0.0)
            val world = player.world
            val spidersQuantity = Extra.getRandomInt(5, 8)

            val listaDeAranhas = mutableListOf<Entity>()

            world.strikeLightningEffect(player.location)
            world.strikeLightningEffect(player.location)
            player.velocity = Vector(0.0, 1.5, 0.0)

            MiftCosmetics.instance.syncDelay(10) {
                val user = player.user
                for (spider in 1..spidersQuantity / 2) {
                    val aranha = world.spawn(locToSpawn, Spider::class.java)
                    listaDeEntityParaRemover.add(aranha)
                    listaDeAranhas.add(aranha)
                    aranha.customName = "§5Aranha §7de ${user.nick}"
                    aranha.isCustomNameVisible = true
                }

                for (spider in 1..spidersQuantity / 2) {
                    val aranha = world.spawn(locToSpawn, CaveSpider::class.java)
                    listaDeEntityParaRemover.add(aranha)
                    listaDeAranhas.add(aranha)
                    aranha.customName = "§5Aranha das Cavernas §7de ${user.nick}"
                    aranha.isCustomNameVisible = true
                }
                listaDeAranhas.random().passenger = player

                object : BukkitRunnable() {
                    override fun run() {
                        for (entityLoop in listaDeAranhas) {
                            listaDeEntityParaRemover.remove(entityLoop)
                            entityLoop.remove()
                        }
                        player.velocity = Vector(0.0, 0.5, 0.0)
                        GadgetSystem.removeActiveGadget(player)
                        player.sendMessage("§cSuas Aranhas foram removidas.")
                    }
                }.runTaskLater(MiftCosmetics.instance, 20 * 15)
            }
        }
    }

    fun removeActiveGadgets() {
        for (entityLoop in listaDeEntityParaRemover) {
            entityLoop.remove()
        }
    }

    init {
        icon =
            ItemBuilder().skin("http://textures.minecraft.net/texture/5617f7dd5ed16f3bd186440517cd440a170015b1cc6fcb2e993c05de33f")
                .name("§aEngenhoca: §eAranhas!")
                .lore(
                    "§7Invoque diversas aranhas em nossos",
                    "§7lobbies e assuste todos!",
                )
    }
}