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
import org.bukkit.*
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class StrawManGadget : Gadget(
    "Espantalho Amaldiçoado",
    "divino",
    listOf(
        "§7Você sabia que deixar um espantalho com uma",
        "§7abóbora deixa ele amaldiçoado? Acho que o",
        "§7fazendeiro que criou este espantalho não sabia..."
    ), ItemBuilder(Material.HAY_BLOCK), 120, "rmcosmetics.gadget.strawman"
) {
    companion object {
        lateinit var instance: StrawManGadget
    }

    val listaDeEntityParaRemover = mutableListOf<Entity>()

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

            Mine.broadcast("§6[Cosméticos] §aBuuuu! ${user.nick} §ainvocou a maldição do espantalho neste lobby!")
            player.velocity = Vector(0.0, 1.5, 0.0)
            player.playSound(player.location, Sound.AMBIENCE_CAVE, 2f, 2f)
            player.playSound(player.location, Sound.AMBIENCE_CAVE, 2f, 2f)
            player.playSound(player.location, Sound.AMBIENCE_CAVE, 2f, 2f)
            mundo.time = 16000

            mundo.strikeLightning(local)
            mundo.strikeLightning(local)
            mundo.strikeLightning(local)

            val stand = local.world.spawn(localstand, ArmorStand::class.java)
            listaDeEntityParaRemover.add(stand)
            stand.isVisible = false
            stand.setGravity(false)
            stand.customName = "§6Espantalho Amaldiçoado §7de ${user.nick}"
            stand.isCustomNameVisible = true

            localfence1.block.type = Material.FENCE
            GadgetSystem.addBlockToList(localfence1.block.location)
            localfence2.block.type = Material.FENCE
            GadgetSystem.addBlockToList(localfence2.block.location)
            localfence3.block.type = Material.FENCE
            GadgetSystem.addBlockToList(localfence3.block.location)
            localhaybale.block.type = Material.HAY_BLOCK
            GadgetSystem.addBlockToList(localhaybale.block.location)
            pumpkin.block.type = Material.PUMPKIN
            GadgetSystem.addBlockToList(pumpkin.block.location)

            object : BukkitRunnable() {
                override fun run() {
                    pumpkin.block.type = Material.JACK_O_LANTERN

                    val villager = local.world.spawn(localskeleton, Villager::class.java)
                    listaDeEntityParaRemover.add(villager)
                    villager.customName = "§6Villager 1"

                    val villager2 = local.world.spawn(localskeleton, Villager::class.java)
                    listaDeEntityParaRemover.add(villager2)
                    villager2.customName = "§6Villager 2"

                    val zumbi = local.world.spawn(localskeleton, Zombie::class.java)
                    listaDeEntityParaRemover.add(zumbi)
                    zumbi.customName = "§6Zumbi 1"

                    val zumbi2 = local.world.spawn(localskeleton, Zombie::class.java)
                    listaDeEntityParaRemover.add(zumbi2)
                    zumbi2.customName = "§6Zumbi 2"

                    val zumbi3 = local.world.spawn(localskeleton, Zombie::class.java)
                    listaDeEntityParaRemover.add(zumbi3)
                    zumbi3.customName = "§6Zumbi 3"

                    val bruxa = local.world.spawn(localskeleton, Witch::class.java)
                    listaDeEntityParaRemover.add(bruxa)
                    bruxa.customName = "§6Bruxa 1"

                    val esqueleto = local.world.spawn(localskeleton, Skeleton::class.java)
                    listaDeEntityParaRemover.add(esqueleto)
                    esqueleto.customName = "§6Esqueleto 1"
                    esqueleto.equipment.chestplate = ItemBuilder(Material.DIAMOND_HELMET)

                    val esqueleto2 = local.world.spawn(localskeleton, Skeleton::class.java)
                    listaDeEntityParaRemover.add(esqueleto2)
                    esqueleto2.customName = "§6Esqueleto 2"
                    esqueleto2.equipment.chestplate = ItemBuilder(Material.GOLD_CHESTPLATE)

                    val esqueleto3 = local.world.spawn(localskeleton, Skeleton::class.java)
                    listaDeEntityParaRemover.add(esqueleto3)
                    esqueleto3.customName = "§6Esqueleto 3"
                    esqueleto3.equipment.chestplate = ItemBuilder(Material.LEATHER_LEGGINGS)

                    val esqueleto4 = local.world.spawn(localskeleton, Skeleton::class.java)
                    listaDeEntityParaRemover.add(esqueleto4)
                    esqueleto4.customName = "§6Esqueleto 4"
                    esqueleto4.equipment.chestplate = ItemBuilder(Material.GOLD_BOOTS)

                    val esqueleto5 = local.world.spawn(localskeleton, Skeleton::class.java)
                    listaDeEntityParaRemover.add(esqueleto5)
                    esqueleto5.customName = "§6Esqueleto 5"
                    esqueleto4.equipment.chestplate = ItemBuilder(Material.GOLD_BOOTS)


                    val morcego = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego)
                    morcego.isAwake = true
                    morcego.passenger = player

                    val morcego2 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego2)
                    morcego2.isAwake = true

                    val morcego3 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego3)
                    morcego3.isAwake = true

                    val morcego4 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego4)
                    morcego4.isAwake = true

                    val morcego5 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego5)
                    morcego5.isAwake = true

                    val morcego6 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego6)
                    morcego6.isAwake = true

                    val morcego7 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego7)
                    morcego7.isAwake = true

                    val morcego8 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego8)
                    morcego8.isAwake = true

                    val morcego9 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego9)
                    morcego9.isAwake = true

                    val morcego10 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego10)
                    morcego10.isAwake = true

                    val morcego11 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego11)
                    morcego11.isAwake = true

                    val morcego12 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego12)
                    morcego12.isAwake = true

                    val morcego13 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego13)
                    morcego13.isAwake = true

                    val morcego14 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego14)
                    morcego14.isAwake = true

                    val morcego15 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego15)
                    morcego15.isAwake = true

                    val morcego16 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego16)
                    morcego16.isAwake = true

                    val morcego17 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego17)
                    morcego17.isAwake = true

                    val morcego18 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego18)
                    morcego18.isAwake = true

                    val morcego19 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego19)
                    morcego19.isAwake = true

                    val morcego20 = local.world.spawn(localbat, Bat::class.java)
                    listaDeEntityParaRemover.add(morcego20)
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
                            GadgetSystem.removeBlockToList(localfence1.block.location)
                            localfence2.block.type = Material.AIR
                            GadgetSystem.removeBlockToList(localfence2.block.location)
                            localfence3.block.type = Material.AIR
                            GadgetSystem.removeBlockToList(localfence3.block.location)
                            localhaybale.block.type = Material.AIR
                            GadgetSystem.removeBlockToList(localhaybale.block.location)
                            pumpkin.block.type = Material.AIR
                            GadgetSystem.removeBlockToList(pumpkin.block.location)
                            esqueleto.remove()
                            listaDeEntityParaRemover.remove(esqueleto)
                            esqueleto2.remove()
                            listaDeEntityParaRemover.remove(esqueleto2)
                            esqueleto3.remove()
                            listaDeEntityParaRemover.remove(esqueleto3)
                            esqueleto4.remove()
                            listaDeEntityParaRemover.remove(esqueleto4)
                            esqueleto5.remove()
                            listaDeEntityParaRemover.remove(esqueleto5)
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
                            morcego11.remove()
                            listaDeEntityParaRemover.remove(morcego11)
                            morcego12.remove()
                            listaDeEntityParaRemover.remove(morcego12)
                            morcego13.remove()
                            listaDeEntityParaRemover.remove(morcego13)
                            morcego14.remove()
                            listaDeEntityParaRemover.remove(morcego14)
                            morcego15.remove()
                            listaDeEntityParaRemover.remove(morcego15)
                            morcego16.remove()
                            listaDeEntityParaRemover.remove(morcego16)
                            morcego17.remove()
                            listaDeEntityParaRemover.remove(morcego17)
                            morcego18.remove()
                            listaDeEntityParaRemover.remove(morcego18)
                            morcego19.remove()
                            listaDeEntityParaRemover.remove(morcego19)
                            morcego20.remove()
                            listaDeEntityParaRemover.remove(morcego20)
                            bruxa.remove()
                            listaDeEntityParaRemover.remove(bruxa)
                            villager.remove()
                            listaDeEntityParaRemover.remove(villager)
                            villager2.remove()
                            listaDeEntityParaRemover.remove(villager2)
                            zumbi.remove()
                            listaDeEntityParaRemover.remove(zumbi)
                            zumbi2.remove()
                            listaDeEntityParaRemover.remove(zumbi2)
                            zumbi3.remove()
                            listaDeEntityParaRemover.remove(zumbi3)
                            stand.remove()
                            listaDeEntityParaRemover.remove(stand)
                            mundo.getEntitiesByClass(Arrow::class.java).forEach(Entity::remove)
                            mundo.strikeLightning(local)
                            mundo.strikeLightning(local)
                            mundo.strikeLightning(local)
                            Mine.broadcast("§6[Cosméticos] §cA maldição do espantalho invocada por ${user.nick} §cse foi em um estouro.")
                            mundo.time = 1000
                        }
                    }.runTaskLater(MiftCosmetics.instance, 20 * 35);
                }

            }.runTaskLater(MiftCosmetics.instance, 20);
        }
    }

    fun removeActiveGadgets(world: World) {
        for (itemLoop in listaDeEntityParaRemover) {
            world.getEntitiesByClass(Arrow::class.java).forEach(Entity::remove)
            itemLoop.remove()
        }
    }

    init {
        instance = this@StrawManGadget
        icon = ItemBuilder(Material.HAY_BLOCK).name("§aEngenhoca: §eEspantalho Amaldiçoado")
            .lore(
                "§7Você sabia que deixar um espantalho com uma",
                "§7abóbora deixa ele amaldiçoado? Acho que o",
                "§7fazendeiro que criou este espantalho não sabia..."
            )
    }
}