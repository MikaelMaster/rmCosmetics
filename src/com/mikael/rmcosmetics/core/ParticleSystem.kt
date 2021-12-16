package com.mikael.rmcosmetics.core

import com.mikael.rmcosmetics.objects.CosmeticsData
import com.mikael.rmcosmetics.objects.ParticleCosmetic
import com.mikael.rmcosmetics.objects.ParticleData
import com.mikael.rmcosmetics.particles.SingleParticleHead
import net.eduard.api.lib.game.ParticleType
import net.eduard.redemikael.core.api.miftCore
import net.eduard.redemikael.core.objects.MiftProfile
import net.eduard.redemikael.core.user
import org.bukkit.Material
import org.bukkit.entity.Player

object ParticleSystem {

    /**
     * Sistema de Partículas do rmCosmetics
     *
     * Desenvolvido por Mikael e Eduard
     */

    var usingParticle = mutableMapOf<Player, ParticleCosmetic>()
    var particles = mutableListOf<ParticleCosmetic>()
    var particlesSelected = mutableMapOf<MiftProfile, CosmeticsData>()

    val precoemgold: MutableMap<ParticleCosmetic, Double> = mutableMapOf<ParticleCosmetic, Double>()
    val precoemcash: MutableMap<ParticleCosmetic, Double> = mutableMapOf<ParticleCosmetic, Double>()
    val compravel: MutableMap<ParticleCosmetic, Boolean> = mutableMapOf<ParticleCosmetic, Boolean>()
    val compravelporgold: MutableMap<ParticleCosmetic, Boolean> = mutableMapOf<ParticleCosmetic, Boolean>()
    val compravelporcash: MutableMap<ParticleCosmetic, Boolean> = mutableMapOf<ParticleCosmetic, Boolean>()
    val particlesByName = mutableMapOf<String, ParticleCosmetic>()

    fun select(player: Player, particle: ParticleCosmetic) {
        val user = player.user
        val selected = getOrCreate(user)
        selected.particle = particle.display
        usingParticle[player] = particle
        selected.updateOnlyQueue("particle")
    }

    fun deselect(player: Player) {
        val user = player.user
        val selected = getOrCreate(user)
        selected.particle = null
        usingParticle.remove(player)
        selected.updateOnlyQueue("particle")
    }

    fun hasSelected(player: Player): Boolean {
        return usingParticle.containsKey(player)
    }

    fun getSelectedParticle(player: Player): ParticleCosmetic {
        return usingParticle[player]!!
    }

    fun load(player: Player) {
        val profile = player.user
        val particleSelected = miftCore.sqlManager.getDataOf<CosmeticsData>(profile) ?: return
        particlesSelected[profile] = particleSelected
        val particle = particles.firstOrNull { it.display == particleSelected.particle } ?: return
        usingParticle[player] = particle
    }

    fun getOrCreate(profile: MiftProfile): CosmeticsData {
        if (CosmeticsUtils.selecteds.containsKey(profile)) {
            return CosmeticsUtils.selecteds[profile]!!
        }
        val particleSelected = CosmeticsData()
        particleSelected.player = profile
        particleSelected.insert()
        particlesSelected[profile] = particleSelected
        CosmeticsUtils.selecteds[profile] = particleSelected
        return particleSelected
    }

    init {
        val particle1 = ParticleCosmetic(
            "Corações",
            "divino",
            ParticleType.HEART,
            Material.RED_ROSE,
            "rmcosmetics.particle.heart",
            "rmcore.benefits.mvp",
            "§cExclusivo para §bMVP §cou superior.",
            SingleParticleHead::class
        )
        val particle2 = ParticleCosmetic(
            "Chamas",
            "divino",
            ParticleType.FLAME,
            Material.BLAZE_POWDER,
            "rmcosmetics.particle.flame",
            "rmcore.defaultperm",
            "§cExclusivo para §7Membro §cou superior.",
            SingleParticleHead::class
        )
        val particle3 = ParticleCosmetic(
            "Críticos",
            "raro",
            ParticleType.CRIT,
            Material.DEAD_BUSH,
            "rmcosmetics.particle.crit",
            "rmcore.defaultperm",
            "§cExclusivo para §7Membro §cou superior.",
            SingleParticleHead::class
        )
        val particle4 = ParticleCosmetic(
            "Críticos Mágicos",
            "raro",
            ParticleType.MAGIC_CRIT,
            Material.PRISMARINE_CRYSTALS,
            "rmcosmetics.particle.magic_crit",
            "rmcore.defaultperm",
            "§cExclusivo para §7Membro §cou superior.",
            SingleParticleHead::class
        )
        val particle5 = ParticleCosmetic(
            "Foguetes",
            "raro",
            ParticleType.FIREWORKS_SPARK,
            Material.FIREWORK,
            "rmcosmetics.particle.fireworks_spark",
            "rmcore.benefits.vip",
            "§cExclusivo para §aVIP §cou superior.",
            SingleParticleHead::class
        )
        val particle6 = ParticleCosmetic(
            "Magia Bruxesca",
            "divino",
            ParticleType.WITCH_MAGIC,
            Material.FERMENTED_SPIDER_EYE,
            "rmcosmetics.particle.witch_magic",
            "rmcore.benefits.mvp",
            "§cExclusivo para §bMVP §cou superior.",
            SingleParticleHead::class
        )
        val particle7 = ParticleCosmetic(
            "Gotas de Água",
            "raro",
            ParticleType.DRIP_WATER,
            Material.WATER_BUCKET,
            "rmcosmetics.particle.drip_water",
            "rmcore.defaultperm",
            "§cExclusivo para §7Membro §cou superior.",
            SingleParticleHead::class
        )
        val particle8 = ParticleCosmetic(
            "Gotas de Lava",
            "raro",
            ParticleType.DRIP_LAVA,
            Material.LAVA_BUCKET,
            "rmcosmetics.particle.drip_lava",
            "rmcore.defaultperm",
            "§cExclusivo para §7Membro §cou superior.",
            SingleParticleHead::class
        )
        val particle9 = ParticleCosmetic(
            "Aldeão Zangado",
            "epico",
            ParticleType.ANGRY_VILLAGER,
            Material.BLAZE_ROD,
            "rmcosmetics.particle.angry_villager",
            "rmcore.benefits.mvpplus",
            "§cExclusivo para §6MVP§c+ §cou superior.",
            SingleParticleHead::class
        )
        val particle10 = ParticleCosmetic(
            "Aldeão Feliz",
            "epico",
            ParticleType.HAPPY_VILLAGER,
            Material.EMERALD,
            "rmcosmetics.particle.happy_villager",
            "rmcore.defaultperm",
            "§cExclusivo para §7Membro §cou superior.",
            SingleParticleHead::class
        )
        val particle11 = ParticleCosmetic(
            "Notas Musicais",
            "raro",
            ParticleType.NOTE,
            Material.RECORD_4,
            "rmcosmetics.particle.note",
            "rmcore.benefits.vip",
            "§cExclusivo para §aVIP §cou superior.",
            SingleParticleHead::class
        )
        val particle12 = ParticleCosmetic(
            "Portal",
            "raro",
            ParticleType.PORTAL,
            Material.EYE_OF_ENDER,
            "rmcosmetics.particle.portal",
            "rmcore.defaultperm",
            "§cExclusivo para §7Membro §cou superior.",
            SingleParticleHead::class
        )
        val particle13 = ParticleCosmetic(
            "Letras Encantadas",
            "comum",
            ParticleType.ENCHANTMENT_TABLE,
            Material.MAP,
            "rmcosmetics.particle.enchantment_table",
            "rmcore.benefits.mvpplus",
            "§cExclusivo para §6MVP§c+ §cou superior.",
            SingleParticleHead::class
        )

        particles.add(particle1)
        particles.add(particle2)
        particles.add(particle3)
        particles.add(particle4)
        particles.add(particle5)
        particles.add(particle6)
        particles.add(particle7)
        particles.add(particle8)
        particles.add(particle9)
        particles.add(particle10)
        particles.add(particle11)
        particles.add(particle12)
        particles.add(particle13)

        for (particle in particles) {
            compravel[particle] = true
            particlesByName[particle.display] = particle
        }
        compravel[particlesByName["Corações"]!!] = true
        compravel[particlesByName["Chamas"]!!] = true
        compravel[particlesByName["Críticos"]!!] = true
        compravel[particlesByName["Críticos Mágicos"]!!] = true
        compravel[particlesByName["Foguetes"]!!] = true
        compravel[particlesByName["Magia Bruxesca"]!!] = true
        compravel[particlesByName["Gotas de Água"]!!] = true
        compravel[particlesByName["Gotas de Lava"]!!] = true
        compravel[particlesByName["Aldeão Zangado"]!!] = true
        compravel[particlesByName["Aldeão Feliz"]!!] = true
        compravel[particlesByName["Notas Musicais"]!!] = true
        compravel[particlesByName["Letras Encantadas"]!!] = true

        for (particle in particles) {
            compravelporgold[particle] = true
            particlesByName[particle.display] = particle
        }
        compravelporgold[particlesByName["Corações"]!!] = true
        compravelporgold[particlesByName["Chamas"]!!] = true
        compravelporgold[particlesByName["Críticos"]!!] = true
        compravelporgold[particlesByName["Críticos Mágicos"]!!] = true
        compravelporgold[particlesByName["Foguetes"]!!] = true
        compravelporgold[particlesByName["Magia Bruxesca"]!!] = true
        compravelporgold[particlesByName["Gotas de Água"]!!] = true
        compravelporgold[particlesByName["Gotas de Lava"]!!] = true
        compravelporgold[particlesByName["Aldeão Zangado"]!!] = true
        compravelporgold[particlesByName["Aldeão Feliz"]!!] = true
        compravelporgold[particlesByName["Notas Musicais"]!!] = true
        compravelporgold[particlesByName["Portal"]!!] = true
        compravelporgold[particlesByName["Letras Encantadas"]!!] = true

        for (particle in particles) {
            compravelporcash[particle] = true
            particlesByName[particle.display] = particle
        }
        compravelporcash[particlesByName["Corações"]!!] = false
        compravelporcash[particlesByName["Chamas"]!!] = true
        compravelporcash[particlesByName["Críticos"]!!] = true
        compravelporcash[particlesByName["Críticos Mágicos"]!!] = true
        compravelporcash[particlesByName["Foguetes"]!!] = false
        compravelporcash[particlesByName["Magia Bruxesca"]!!] = false
        compravelporcash[particlesByName["Gotas de Água"]!!] = true
        compravelporcash[particlesByName["Gotas de Lava"]!!] = true
        compravelporcash[particlesByName["Aldeão Zangado"]!!] = false
        compravelporcash[particlesByName["Aldeão Feliz"]!!] = false
        compravelporcash[particlesByName["Notas Musicais"]!!] = true
        compravelporcash[particlesByName["Portal"]!!] = true
        compravelporcash[particlesByName["Letras Encantadas"]!!] = true

        for (particle in particles) {
            precoemgold[particle] = 350.0
            particlesByName[particle.display] = particle
        }
        precoemgold[particlesByName["Corações"]!!] = 650.0
        precoemgold[particlesByName["Chamas"]!!] = 450.0
        precoemgold[particlesByName["Críticos"]!!] = 300.0
        precoemgold[particlesByName["Críticos Mágicos"]!!] = 300.0
        precoemgold[particlesByName["Foguetes"]!!] = 550.0
        precoemgold[particlesByName["Magia Bruxesca"]!!] = 650.0
        precoemgold[particlesByName["Gotas de Água"]!!] = 450.0
        precoemgold[particlesByName["Gotas de Lava"]!!] = 450.0
        precoemgold[particlesByName["Aldeão Zangado"]!!] = 650.0
        precoemgold[particlesByName["Aldeão Feliz"]!!] = 750.0
        precoemgold[particlesByName["Notas Musicais"]!!] = 650.0
        precoemgold[particlesByName["Portal"]!!] = 450.0
        precoemgold[particlesByName["Letras Encantadas"]!!] = 400.0

        for (particle in particles) {
            precoemcash[particle] = 350.0
            particlesByName[particle.display] = particle
        }
        precoemcash[particlesByName["Corações"]!!] = Double.MAX_VALUE
        precoemcash[particlesByName["Chamas"]!!] = 650.0
        precoemcash[particlesByName["Críticos"]!!] = 400.0
        precoemcash[particlesByName["Críticos Mágicos"]!!] = 400.0
        precoemcash[particlesByName["Foguetes"]!!] = Double.MAX_VALUE
        precoemcash[particlesByName["Magia Bruxesca"]!!] = Double.MAX_VALUE
        precoemcash[particlesByName["Gotas de Água"]!!] = 600.0
        precoemcash[particlesByName["Gotas de Lava"]!!] = 600.0
        precoemcash[particlesByName["Aldeão Zangado"]!!] = Double.MAX_VALUE
        precoemcash[particlesByName["Aldeão Feliz"]!!] = Double.MAX_VALUE
        precoemcash[particlesByName["Notas Musicais"]!!] = 800.0
        precoemcash[particlesByName["Portal"]!!] = 550.0
        precoemcash[particlesByName["Letras Encantadas"]!!] = 500.0
    }
}