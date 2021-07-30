package com.mikael.rmcosmetics.core

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.objects.AnimatedHatData
import com.mikael.rmcosmetics.objects.HatAnimated
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.redemikael.core.api.miftCore
import net.eduard.redemikael.core.objects.MiftProfile
import net.eduard.redemikael.core.user
import org.bukkit.entity.Player

object HatAnimatedSystem {

    var animatedHats = mutableListOf<HatAnimated>()
    var usingAnimatedHat = mutableMapOf<Player, HatAnimated>()
    var hatsSelected = mutableMapOf<MiftProfile, AnimatedHatData>()

    val precoemgold: MutableMap<HatAnimated, Double> = mutableMapOf<HatAnimated, Double>()
    val precoemcash: MutableMap<HatAnimated, Double> = mutableMapOf<HatAnimated, Double>()
    val compravel: MutableMap<HatAnimated, Boolean> = mutableMapOf<HatAnimated, Boolean>()
    val compravelporgold: MutableMap<HatAnimated, Boolean> = mutableMapOf<HatAnimated, Boolean>()
    val compravelporcash: MutableMap<HatAnimated, Boolean> = mutableMapOf<HatAnimated, Boolean>()
    val animatedHatsByName = mutableMapOf<String, HatAnimated>()

    init {

        val chapeu1 = HatAnimated(
            "Alegre", listOf(""), mutableMapOf(
                "http://textures.minecraft.net/texture/465b5611f8abc01d9bb8fcd62f3a64b5125534a428731f202e619d9ce1" to 5,
                "http://textures.minecraft.net/texture/8d8f5fb387ca66fc2f65b91fcb231604548e8565895bb96c676984205e6f19" to 5,
                "http://textures.minecraft.net/texture/01b9def55876c41c17c815f88115f02c95f89620fbed6a6cb2d38d46fe05" to 20
            ), "rmcosmetics.animatedhat.alegre",
            "rmcosmetics.defaultperm",
            "§fExclusivo para §7Membro §fou superior."
        )

        val chapeu2 = HatAnimated(
            "Assustado", listOf(""), mutableMapOf(
                "http://textures.minecraft.net/texture/1b9932b5658f4cac4f4f8d9e98f6dcee2e17744169ccb5c8145365f17d445f3" to 2,
                "http://textures.minecraft.net/texture/ef82e9d0170f1330ba84d57c4689b75e916ab5e96ea0313aed651c8777f7573" to 2,
                "http://textures.minecraft.net/texture/b328db1c323585adeba1907ced306050e02aa77591588fb182fdeaf423ad6" to 2,
                "http://textures.minecraft.net/texture/45868529fbf4be629371275b1138dab929576021716ee737db12634aa125af3" to 20
            ), "rmcosmetics.animatedhat.hat",
            "rmcosmetics.defaultperm",
            "§fExclusivo para §7Membro §fou superior."
        )

        val chapeu3 = HatAnimated(
            "Piscadela", listOf(""), mutableMapOf(
                "http://textures.minecraft.net/texture/465b5611f8abc01d9bb8fcd62f3a64b5125534a428731f202e619d9ce1" to 5,
                "http://textures.minecraft.net/texture/8d8f5fb387ca66fc2f65b91fcb231604548e8565895bb96c676984205e6f19" to 5,
                "http://textures.minecraft.net/texture/f4ea2d6f939fefeff5d122e63dd26fa8a427df90b2928bc1fa89a8252a7e" to 10
            ), "rmcosmetics.animatedhat.hat",
            "rmcosmetics.benefits.vip",
            "§fExclusivo para §aVIP §fou superior."
        )

        animatedHats.add(chapeu1)
        animatedHats.add(chapeu2)
        animatedHats.add(chapeu3)

        for (animatedHat in animatedHats) {
            compravel[animatedHat] = true
            animatedHatsByName[animatedHat.display] = animatedHat
        }
        compravel[animatedHatsByName["Alegre"]!!] = true

        for (animatedHat in animatedHats) {
            compravelporgold[animatedHat] = true
            animatedHatsByName[animatedHat.display] = animatedHat
        }
        compravelporgold[animatedHatsByName["Alegre"]!!] = true
        compravelporgold[animatedHatsByName["Assustado"]!!] = true
        compravelporgold[animatedHatsByName["Piscadela"]!!] = true

        for (animatedHat in animatedHats) {
            compravelporcash[animatedHat] = true
            animatedHatsByName[animatedHat.display] = animatedHat
        }
        compravelporcash[animatedHatsByName["Alegre"]!!] = true
        compravelporcash[animatedHatsByName["Assustado"]!!] = false
        compravelporcash[animatedHatsByName["Piscadela"]!!] = false

        for (animatedHat in animatedHats) {
            precoemgold[animatedHat] = 500.0
            animatedHatsByName[animatedHat.display] = animatedHat
        }
        precoemgold[animatedHatsByName["Alegre"]!!] = 450.0
        precoemgold[animatedHatsByName["Assustado"]!!] = 650.0
        precoemgold[animatedHatsByName["Piscadela"]!!] = 650.0

        for (animatedHat in animatedHats) {
            precoemcash[animatedHat] = 500.0
            animatedHatsByName[animatedHat.display] = animatedHat
        }
        precoemcash[animatedHatsByName["Alegre"]!!] = 650.0
        precoemcash[animatedHatsByName["Assustado"]!!] = 0.0
        precoemcash[animatedHatsByName["Piscadela"]!!] = 0.0

        MiftCosmetics.instance.asyncTimer(1, 1) {
            for (hat in animatedHats) {
                if (hat.canChange())
                    hat.next()
            }
            for ((player, hat) in usingAnimatedHat) {
                val hatItem = ItemBuilder().skin(hat.getCurrentUrl())
                    .name("§a" + hat.display)
                player.equipment.helmet = hatItem
            }
        }
    }


    fun select(player: Player, hatAnimated: HatAnimated) {
        val user = player.user
        val selected = getOrCreate(user)
        selected.animatedHat = hatAnimated.display
        usingAnimatedHat[player] = hatAnimated
        selected.updateQueue()
    }

    fun hasSelected(player: Player): Boolean {
        return usingAnimatedHat.containsKey(player)
    }

    fun getSelectedAnimatedHat(player: Player): HatAnimated {
        return usingAnimatedHat[player]!!
    }

    fun deselect(player: Player) {
        val user = player.user
        val selected = getOrCreate(user)
        selected.animatedHat = null
        usingAnimatedHat.remove(player)
        selected.updateQueue()
    }

    fun load(player: Player) {
        val profile = player.user
        val hatAnimatedSelected = miftCore.sqlManager.getDataOf<AnimatedHatData>(profile) ?: return
        hatsSelected[profile] = hatAnimatedSelected
        val hatAnimated = animatedHats.firstOrNull { it.display == hatAnimatedSelected.animatedHat } ?: return
        usingAnimatedHat[player] = hatAnimated
    }

    fun getOrCreate(profile: MiftProfile): AnimatedHatData {
        if (hatsSelected.containsKey(profile)) {
            return hatsSelected[profile]!!
        }
        val hatAnimatedSelected = AnimatedHatData()
        hatAnimatedSelected.player = profile
        hatAnimatedSelected.insert()
        hatsSelected[profile] = hatAnimatedSelected
        return hatAnimatedSelected
    }
}