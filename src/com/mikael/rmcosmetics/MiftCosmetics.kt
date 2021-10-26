package com.mikael.rmcosmetics

import com.kirelcodes.miniaturepets.pets.PetManager
import com.mikael.rmcosmetics.commands.MiftCosmeticsCommand
import com.mikael.rmcosmetics.core.*
import com.mikael.rmcosmetics.gadgets.*
import com.mikael.rmcosmetics.listener.CosmeticsListener
import com.mikael.rmcosmetics.listener.CosmeticLoaderListener
import com.mikael.rmcosmetics.listener.PetSystemListener
import com.mikael.rmcosmetics.menu.*
import com.mikael.rmcosmetics.objects.*
import net.eduard.api.lib.modules.BukkitTimeHandler
import net.eduard.api.lib.plugin.IPluginInstance
import net.eduard.redemikael.core.api.miftCore
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Creature
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

class MiftCosmetics : JavaPlugin(), IPluginInstance, BukkitTimeHandler {
    companion object {
        lateinit var instance: MiftCosmetics
    }

    override fun onEnable() {
        instance = this@MiftCosmetics
        val timeStart = System.currentTimeMillis()

        log("Criando tabelas e referências...")
        miftCore.sqlManager.createTable<HatData>()
        miftCore.sqlManager.createReferences<HatData>()
        miftCore.sqlManager.createTable<GadgetData>()
        miftCore.sqlManager.createReferences<GadgetData>()
        miftCore.sqlManager.createTable<ParticleData>()
        miftCore.sqlManager.createReferences<ParticleData>()
        miftCore.sqlManager.createTable<AnimatedHatData>()
        miftCore.sqlManager.createReferences<AnimatedHatData>()
        miftCore.sqlManager.createTable<ClosetData>()
        miftCore.sqlManager.createReferences<ClosetData>()
        miftCore.sqlManager.createTable<CompanionData>()
        miftCore.sqlManager.createReferences<CompanionData>()
        miftCore.sqlManager.createTable<BannerData>()
        miftCore.sqlManager.createReferences<BannerData>()
        miftCore.sqlManager.createTable<MiftPetData>()
        miftCore.sqlManager.createReferences<MiftPetData>()

        log("Carregando sistemas...")
        MiftCosmeticsCommand().registerCommand(this)
        CosmeticsListener().registerListener(this)
        CosmeticLoaderListener().registerListener(this)
        PetSystemListener().registerListener(this)
        MenuCosmetics().registerMenu(this)
        MenuCloset().registerMenu(this)
        MenuConfirmCompanionBuy().registerMenu(this)
        MenuConfirmCompanionBuy2().registerMenu(this)
        MenuSelectCoinTypeCompanion().registerMenu(this)
        MenuConfirmParticleBuy().registerMenu(this)
        MenuConfirmParticleBuy2().registerMenu(this)
        MenuSelectCoinTypeParticle().registerMenu(this)
        MenuConfirmAnimatedHatBuy().registerMenu(this)
        MenuConfirmAnimatedHatBuy2().registerMenu(this)
        MenuSelectCoinTypeAnimatedHat().registerMenu(this)
        for (gadget in GadgetSystem.gadgets) {
            gadget.register(this)
        }

        log("Iniciando timers...")
        asyncTimer(20, 20) {
            for (playerLoop in Bukkit.getOnlinePlayers()) {
                try {
                    if (!PetManager.hasPet(playerLoop)) continue
                    val playerLoc = playerLoop.location
                    val playerPet = PetManager.getPet(playerLoop)
                    val playetPetLoc = playerPet.location
                    val distance = playerLoc.distance(playetPetLoc)
                    if (distance > 15.0) {
                        syncTask {
                            playerPet.navigator.teleport(playerLoc)
                        }
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }

        syncTimer(20, 1) {
            for ((owner, petNav) in PetSystem.spawnedNavigators) {
                try {
                    val pet = PetSystem.spawnedPets[petNav]!!
                    if (pet is Creature && pet.target != null) {
                        pet.target = null
                    }
                    pet.teleport(petNav.location.clone().add(-1.0, 0.0, -1.0))
                    asyncTask {
                        if (petNav.location.distance(owner.location) > 2.5) {
                            if (petNav.location.distance(owner.location) > 15.0) {
                                syncTask {
                                    petNav.target = null
                                    petNav.teleport(owner.location)
                                }
                            } else {
                                syncTask {
                                    petNav.target = owner
                                }
                            }
                        } else {
                            syncTask {
                                petNav.target = null
                            }
                        }
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }

        val timePast = System.currentTimeMillis() - timeStart
        log("§aPlugin ativado com sucesso! (Tempo levado: §f${timePast}ms§a)")
    }

    override fun onDisable() {
        log("Descarregando sistemas...")

        log("Removendo companheiros spawnados...")
        for (playerLoop in Bukkit.getOnlinePlayers()) {
            if (PetManager.hasPet(playerLoop)) {
                playerLoop.sendMessage("§6[Cosméticos] §cSeu companheiro foi removido.")
                PetManager.getPet(playerLoop).remove()
            }
        }

        log("Removendo pets spawnados...")
        for ((owner, petNav) in PetSystem.spawnedNavigators) {
            val pet = PetSystem.spawnedPets[petNav]!!
            pet.remove()
            petNav.remove()
            owner.sendMessage("§6[Cosméticos] §cSeu pet foi removido.")
        }
        PetSystem.spawnedPets.clear()
        PetSystem.spawnedNavigators.clear()

        log("Removendo engenhocas ativas...")
        for (bloco in GadgetSystem.blocksList) {
            bloco.block.type = Material.AIR
        }
        RabbitsFamilyGadget.instance.removeActiveGadgets()
        FireworkShowGadget.instance.removeActiveGadgets()
        BombManGadget.instance.removeActiveGadgets()
        ThunderGadget.instance.removeActiveGadgets()
        EggRainfallGadget.instance.removeActiveGadgets()
        FlyingHorseGadget.instance.removeActiveGadgets()
        // Ender dragon gadget ta desabilitado temporariamente
        // EnderDragonGadget.instance.removeActiveGadgets()
        // Engenhoca lançador não precisa ser removida pois é fogo de artifício
        GhostsGadget.instance.removeActiveGadgets()
        GoldFountainGadget.instance.removeActiveGadgets()
        BreadsGadget.instance.removeActiveGadgets()
        CreeperGadget.instance.removeActiveGadgets()
        GiftGadget.instace.removeActiveGadgets()
        // DiscothequeGadget.instance.removeActiveGadgets()
        BatsGadget.instance.removeActiveGadgets()
        SpidersGadget.instance.removeActiveGadgets()
        for (world in Bukkit.getWorlds()) {
            StrawManGadget.instance.removeActiveGadgets(world)
        }
        log("§cPlugin desativado!")
    }

    override fun getPlugin(): Any {
        return this
    }

    override fun getSystemName(): String {
        return description.name
    }

    override fun getPluginConnected(): Plugin {
        return this
    }

    fun log(msg: String) {
        Bukkit.getConsoleSender().sendMessage("§b[rmCosmetics] §f${msg}")
    }
}

