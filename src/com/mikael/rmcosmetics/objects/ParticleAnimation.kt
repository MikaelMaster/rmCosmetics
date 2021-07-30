package com.mikael.rmcosmetics.objects

import com.mikael.rmcosmetics.MiftCosmetics
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

open abstract class ParticleAnimation(
    var player : Player,
    var durationTicks : Int = -1
) : BukkitRunnable() {
    companion object{
        val animationsRunning = mutableListOf<ParticleAnimation>()
    }
    init{
        runTaskTimerAsynchronously(MiftCosmetics.instance,1,1)
        animationsRunning.add(this)
    }
    var running = false
    var currentTick = 0
    override fun run() {
        if (running) {
            currentTick++
            tick()
            if (durationTicks>0&&currentTick>durationTicks){
                stop()
            }
        }
    }
    abstract fun tick()
    fun start(){
       running = true

    }
    fun stop(){
        running = false
    }
    fun restart(){
        stop()
        start()
    }
}