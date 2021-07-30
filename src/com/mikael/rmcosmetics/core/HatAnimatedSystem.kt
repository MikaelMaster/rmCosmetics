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
            "rmcosmetics.benefits.mvp",
            "§fExclusivo para §6MVP §fou superior."
        )

        val chapeu2 = HatAnimated(
            "Assustado", listOf(""), mutableMapOf(
                "http://textures.minecraft.net/texture/1b9932b5658f4cac4f4f8d9e98f6dcee2e17744169ccb5c8145365f17d445f3" to 2,
                "http://textures.minecraft.net/texture/ef82e9d0170f1330ba84d57c4689b75e916ab5e96ea0313aed651c8777f7573" to 2,
                "http://textures.minecraft.net/texture/b328db1c323585adeba1907ced306050e02aa77591588fb182fdeaf423ad6" to 2,
                "http://textures.minecraft.net/texture/45868529fbf4be629371275b1138dab929576021716ee737db12634aa125af3" to 20
            ), "rmcosmetics.animatedhat.assustado",
            "rmcosmetics.defaultperm",
            "§fExclusivo para §7Membro §fou superior."
        )

        val chapeu3 = HatAnimated(
            "Piscadela", listOf(""), mutableMapOf(
                "http://textures.minecraft.net/texture/465b5611f8abc01d9bb8fcd62f3a64b5125534a428731f202e619d9ce1" to 5,
                "http://textures.minecraft.net/texture/8d8f5fb387ca66fc2f65b91fcb231604548e8565895bb96c676984205e6f19" to 5,
                "http://textures.minecraft.net/texture/f4ea2d6f939fefeff5d122e63dd26fa8a427df90b2928bc1fa89a8252a7e" to 10
            ), "rmcosmetics.animatedhat.piscadela",
            "rmcosmetics.benefits.mvp",
            "§fExclusivo para §6MVP §fou superior."
        )

        val chapeu4 = HatAnimated(
            "Apaixonado", listOf(""), mutableMapOf(
                "http://textures.minecraft.net/texture/196b8e272c54a422d9df36d85caff26624c733e7b3f6040d3e4c9cd6e" to 5,
                "http://textures.minecraft.net/texture/129fb9f593b6ae533dfa8ce79615fcc35894a42cbb41de598d694767352fe" to 5,
                "http://textures.minecraft.net/texture/42737e99e4c0596a3712e7711baecae8d1ddb774ac1cf531896862380753e16" to 10,
                "http://textures.minecraft.net/texture/129fb9f593b6ae533dfa8ce79615fcc35894a42cbb41de598d694767352fe" to 5,
                "http://textures.minecraft.net/texture/42737e99e4c0596a3712e7711baecae8d1ddb774ac1cf531896862380753e16" to 10
            ), "rmcosmetics.animatedhat.apaixonado",
            "rmcosmetics.benefits.vip",
            "§fExclusivo para §aVIP §fou superior."
        )

        val chapeu5 = HatAnimated(
            "Slime Multicolor", listOf(""), mutableMapOf(
                "http://textures.minecraft.net/texture/cce5609e53370fcb51c74333bfe03414af94fa419b5c3f09a8d59dadc51ff6f8" to 3,
                "http://textures.minecraft.net/texture/895aeec6b842ada8669f846d65bc49762597824ab944f22f45bf3bbb941abe6c" to 3,
                "http://textures.minecraft.net/texture/76f286039bdd40cfc96a5c597f9f4bded4339ec95732da6551972e0f1d573" to 3,
                "http://textures.minecraft.net/texture/2eba8e13e3bd1243d7128aebfa48d4c9f2d1b120740548aaf4afb93a51fe3b26" to 3,
                "http://textures.minecraft.net/texture/ad34ba688d9d275b5b2bc200faac000a134525e565b9268e34be392a600d173e" to 3,
                "http://textures.minecraft.net/texture/55aa7895830f566618a71af691124654abc1cdfc18bbd6a6631dfbdc6a648b2" to 3
            ), "rmcosmetics.animatedhat.slime_multicolor",
            "rmcosmetics.benefits.vip",
            "§fExclusivo para §aVIP §fou superior."
        )

        val chapeu6 = HatAnimated(
            "Sirene", listOf(""), mutableMapOf(
                "http://textures.minecraft.net/texture/71e08d547f992949f013a1e2c7e3cd226ab485e3bc8c0c0c39c15d5b7c9e820" to 3,
                "http://textures.minecraft.net/texture/15742475ee5ad5bef398bb41943a515dcaa77dcd72228b519ef69c3aa86c4" to 3
            ), "rmcosmetics.animatedhat.sirene",
            "rmcosmetics.defaultperm",
            "§fExclusivo para §7Membro §fou superior."
        )

        val chapeu7 = HatAnimated(
            "Notificação do Discord", listOf(""), mutableMapOf(
                "http://textures.minecraft.net/texture/dc59e5c7b0738b579f3b444c13a47bed496b30838b2ee2b127cc59cd798aee77" to 5,
                "http://textures.minecraft.net/texture/ad833b51566565658f9011de8784e90c1ad9ba5d3337f8c069213bbdee986523" to 5
            ), "rmcosmetics.animatedhat.notificacao_do_discord",
            "rmcosmetics.defaultperm",
            "§fExclusivo para §7Membro §fou superior."
        )

        animatedHats.add(chapeu1)
        animatedHats.add(chapeu2)
        animatedHats.add(chapeu3)
        animatedHats.add(chapeu4)
        animatedHats.add(chapeu5)
        animatedHats.add(chapeu6)
        animatedHats.add(chapeu7)

        for (animatedHat in animatedHats) {
            compravel[animatedHat] = true
            animatedHatsByName[animatedHat.display] = animatedHat
        }
        compravel[animatedHatsByName["Alegre"]!!] = true
        compravel[animatedHatsByName["Assustado"]!!] = true
        compravel[animatedHatsByName["Piscadela"]!!] = false
        compravel[animatedHatsByName["Apaixonado"]!!] = false
        compravel[animatedHatsByName["Slime Multicolor"]!!] = true
        compravel[animatedHatsByName["Sirene"]!!] = true
        compravel[animatedHatsByName["Notificação do Discord"]!!] = false

        for (animatedHat in animatedHats) {
            compravelporgold[animatedHat] = true
            animatedHatsByName[animatedHat.display] = animatedHat
        }
        compravelporgold[animatedHatsByName["Alegre"]!!] = true
        compravelporgold[animatedHatsByName["Assustado"]!!] = true
        compravelporgold[animatedHatsByName["Piscadela"]!!] = false
        compravelporgold[animatedHatsByName["Apaixonado"]!!] = false
        compravelporgold[animatedHatsByName["Slime Multicolor"]!!] = true
        compravelporgold[animatedHatsByName["Sirene"]!!] = true
        compravelporgold[animatedHatsByName["Notificação do Discord"]!!] = false

        for (animatedHat in animatedHats) {
            compravelporcash[animatedHat] = true
            animatedHatsByName[animatedHat.display] = animatedHat
        }
        compravelporcash[animatedHatsByName["Alegre"]!!] = false
        compravelporcash[animatedHatsByName["Assustado"]!!] = false
        compravelporcash[animatedHatsByName["Piscadela"]!!] = false
        compravelporcash[animatedHatsByName["Apaixonado"]!!] = false
        compravelporcash[animatedHatsByName["Slime Multicolor"]!!] = true
        compravelporcash[animatedHatsByName["Sirene"]!!] = true
        compravelporcash[animatedHatsByName["Notificação do Discord"]!!] = false

        for (animatedHat in animatedHats) {
            precoemgold[animatedHat] = 500.0
            animatedHatsByName[animatedHat.display] = animatedHat
        }
        precoemgold[animatedHatsByName["Alegre"]!!] = 650.0
        precoemgold[animatedHatsByName["Assustado"]!!] = 550.0
        precoemgold[animatedHatsByName["Piscadela"]!!] = 0.0
        precoemgold[animatedHatsByName["Apaixonado"]!!] = 0.0
        precoemgold[animatedHatsByName["Slime Multicolor"]!!] = 500.0
        precoemgold[animatedHatsByName["Sirene"]!!] = 450.0
        precoemgold[animatedHatsByName["Notificação do Discord"]!!] = 0.0

        for (animatedHat in animatedHats) {
            precoemcash[animatedHat] = 500.0
            animatedHatsByName[animatedHat.display] = animatedHat
        }
        precoemcash[animatedHatsByName["Alegre"]!!] = 0.0
        precoemcash[animatedHatsByName["Assustado"]!!] = 0.0
        precoemcash[animatedHatsByName["Piscadela"]!!] = 0.0
        precoemcash[animatedHatsByName["Apaixonado"]!!] = 0.0
        precoemcash[animatedHatsByName["Slime Multicolor"]!!] = 650.0
        precoemcash[animatedHatsByName["Sirene"]!!] = 550.0
        precoemcash[animatedHatsByName["Notificação do Discord"]!!] = 0.0

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