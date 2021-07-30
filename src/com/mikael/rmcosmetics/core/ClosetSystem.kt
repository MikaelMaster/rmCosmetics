package com.mikael.rmcosmetics.core

import com.mikael.rmcosmetics.objects.ClosetData
import net.eduard.redemikael.core.objects.MiftProfile
import net.eduard.redemikael.core.user
import org.bukkit.entity.Player

object ClosetSystem {

    var closets = mutableMapOf<MiftProfile, ClosetData>()

    fun getPlayerCloset(player: Player): ClosetData {
        val user = player.user
        if (user in closets) {
            return closets[user]!!
        }
        val newCloset = ClosetData()
        newCloset.player = user
        newCloset.insert()
        closets[user] = newCloset
        return newCloset
    }
}