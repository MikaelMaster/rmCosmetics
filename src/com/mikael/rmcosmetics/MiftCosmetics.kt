package com.mikael.rmcosmetics

import com.mikael.rmcosmetics.commands.RemoveCosmeticsCommand
import com.mikael.rmcosmetics.commands.VersionCommand
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.gadgets.*
import com.mikael.rmcosmetics.menu.*
import com.mikael.rmcosmetics.objects.*
import net.eduard.api.lib.modules.BukkitTimeHandler
import net.eduard.api.lib.plugin.IPluginInstance
import net.eduard.redemikael.core.api.miftCore
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

class MiftCosmetics : JavaPlugin(), IPluginInstance, BukkitTimeHandler {
    companion object {
        lateinit var instance: MiftCosmetics
    }

    override fun onEnable() {
        instance = this

        val inicio = System.currentTimeMillis()
        Bukkit.getConsoleSender().sendMessage("§b[rmCosmetics] §fCriando tabelas e referências...")
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
        Bukkit.getConsoleSender().sendMessage("§b[rmCosmetics] §fCarregando sistemas...")
        VersionCommand().registerCommand(this)
        RemoveCosmeticsCommand().registerCommand(this)
        CosmeticsListener().register(this)
        MenuCosmetics().register(this)
        MenuCloset().register(this)
        MenuPets().register(this)
        MenuConfirmCompanionBuy().register(this)
        MenuConfirmCompanionBuy2().register(this)
        MenuSelectCoinTypeCompanion().register(this)
        MenuConfirmParticleBuy().register(this)
        MenuConfirmParticleBuy2().register(this)
        MenuSelectCoinTypeParticle().register(this)
        MenuConfirmAnimatedHatBuy().register(this)
        MenuConfirmAnimatedHatBuy2().register(this)
        MenuSelectCoinTypeAnimatedHat().register(this)
        for (gadget in GadgetSystem.gadgets) {
            gadget.register(this)
        }
        Bukkit.getConsoleSender().sendMessage("§b[rmCosmetics] §fIniciando timers...")

        /*
        object : BukkitRunnable() {
            override fun run() {

                for (player in Bukkit.getOnlinePlayers()) {
                    val loc = player.

                    Particle(ParticleType.LARGE_SMOKE, 1, 0f, 0f, 0f, 0f)
                        .create(player, loc)
                }
            }
        }.runTaskTimerAsynchronously(instance, 5, 5)
        */

        val fim = System.currentTimeMillis()
        val tempo = fim - inicio
        Bukkit.getConsoleSender()
            .sendMessage("§b[rmCosmetics] §aPlugin ativado com sucesso! (Tempo levado: §f${tempo}ms§a)")
    }

    override fun onDisable() {
        Bukkit.getConsoleSender().sendMessage("§b[rmCosmetics] §fDescarregando sistemas...")
        Bukkit.getConsoleSender().sendMessage("§b[rmCosmetics] §fRemovendo engenhocas ativas...")

        for (bloco in GadgetSystem.blocksList) {
            bloco.block.type = Material.AIR
        }

        RabbitsFamilyGadget.instance.removeActiveGadgets()
        FireworkShowGadget.instance.removeActiveGadgets()
        BombManGadget.instance.removeActiveGadgets()
        ThunderGadget.instance.removeActiveGadgets()
        EggRainfallGadget.instance.removeActiveGadgets()
        FlyingHorseGadget.instance.removeActiveGadgets()
        EnderDragonGadget.instance.removeActiveGadgets()
        // Engenhoca lançador não precisa ser removida pois é fogo de artifício
        StrawManGadget.instance.removeActiveGadgets()
        GhostsGadget.instance.removeActiveGadgets()
        GoldFountainGadget.instance.removeActiveGadgets()
        BreadsGadget.instance.removeActiveGadgets()
        CreeperGadget.instance.removeActiveGadgets()
        GiftGadget.instace.removeActiveGadgets()
        // DiscothequeGadget.instance.removeActiveGadgets()
        BatsGadget.instance.removeActiveGadgets()
        Bukkit.getConsoleSender().sendMessage("§b[rmCosmetics] §cPlugin desativado!")
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
}

