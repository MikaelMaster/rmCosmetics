package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.user
import org.bukkit.Material
import org.bukkit.block.Biome
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class SnowStormGadget : Gadget("Tempestade de Neve", listOf(
    "§7Tenha o poder de controlar a neve!",
    "§7Com esta engenhoca, você invocará uma",
    "§7tempestade de neve em todo o lobby que",
    "§7será vista por todos os jogadores."
), ItemBuilder(Material.SNOW_BALL),120, "rmcosmetics.gadget.snowstorm") {

    val cooldown = CooldownManager(20 * 5)
    //val biomesArray : Array<Array<Biome>> = arrayOf()
   // val biomes = mutableMapOf<Int,Map<Int,Biome>>()

    init {
        cooldown.msgCooldown = "§cVocê precisa esperar mais %times para utilizar esta engenhoca novamente!"

    }

    @EventHandler
    fun clicando (event : PlayerInteractEvent){
        if (event.item == null) return
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        if (icon != event.item) return
        val player = event.player
        player.updateInventory()
        if (cooldown.cooldown(player)) {
            val user = player.user

            val local = event.clickedBlock.location
            val localstand = local.clone().add(0.5, 1.0, -0.7)
            val localsnowgolem = local.clone().add(-1.5, 2.5, 0.5)
            val localsnowgolem2 = local.clone().add(1.5, 2.5, 0.5)
            val snowblock = local.clone().add(0.0, 1.0, 0.0)
            val iceblock = local.clone().add(0.0, 2.0, 0.0)

            var raio = 50
            val chunkCentral = local.chunk
          /*  for (loopPlayer in Bukkit.getOnlinePlayers()){
                loopPlayer.sendMessage("Mandando mensagem global")

            }

            for (n in 0 until 15){
                if (n == 14){
                    //
                }
                if (n == 0){

                }
            }

            for (slotHotBar in 0 until 9){

                player.inventory.setItem(slotHotBar , null)

            }
            Pular de 2 em 2 usa 'step'
            for (n in -15 until 20 step 2) {
                if (n == 19) {
                    player.sendMessage("vai acabar o tempo")
                }
            }
            */
            // x =-5

            val block = local.block
            val biome = block.biome
            local.world.weatherDuration = 20*45
            local.world.isThundering = true


            for (x in -raio until +raio){
                //
                for (z in -raio until +raio){

                    for (y in -raio until +raio) {
                        //pegar chunk
                            if (y<0){
                                continue;
                            }


                        val loopBlock = block.getRelative(x,y,z)
                        loopBlock.biome = Biome.COLD_TAIGA

                  }
                }
            }
            for (x in -10 until +10){
                for (z in -10 until +10){
                    local.world.unloadChunk(chunkCentral.x +x, chunkCentral.z + z)
                    local.world.loadChunk(chunkCentral.x +x, chunkCentral.z + z)
                }
            }

            val blocodeneve = snowblock.block.setType(Material.SNOW_BLOCK)
            val blocodegelo = iceblock.block.setType(Material.ICE)

            val golemdeneve = local.world.spawn(localsnowgolem, Snowman::class.java)
            golemdeneve.customName = "§6Golem de Neve 1"

            val golemdeneve2 = local.world.spawn(localsnowgolem2, Snowman::class.java)
            golemdeneve2.customName = "§6Golem de Neve 2"

            val golemdeneve3 = local.world.spawn(localsnowgolem, Snowman::class.java)
            golemdeneve3.customName = "§6Golem de Neve 3"

            val golemdeneve4 = local.world.spawn(localsnowgolem2, Snowman::class.java)
            golemdeneve4.customName = "§6Golem de Neve 4"

            val golemdeneve5 = local.world.spawn(localsnowgolem, Snowman::class.java)
            golemdeneve5.customName = "§6Golem de Neve 5"

            val golemdeneve6 = local.world.spawn(localsnowgolem2, Snowman::class.java)
            golemdeneve6.customName = "§6Golem de Neve 6"

            val zumbi = local.world.spawn(localsnowgolem, Zombie::class.java)
            zumbi.isBaby = false
            zumbi.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20*45, 1))
            zumbi.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 20*45, 10))

            val zumbi2 = local.world.spawn(localsnowgolem2, Zombie::class.java)
            zumbi2.isBaby = false
            zumbi2.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20*45, 1))
            zumbi2.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 20*45, 10))

            val zumbi3 = local.world.spawn(localsnowgolem, Zombie::class.java)
            zumbi3.isBaby = false
            zumbi3.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20*45, 1))
            zumbi3.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 20*45, 10))

            val stand = local.world.spawn(localstand, ArmorStand::class.java)
            stand.isVisible = false
            stand.customName = "§6Tempestade de Neve §7de ${player.name}"
            stand.isCustomNameVisible = true
            stand.setGravity(false)

            player.sendMessage("§aVocê ativou a engenhoca Tempestade de Neve! Duração: §f45s")
            Mine.broadcast("§6[Cosméticos] §a${user.nick} §ainvocou uma enorme Tempestade de Neve neste lobby!")

            object : BukkitRunnable() {
                override fun run() {

                    Mine.broadcast("§6[Cosméticos] §cÉ sol novamente! A Tempestade de Neve invocada por ${user.nick} §cse foi.")
                    val blocodeneve = snowblock.block.setType(Material.AIR)
                    val blocodegelo = iceblock.block.setType(Material.AIR)

                    golemdeneve.remove()
                    golemdeneve2.remove()
                    golemdeneve3.remove()
                    golemdeneve4.remove()
                    golemdeneve5.remove()
                    golemdeneve6.remove()
                    zumbi.remove()
                    zumbi2.remove()
                    zumbi3.remove()

                    // voltar ao bioma normal
                    for (x in -raio until +raio){
                        //
                        for (z in -raio until +raio){

                            for (y in -raio until +raio) {

                                if (y<0){
                                    continue;
                                }
                                //pegar chunk


                                val loopBlock = block.getRelative(x,y,z)
                                loopBlock.biome = biome

                            }
                        }
                    }

                    local.world.weatherDuration = 0
                    local.world.isThundering = false

                    stand.remove()

                }

            }.runTaskLater(MiftCosmetics.instance, 20*45);

        }
    }

    init {

        icon = ItemBuilder(Material.SNOW_BALL)
            .name("§aEngenhoca: §eTempestade de Neve")
            .lore(
                "§7Tenha o poder de controlar a neve!",
                "§7Com esta engenhoca, você invocará uma",
                "§7tempestade de neve em todo o lobby que",
                "§7será vista por todos os jogadores."
            )

    }

}