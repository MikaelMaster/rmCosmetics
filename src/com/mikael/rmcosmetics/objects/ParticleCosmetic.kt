package com.mikael.rmcosmetics.objects

import com.mikael.rmcosmetics.particles.SingleParticleHead
import net.eduard.api.lib.game.ParticleType
import org.bukkit.Material
import kotlin.reflect.KClass

open class ParticleCosmetic(

    var display: String = "Partícula",
    var rarity: String = "comum",
    var type: ParticleType = ParticleType.HEART,
    var material: Material = Material.RED_ROSE,
    var permission: String = "rmcosmetics.particle.particle",
    var groupPermission: String = "rmcosmetics.animatedhat.vip",
    var exclusiveGroupName: String = "§fExclusivo para §7Membro §fou superior.",
    var animationClass: KClass<out ParticleAnimation> = SingleParticleHead::class
)