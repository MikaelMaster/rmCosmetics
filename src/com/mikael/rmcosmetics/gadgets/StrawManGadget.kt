package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.user
import org.bukkit.*
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable

class StrawManGadget : Gadget(
    "Espantalho Amaldiçoado", listOf(
        "§7Você sabia que deixar um espantalho com uma",
        "§7abóbora deixa ele amaldiçoado? Acho que o",
        "§7fazendeiro que criou este espantalho não sabia..."
    ), ItemBuilder(Material.HAY_BLOCK), 120, "rmcosmetics.gadget.strawman"
) {

    val cooldown = CooldownManager(20 * 120)

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
        val localskeleton = local.clone().add(0.0, 1.0, -2.0)
        val localfence1 = local.clone().add(0.0, 1.0, 0.0)
        val localfence2 = local.clone().add(1.0, 2.0, 0.0)
        val localfence3 = local.clone().add(-1.0, 2.0, 0.0)
        val localhaybale = local.clone().add(0.5, 2.0, 0.0)
        val pumpkin = local.clone().add(0.0, 3.0, 0.0)
        val localbat = local.clone().add(0.0, 4.0, 1.0)
        val localstand = local.clone().add(0.5, 1.8, -0.3)

        if (localskeleton.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (localfence1.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (localfence2.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (localfence3.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (localhaybale.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (pumpkin.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (localbat.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }
        if (localstand.block.type != Material.AIR) {
            player.sendMessage("§cNão existe espaço o suficiente para ativar essa engenhoca aqui!")
            return
        }

        if (cooldown.cooldown(player)) {
            val user = player.user
            player.sendMessage("§aVocê ativou a engenhoca Espantalho Amaldiçoado! Duração: §f35s")
            GadgetSystem.putActiveGadget(player)
            val mundo = player.world
            val mundotime = mundo.time

            Mine.broadcast("§6[Cosméticos] §aBuuuu! ${user.nick} §ainvocou a maldição do espantalho neste lobby!")
            player.playSound(player.location, Sound.AMBIENCE_CAVE, 2f, 2f)
            player.playSound(player.location, Sound.AMBIENCE_CAVE, 2f, 2f)
            player.playSound(player.location, Sound.AMBIENCE_CAVE, 2f, 2f)
            mundo.time = 160000

            mundo.strikeLightning(local)
            mundo.strikeLightning(local)
            mundo.strikeLightning(local)

            val stand = local.world.spawn(localstand, ArmorStand::class.java)
            stand.isVisible = false
            stand.setGravity(false)
            stand.customName = "§6Espantalho Amaldiçoado §7de ${user.nick}"
            stand.isCustomNameVisible = true

            localfence1.block.type = Material.FENCE
            localfence2.block.type = Material.FENCE
            localfence3.block.type = Material.FENCE
            localhaybale.block.type = Material.HAY_BLOCK
            pumpkin.block.type = Material.PUMPKIN

            object : BukkitRunnable() {
                override fun run() {
                    pumpkin.block.type = Material.JACK_O_LANTERN

                    val villager = local.world.spawn(localskeleton, Villager::class.java)
                    villager.customName = "§6Villager 1"

                    val villager2 = local.world.spawn(localskeleton, Villager::class.java)
                    villager2.customName = "§6Villager 2"

                    val zumbi = local.world.spawn(localskeleton, Zombie::class.java)
                    zumbi.customName = "§6Zumbi 1"

                    val zumbi2 = local.world.spawn(localskeleton, Zombie::class.java)
                    zumbi2.customName = "§6Zumbi 2"

                    val zumbi3 = local.world.spawn(localskeleton, Zombie::class.java)
                    zumbi3.customName = "§6Zumbi 3"

                    val bruxa = local.world.spawn(localskeleton, Witch::class.java)
                    bruxa.customName = "§6Bruxa 1"

                    val esqueleto = local.world.spawn(localskeleton, Skeleton::class.java)
                    esqueleto.customName = "§6Esqueleto 1"
                    esqueleto.equipment.chestplate = ItemBuilder(Material.DIAMOND_HELMET)

                    val esqueleto2 = local.world.spawn(localskeleton, Skeleton::class.java)
                    esqueleto2.customName = "§6Esqueleto 2"
                    esqueleto2.equipment.chestplate = ItemBuilder(Material.GOLD_CHESTPLATE)

                    val esqueleto3 = local.world.spawn(localskeleton, Skeleton::class.java)
                    esqueleto3.customName = "§6Esqueleto 3"
                    esqueleto3.equipment.chestplate = ItemBuilder(Material.LEATHER_LEGGINGS)

                    val esqueleto4 = local.world.spawn(localskeleton, Skeleton::class.java)
                    esqueleto4.customName = "§6Esqueleto 4"
                    esqueleto4.equipment.chestplate = ItemBuilder(Material.GOLD_BOOTS)

                    val esqueleto5 = local.world.spawn(localskeleton, Skeleton::class.java)
                    esqueleto5.customName = "§6Esqueleto 5"
                    esqueleto4.equipment.chestplate = ItemBuilder(Material.GOLD_BOOTS)


                    val morcego = local.world.spawn(localbat, Bat::class.java)
                    morcego.isAwake = true
                    morcego.passenger = player

                    val morcego2 = local.world.spawn(localbat, Bat::class.java)
                    morcego2.isAwake = true

                    val morcego3 = local.world.spawn(localbat, Bat::class.java)
                    morcego3.isAwake = true

                    val morcego4 = local.world.spawn(localbat, Bat::class.java)
                    morcego4.isAwake = true

                    val morcego5 = local.world.spawn(localbat, Bat::class.java)
                    morcego5.isAwake = true

                    val morcego6 = local.world.spawn(localbat, Bat::class.java)
                    morcego6.isAwake = true

                    val morcego7 = local.world.spawn(localbat, Bat::class.java)
                    morcego7.isAwake = true

                    val morcego8 = local.world.spawn(localbat, Bat::class.java)
                    morcego8.isAwake = true

                    val morcego9 = local.world.spawn(localbat, Bat::class.java)
                    morcego9.isAwake = true

                    val morcego10 = local.world.spawn(localbat, Bat::class.java)
                    morcego10.isAwake = true

                    val morcego11 = local.world.spawn(localbat, Bat::class.java)
                    morcego11.isAwake = true

                    val morcego12 = local.world.spawn(localbat, Bat::class.java)
                    morcego12.isAwake = true

                    val morcego13 = local.world.spawn(localbat, Bat::class.java)
                    morcego13.isAwake = true

                    val morcego14 = local.world.spawn(localbat, Bat::class.java)
                    morcego14.isAwake = true

                    val morcego15 = local.world.spawn(localbat, Bat::class.java)
                    morcego15.isAwake = true

                    val morcego16 = local.world.spawn(localbat, Bat::class.java)
                    morcego16.isAwake = true

                    val morcego17 = local.world.spawn(localbat, Bat::class.java)
                    morcego17.isAwake = true

                    val morcego18 = local.world.spawn(localbat, Bat::class.java)
                    morcego18.isAwake = true

                    val morcego19 = local.world.spawn(localbat, Bat::class.java)
                    morcego19.isAwake = true

                    val morcego20 = local.world.spawn(localbat, Bat::class.java)
                    morcego20.isAwake = true

                    object : BukkitRunnable() {
                        override fun run() {
                            if (morcego.isDead) {
                                cancel()
                            }
                            mundo.strikeLightning(local)
                        }
                    }.runTaskTimer(MiftCosmetics.instance, 20, 20)

                    object : BukkitRunnable() {
                        override fun run() {
                            player.sendMessage("§cSeu Espantalho Amaldiçoado foi removido.")
                            GadgetSystem.removeActiveGadget(player)
                            val tnt = local.world.spawn(localskeleton, TNTPrimed::class.java)
                            tnt.fuseTicks = 10
                            Mine.newFirework(
                                localskeleton,
                                0,
                                Color.GRAY,
                                Color.GRAY,
                                true,
                                true,
                                FireworkEffect.Type.BURST
                            )
                            Mine.newFirework(
                                localskeleton,
                                0,
                                Color.WHITE,
                                Color.GRAY,
                                true,
                                true,
                                FireworkEffect.Type.BURST
                            )
                            Mine.newFirework(
                                localskeleton,
                                0,
                                Color.GRAY,
                                Color.GRAY,
                                true,
                                true,
                                FireworkEffect.Type.BURST
                            )
                            Mine.newFirework(
                                localskeleton,
                                1,
                                Color.WHITE,
                                Color.GRAY,
                                true,
                                true,
                                FireworkEffect.Type.BALL
                            )
                            Mine.newFirework(
                                localskeleton,
                                1,
                                Color.GRAY,
                                Color.GRAY,
                                true,
                                true,
                                FireworkEffect.Type.BALL
                            )
                            localfence1.block.type = Material.AIR
                            localfence2.block.type = Material.AIR
                            localfence3.block.type = Material.AIR
                            localhaybale.block.type = Material.AIR
                            pumpkin.block.type = Material.AIR
                            esqueleto.remove()
                            esqueleto2.remove()
                            esqueleto3.remove()
                            esqueleto4.remove()
                            esqueleto5.remove()
                            morcego.remove()
                            morcego2.remove()
                            morcego3.remove()
                            morcego4.remove()
                            morcego5.remove()
                            morcego6.remove()
                            morcego7.remove()
                            morcego8.remove()
                            morcego9.remove()
                            morcego10.remove()
                            morcego11.remove()
                            morcego12.remove()
                            morcego13.remove()
                            morcego14.remove()
                            morcego15.remove()
                            morcego16.remove()
                            morcego17.remove()
                            morcego18.remove()
                            morcego19.remove()
                            morcego20.remove()
                            bruxa.remove()
                            villager.remove()
                            villager2.remove()
                            zumbi.remove()
                            zumbi2.remove()
                            zumbi3.remove()
                            stand.remove()
                            mundo.getEntitiesByClass(Arrow::class.java).forEach(Entity::remove)
                            mundo.strikeLightning(local)
                            mundo.strikeLightning(local)
                            mundo.strikeLightning(local)
                            Mine.broadcast("§6[Cosméticos] §cA maldição do espantalho invocada por ${user.nick} §cse foi em um estouro.")
                            mundo.time = mundotime
                        }

                    }.runTaskLater(MiftCosmetics.instance, 20 * 35);

                }

            }.runTaskLater(MiftCosmetics.instance, 20);

        }
    }

    init {
        icon = ItemBuilder(Material.HAY_BLOCK).name("§aEngenhoca: §eEspantalho Amaldiçoado")
            .lore(
                "§7Você sabia que deixar um espantalho com uma",
                "§7abóbora deixa ele amaldiçoado? Acho que o",
                "§7fazendeiro que criou este espantalho não sabia..."
            )
    }
}