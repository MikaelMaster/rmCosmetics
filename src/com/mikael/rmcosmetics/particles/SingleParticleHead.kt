package com.mikael.rmcosmetics.particles

import com.mikael.rmcosmetics.core.ParticleSystem
import com.mikael.rmcosmetics.menu.MenuParticles
import com.mikael.rmcosmetics.objects.ParticleAnimation
import net.eduard.api.lib.game.Particle
import net.eduard.api.lib.game.ParticleType
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class SingleParticleHead(player: Player) : ParticleAnimation(player) {
    override fun tick() {
        val localparticle = player.location.clone().add(0.0, 2.2, 0.0)
        if (MenuParticles.usingEffect.containsKey(player)) {

            val usedParticle = ParticleSystem.getSelectedParticle(player)
            val particletype = usedParticle.type

            for (playerLoop in Bukkit.getOnlinePlayers()) {
                if (!playerLoop.canSee(player)) continue

                when (particletype) {
                    ParticleType.FLAME -> {
                        Particle(particletype, 1, 0.1f, 0.1f, 0.1f, 0.1f)
                            .create(playerLoop, localparticle)
                    }
                    ParticleType.NOTE -> {
                        Particle(particletype, 1, 0.1f, 0f, 0f, 0f)
                            .create(playerLoop, localparticle)
                    }
                    else -> {
                        Particle(particletype, 1, 0f, 0f, 0f, 0f)
                            .create(playerLoop, localparticle)
                    }
                }
            }
        }
    }
}