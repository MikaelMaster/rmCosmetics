package com.mikael.rmcosmetics.objects

import com.mikael.rmcosmetics.particles.SingleParticleHead
import net.eduard.api.lib.game.ParticleType
import org.bukkit.Material
import kotlin.reflect.KClass

open class ParticleCosmetic(

    var display: String = "Partícula",
    var type: ParticleType = ParticleType.HEART,
    var material: Material = Material.RED_ROSE,
    var permission: String = "rmcosmetics.particle.particle",
    var animationClass: KClass<out ParticleAnimation> = SingleParticleHead::class
)