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
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class RabbitsFamilyGadget : Gadget(
    "Família de Coelhos",
    "raro",
    listOf(
        "§7Entre no clima da páscoa! Invoque",
        "§7uma família de coelhos em nossos lobbies."
    ),
    ItemBuilder().skin("http://textures.minecraft.net/texture/117bffc1972acd7f3b4a8f43b5b6c7534695b8fd62677e0306b2831574b"),
    45,
    "rmcosmetics.gadget.rabbitsfamily"
) {
    companion object {
        lateinit var instance: RabbitsFamilyGadget
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
        val local = event.clickedBlock.location

        val localrabbit = local.clone().add(0.5, 2.5, 0.5)
        val localegg = local.clone().add(0.5, 5.5, 0.5)
        val localstand = local.clone().add(0.0, 1.5, -0.3)
        val localfirework = local.clone().add(0.0, 2.0, 0.0)

        val haybale1 = local.clone().add(0.0, 1.0, 0.0)
        val haybale2 = local.clone().add(1.0, 1.0, 0.0)
        val haybale3 = local.clone().add(0.0, 1.0, -1.0)
        val haybale4 = local.clone().add(0.0, 2.0, -1.0)
        val haybale5 = local.clone().add(-1.0, 1.0, 0.0)
        val slab1 = local.clone().add(0.0, 1.0, 1.0)
        val slab2 = local.clone().add(-1.0, 1.0, 0.0)
        if (haybale1.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (haybale2.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (haybale3.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (haybale4.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (haybale5.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (slab1.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (slab2.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }

        if (cooldown.cooldown(player)) {
            val user = player.user
            player.sendMessage("§aVocê ativou a engenhoca Família de Coelhos! Duração: §f15s")
            GadgetSystem.putActiveGadget(player)

            val stand = local.world.spawn(localstand, ArmorStand::class.java)
            listaDeEntityParaRemover.add(stand)
            stand.isVisible = false
            stand.setGravity(false)
            stand.customName = "§aFamília de Coelhos §7de ${user.nick}"
            stand.isCustomNameVisible = true

            haybale1.block.type = Material.HAY_BLOCK
            GadgetSystem.addBlockToList(haybale1.block.location)
            haybale2.block.type = Material.HAY_BLOCK
            GadgetSystem.addBlockToList(haybale2.block.location)
            haybale3.block.type = Material.HAY_BLOCK
            GadgetSystem.addBlockToList(haybale3.block.location)
            haybale4.block.type = Material.HAY_BLOCK
            GadgetSystem.addBlockToList(haybale4.block.location)
            haybale5.block.type = Material.HAY_BLOCK
            GadgetSystem.addBlockToList(haybale5.block.location)
            slab1.block.type = Material.WOOD_STEP
            GadgetSystem.addBlockToList(slab1.block.location)
            slab2.block.type = Material.WOOD_STEP
            GadgetSystem.addBlockToList(slab2.block.location)

            local.world.spawn(localegg, Egg::class.java)
            local.world.spawn(localegg, Egg::class.java)
            local.world.spawn(localegg, Egg::class.java)

            val zombie = local.world.spawn(localegg, Zombie::class.java)
            listaDeEntityParaRemover.add(zombie)
            zombie.isBaby = true
            zombie.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20 * 3, 1))

            object : BukkitRunnable() {
                override fun run() {
                    listaDeEntityParaRemover.remove(zombie)
                    zombie.remove()
                }
            }.runTaskLater(MiftCosmetics.instance, 20 * 2)

            val rabbit = local.world.spawn(localrabbit, Rabbit::class.java)
            listaDeEntityParaRemover.add(rabbit)
            rabbit.customName = "§6Coelho 1"

            val rabbit2 = local.world.spawn(localrabbit, Rabbit::class.java)
            listaDeEntityParaRemover.add(rabbit2)
            rabbit2.customName = "§6Coelho 2"

            val rabbit3 = local.world.spawn(localrabbit, Rabbit::class.java)
            listaDeEntityParaRemover.add(rabbit3)
            rabbit3.customName = "§6Coelho 3"

            val rabbit4 = local.world.spawn(localrabbit, Rabbit::class.java)
            listaDeEntityParaRemover.add(rabbit4)
            rabbit4.customName = "§6Coelho 4"

            val rabbit5 = local.world.spawn(localrabbit, Rabbit::class.java)
            listaDeEntityParaRemover.add(rabbit5)
            rabbit5.customName = "§6Coelho 5"

            val rabbit6 = local.world.spawn(localrabbit, Rabbit::class.java)
            listaDeEntityParaRemover.add(rabbit6)
            rabbit6.customName = "§6Coelho 6"

            val rabbit7 = local.world.spawn(localrabbit, Rabbit::class.java)
            listaDeEntityParaRemover.add(rabbit7)
            rabbit7.customName = "§6Coelho 7"

            val rabbit8 = local.world.spawn(localrabbit, Rabbit::class.java)
            listaDeEntityParaRemover.add(rabbit8)
            rabbit8.customName = "§6Coelho 8"

            Mine.newFirework(localfirework, 0, Color.LIME, Color.GREEN, true, true, FireworkEffect.Type.BURST)
            Mine.newFirework(localfirework, 1, Color.LIME, Color.GREEN, true, true, FireworkEffect.Type.BALL)

            object : BukkitRunnable() {
                override fun run() {

                    player.sendMessage("§cSua Família de Coelhos foi removida.")
                    GadgetSystem.removeActiveGadget(player)

                    val tnt = local.world.spawn(localfirework, TNTPrimed::class.java)
                    tnt.fuseTicks = 10
                    Mine.newFirework(localfirework, 0, Color.GRAY, Color.GRAY, true, true, FireworkEffect.Type.BURST)
                    Mine.newFirework(localfirework, 0, Color.WHITE, Color.GRAY, true, true, FireworkEffect.Type.BURST)
                    Mine.newFirework(localfirework, 0, Color.GRAY, Color.GRAY, true, true, FireworkEffect.Type.BURST)
                    Mine.newFirework(localfirework, 1, Color.WHITE, Color.GRAY, true, true, FireworkEffect.Type.BALL)
                    Mine.newFirework(localfirework, 1, Color.GRAY, Color.GRAY, true, true, FireworkEffect.Type.BALL)

                    haybale1.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(haybale1.block.location)
                    haybale2.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(haybale2.block.location)
                    haybale3.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(haybale3.block.location)
                    haybale4.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(haybale4.block.location)
                    haybale5.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(haybale5.block.location)
                    slab1.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(slab1.block.location)
                    slab2.block.type = Material.AIR
                    GadgetSystem.removeBlockToList(slab2.block.location)

                    rabbit.remove()
                    listaDeEntityParaRemover.remove(rabbit)
                    rabbit2.remove()
                    listaDeEntityParaRemover.remove(rabbit2)
                    rabbit3.remove()
                    listaDeEntityParaRemover.remove(rabbit3)
                    rabbit4.remove()
                    listaDeEntityParaRemover.remove(rabbit4)
                    rabbit5.remove()
                    listaDeEntityParaRemover.remove(rabbit5)
                    rabbit6.remove()
                    listaDeEntityParaRemover.remove(rabbit6)
                    rabbit7.remove()
                    listaDeEntityParaRemover.remove(rabbit7)
                    rabbit8.remove()
                    listaDeEntityParaRemover.remove(rabbit8)
                    stand.remove()
                    listaDeEntityParaRemover.remove(stand)
                }
            }.runTaskLater(MiftCosmetics.instance, 20 * 15)
        }
    }

    fun removeActiveGadgets() {
        for (itemLoop in listaDeEntityParaRemover) {
            itemLoop.remove()
        }
    }

    init {
        instance = this@RabbitsFamilyGadget
        icon =
            ItemBuilder().skin("http://textures.minecraft.net/texture/117bffc1972acd7f3b4a8f43b5b6c7534695b8fd62677e0306b2831574b")
                .name("§aEngenhoca: §eFamília de Coelhos")
                .lore(
                    "§7Entre no clima da páscoa! Invoque",
                    "§7uma família de coelhos em nossos lobbies."
                )
    }
}