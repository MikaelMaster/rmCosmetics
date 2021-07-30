package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.user
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
    listOf(
        "§7Entre no clima da páscoa! Invoque",
        "§7uma família de coelhos em nossos lobbies."
    ),
    ItemBuilder().skin("http://textures.minecraft.net/texture/117bffc1972acd7f3b4a8f43b5b6c7534695b8fd62677e0306b2831574b"),
    45,
    "rmcosmetics.gadget.rabbitsfamily"
) {

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
            stand.isVisible = false
            stand.setGravity(false)
            stand.customName = "§aFamília de Coelhos §7de ${user.nick}"
            stand.isCustomNameVisible = true

            haybale1.block.type = Material.HAY_BLOCK
            haybale2.block.type = Material.HAY_BLOCK
            haybale3.block.type = Material.HAY_BLOCK
            haybale4.block.type = Material.HAY_BLOCK
            haybale5.block.type = Material.HAY_BLOCK
            slab1.block.type = Material.WOOD_STEP
            slab2.block.type = Material.WOOD_STEP

            local.world.spawn(localegg, Egg::class.java)
            local.world.spawn(localegg, Egg::class.java)
            local.world.spawn(localegg, Egg::class.java)

            val zombie = local.world.spawn(localegg, Zombie::class.java)
            zombie.isBaby = true
            zombie.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20 * 3, 1))

            object : BukkitRunnable() {
                override fun run() {
                    zombie.remove()
                }
            }.runTaskLater(MiftCosmetics.instance, 20 * 2)

            val rabbit = local.world.spawn(localrabbit, Rabbit::class.java)
            rabbit.customName = "§6Coelho 1"

            val rabbit2 = local.world.spawn(localrabbit, Rabbit::class.java)
            rabbit2.customName = "§6Coelho 2"

            val rabbit3 = local.world.spawn(localrabbit, Rabbit::class.java)
            rabbit3.customName = "§6Coelho 3"

            val rabbit4 = local.world.spawn(localrabbit, Rabbit::class.java)
            rabbit4.customName = "§6Coelho 4"

            val rabbit5 = local.world.spawn(localrabbit, Rabbit::class.java)
            rabbit5.customName = "§6Coelho 5"

            val rabbit6 = local.world.spawn(localrabbit, Rabbit::class.java)
            rabbit6.customName = "§6Coelho 6"

            val rabbit7 = local.world.spawn(localrabbit, Rabbit::class.java)
            rabbit7.customName = "§6Coelho 7"

            val rabbit8 = local.world.spawn(localrabbit, Rabbit::class.java)
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
                    haybale2.block.type = Material.AIR
                    haybale3.block.type = Material.AIR
                    haybale4.block.type = Material.AIR
                    haybale5.block.type = Material.AIR
                    slab1.block.type = Material.AIR
                    slab2.block.type = Material.AIR

                    rabbit.remove()
                    rabbit2.remove()
                    rabbit3.remove()
                    rabbit4.remove()
                    rabbit5.remove()
                    rabbit6.remove()
                    rabbit7.remove()
                    rabbit8.remove()
                    stand.remove()
                }
            }.runTaskLater(MiftCosmetics.instance, 20 * 15)
        }
    }

    init {
        icon =
            ItemBuilder().skin("http://textures.minecraft.net/texture/117bffc1972acd7f3b4a8f43b5b6c7534695b8fd62677e0306b2831574b")
                .name("§aEngenhoca: §eFamília de Coelhos")
                .lore(
                    "§7Entre no clima da páscoa! Invoque",
                    "§7uma família de coelhos em nossos lobbies."
                )
    }
}