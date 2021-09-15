package com.mikael.rmcosmetics.core

import com.kirelcodes.miniaturepets.loader.PetLoader
import com.kirelcodes.miniaturepets.pets.Pet
import com.kirelcodes.miniaturepets.pets.PetContainer
import com.mikael.rmcosmetics.objects.CompanionData
import net.eduard.redemikael.core.objects.MiftProfile
import net.eduard.redemikael.core.user
import org.bukkit.entity.Player

object CompanionSystem {

    var usingCompanion = mutableMapOf<Player, Pet>()
    var companionsSelected = mutableMapOf<MiftProfile, CompanionData>()

    fun select(player: Player, pet: Pet) {
        val user = player.user
        val selected = getOrCreate(user)
        selected.companion = pet.container.name
        usingCompanion[player] = pet
        selected.updateOnlyQueue("companion")
    }

    fun hasSelected(player: Player): Boolean {
        return usingCompanion.containsKey(player)
    }

    fun hasName(player: Player): Boolean {
        val user = player.user
        val selected = getOrCreate(user)

        return selected.customNames.customNames.containsKey(selected.companion)
    }

    fun getSelectedCompanion(player: Player): Pet {
        return usingCompanion[player]!!
    }

    fun deselect(player: Player) {
        val user = player.user
        val selected = getOrCreate(user)
        selected.companion = null
        usingCompanion.remove(player)
        selected.updateOnlyQueue("companion")
    }

    fun setCustomName(player: Player, name: String) {
        val user = player.user
        val selected = getOrCreate(user)
        selected.customNames.customNames[selected.companion!!] = name
        selected.updateOnlyQueue("customNames")
    }

    fun getCustomName(player: Player): String? {
        val user = player.user
        val selected = getOrCreate(user)

        return selected.customNames.customNames[selected.companion!!]
    }

    fun getOrCreate(profile: MiftProfile): CompanionData {
        if (companionsSelected.containsKey(profile)) {
            return companionsSelected[profile]!!
        }
        val companionSelected = CompanionData()
        companionSelected.player = profile
        companionSelected.insert()
        companionsSelected[profile] = companionSelected
        return companionSelected
    }

    val precoemgold: MutableMap<PetContainer, Double> = mutableMapOf<PetContainer, Double>()
    val precoemcash: MutableMap<PetContainer, Double> = mutableMapOf<PetContainer, Double>()
    val compravel: MutableMap<PetContainer, Boolean> = mutableMapOf<PetContainer, Boolean>()
    val compravelporgold: MutableMap<PetContainer, Boolean> = mutableMapOf<PetContainer, Boolean>()
    val compravelporcash: MutableMap<PetContainer, Boolean> = mutableMapOf<PetContainer, Boolean>()
    val groupPermission: MutableMap<PetContainer, String> = mutableMapOf<PetContainer, String>()
    val exclusiveGroupName: MutableMap<PetContainer, String> = mutableMapOf<PetContainer, String>()
    val rarity: MutableMap<PetContainer, String> = mutableMapOf<PetContainer, String>()
    val petsByName = mutableMapOf<String, PetContainer>()

    init {

        for (pet in PetLoader.getPets()) {
            compravel[pet] = true
            petsByName[pet.name.toLowerCase()] = pet
        }
        compravel[petsByName["urso"]!!] = true
        compravel[petsByName["panda gigante"]!!] = true
        compravel[petsByName["boxer"]!!] = true
        compravel[petsByName["macaco"]!!] = true
        compravel[petsByName["diglet"]!!] = true
        compravel[petsByName["pato"]!!] = true
        compravel[petsByName["dragão de fogo"]!!] = true
        compravel[petsByName["girafa"]!!] = true
        compravel[petsByName["gorila"]!!] = true
        compravel[petsByName["dragão de gelo"]!!] = true
        compravel[petsByName["coala"]!!] = true
        compravel[petsByName["leão"]!!] = true
        compravel[petsByName["minime"]!!] = true
        compravel[petsByName["bb-8"]!!] = true
        compravel[petsByName["ursinho de pelúcia"]!!] = true
        compravel[petsByName["pinguim"]!!] = true
        compravel[petsByName["pug"]!!] = true
        compravel[petsByName["tartaruga"]!!] = true
        compravel[petsByName["logo do youtube"]!!] = false
        compravel[petsByName["logo da twitch"]!!] = false

        for (pet in PetLoader.getPets()) {
            compravelporgold[pet] = true
            petsByName[pet.name.toLowerCase()] = pet
        }
        compravelporgold[petsByName["urso"]!!] = true
        compravelporgold[petsByName["panda gigante"]!!] = true
        compravelporgold[petsByName["boxer"]!!] = true
        compravelporgold[petsByName["macaco"]!!] = true
        compravelporgold[petsByName["diglet"]!!] = true
        compravelporgold[petsByName["pato"]!!] = true
        compravelporgold[petsByName["dragão de fogo"]!!] = true
        compravelporgold[petsByName["girafa"]!!] = true
        compravelporgold[petsByName["gorila"]!!] = true
        compravelporgold[petsByName["dragão de gelo"]!!] = true
        compravelporgold[petsByName["coala"]!!] = true
        compravelporgold[petsByName["leão"]!!] = true
        compravelporgold[petsByName["minime"]!!] = true
        compravelporgold[petsByName["bb-8"]!!] = true
        compravelporgold[petsByName["ursinho de pelúcia"]!!] = true
        compravelporgold[petsByName["pinguim"]!!] = true
        compravelporgold[petsByName["pug"]!!] = true
        compravelporgold[petsByName["tartaruga"]!!] = true
        compravelporgold[petsByName["logo do youtube"]!!] = false
        compravelporgold[petsByName["logo da twitch"]!!] = false

        for (pet in PetLoader.getPets()) {
            compravelporcash[pet] = true
            petsByName[pet.name.toLowerCase()] = pet
        }
        compravelporcash[petsByName["urso"]!!] = true
        compravelporcash[petsByName["panda gigante"]!!] = false
        compravelporcash[petsByName["boxer"]!!] = true
        compravelporcash[petsByName["macaco"]!!] = true
        compravelporcash[petsByName["diglet"]!!] = false
        compravelporcash[petsByName["pato"]!!] = true
        compravelporcash[petsByName["dragão de fogo"]!!] = true
        compravelporcash[petsByName["girafa"]!!] = true
        compravelporcash[petsByName["gorila"]!!] = true
        compravelporcash[petsByName["dragão de gelo"]!!] = true
        compravelporcash[petsByName["coala"]!!] = true
        compravelporcash[petsByName["leão"]!!] = true
        compravelporcash[petsByName["minime"]!!] = true
        compravelporcash[petsByName["bb-8"]!!] = true
        compravelporcash[petsByName["ursinho de pelúcia"]!!] = false
        compravelporcash[petsByName["pinguim"]!!] = false
        compravelporcash[petsByName["pug"]!!] = true
        compravelporcash[petsByName["tartaruga"]!!] = false
        compravelporcash[petsByName["logo do youtube"]!!] = false
        compravelporgold[petsByName["logo da twitch"]!!] = false

        for (pet in PetLoader.getPets()) {
            precoemgold[pet] = 0.0
            petsByName[pet.name.toLowerCase()] = pet
        }
        precoemgold[petsByName["urso"]!!] = 850.0
        precoemgold[petsByName["panda gigante"]!!] = 1500.0
        precoemgold[petsByName["boxer"]!!] = 850.0
        precoemgold[petsByName["macaco"]!!] = 1000.0
        precoemgold[petsByName["diglet"]!!] = 500.0
        precoemgold[petsByName["pato"]!!] = 750.0
        precoemgold[petsByName["dragão de fogo"]!!] = 1000.0
        precoemgold[petsByName["girafa"]!!] = 1500.0
        precoemgold[petsByName["gorila"]!!] = 850.0
        precoemgold[petsByName["dragão de gelo"]!!] = 1000.0
        precoemgold[petsByName["coala"]!!] = 850.0
        precoemgold[petsByName["leão"]!!] = 850.0
        precoemgold[petsByName["minime"]!!] = 650.0
        precoemgold[petsByName["bb-8"]!!] = 1000.0
        precoemgold[petsByName["ursinho de pelúcia"]!!] = 500.0
        precoemgold[petsByName["pinguim"]!!] = 750.0
        precoemgold[petsByName["pug"]!!] = 850.0
        precoemgold[petsByName["tartaruga"]!!] = 1000.0
        precoemgold[petsByName["logo do youtube"]!!] = Double.MAX_VALUE
        precoemgold[petsByName["logo da twitch"]!!] = Double.MAX_VALUE

        for (pet in PetLoader.getPets()) {
            precoemcash[pet] = 0.0
            petsByName[pet.name.toLowerCase()] = pet
        }
        precoemcash[petsByName["urso"]!!] = 650.0
        precoemcash[petsByName["panda gigante"]!!] = Double.MAX_VALUE
        precoemcash[petsByName["boxer"]!!] = 1000.0
        precoemcash[petsByName["macaco"]!!] = 1350.0
        precoemcash[petsByName["diglet"]!!] = Double.MAX_VALUE
        precoemcash[petsByName["pato"]!!] = 500.0
        precoemcash[petsByName["dragão de fogo"]!!] = 1500.0
        precoemcash[petsByName["girafa"]!!] = 1850.0
        precoemcash[petsByName["gorila"]!!] = 1000.0
        precoemcash[petsByName["dragão de gelo"]!!] = 1500.0
        precoemcash[petsByName["coala"]!!] = 1000.0
        precoemcash[petsByName["leão"]!!] = 1000.0
        precoemcash[petsByName["minime"]!!] = 750.0
        precoemcash[petsByName["bb-8"]!!] = 1500.0
        precoemcash[petsByName["ursinho de pelúcia"]!!] = Double.MAX_VALUE
        precoemcash[petsByName["pinguim"]!!] = Double.MAX_VALUE
        precoemcash[petsByName["pug"]!!] = 1000.0
        precoemcash[petsByName["tartaruga"]!!] = Double.MAX_VALUE
        precoemcash[petsByName["logo do youtube"]!!] = Double.MAX_VALUE
        precoemcash[petsByName["logo da twitch"]!!] = Double.MAX_VALUE

        for (pet in PetLoader.getPets()) {
            groupPermission[pet] = "rmcosmetics.defaultperm"
            petsByName[pet.name.toLowerCase()] = pet
        }
        groupPermission[petsByName["urso"]!!] = "rmcosmetics.benefits.vip"
        groupPermission[petsByName["panda gigante"]!!] = "rmcosmetics.benefits.mvpplus"
        groupPermission[petsByName["boxer"]!!] = "rmcosmetics.defaultperm"
        groupPermission[petsByName["macaco"]!!] = "rmcosmetics.benefits.mvp"
        groupPermission[petsByName["diglet"]!!] = "rmcosmetics.defaultperm"
        groupPermission[petsByName["pato"]!!] = "rmcosmetics.defaultperm"
        groupPermission[petsByName["dragão de fogo"]!!] = "rmcosmetics.benefits.vip"
        groupPermission[petsByName["girafa"]!!] = "rmcosmetics.benefits.mvp"
        groupPermission[petsByName["gorila"]!!] = "rmcosmetics.defaultperm"
        groupPermission[petsByName["dragão de gelo"]!!] = "rmcosmetics.benefits.vip"
        groupPermission[petsByName["coala"]!!] = "rmcosmetics.defaultperm"
        groupPermission[petsByName["leão"]!!] = "rmcosmetics.defaultperm"
        groupPermission[petsByName["minime"]!!] = "rmcosmetics.defaultperm"
        groupPermission[petsByName["bb-8"]!!] = "rmcosmetics.benefits.mvp"
        groupPermission[petsByName["ursinho de pelúcia"]!!] = "rmcosmetics.defaultperm"
        groupPermission[petsByName["pinguim"]!!] = "rmcosmetics.defaultperm"
        groupPermission[petsByName["pug"]!!] = "rmcosmetics.benefits.mvpplus"
        groupPermission[petsByName["tartaruga"]!!] = "rmcosmetics.benefits.mvpplus"
        groupPermission[petsByName["logo do youtube"]!!] = "rmcosmetics.benefits.youtuber"
        groupPermission[petsByName["logo da twitch"]!!] = "rmcosmetics.benefits.streamer"

        for (pet in PetLoader.getPets()) {
            exclusiveGroupName[pet] = "§fExclusivo para §7Membro §fou superior."
            petsByName[pet.name.toLowerCase()] = pet
        }
        exclusiveGroupName[petsByName["urso"]!!] = "§fExclusivo para §aVIP §fou superior."
        exclusiveGroupName[petsByName["panda gigante"]!!] = "§fExclusivo para §bMVP§6+ §fou superior."
        exclusiveGroupName[petsByName["boxer"]!!] = "§fExclusivo para §7Membro §fou superior."
        exclusiveGroupName[petsByName["macaco"]!!] = "§fExclusivo para §6MVP §fou superior."
        exclusiveGroupName[petsByName["diglet"]!!] = "§fExclusivo para §7Membro §fou superior."
        exclusiveGroupName[petsByName["pato"]!!] = "§fExclusivo para §7Membro §fou superior."
        exclusiveGroupName[petsByName["dragão de fogo"]!!] = "§fExclusivo para §aVIP §fou superior."
        exclusiveGroupName[petsByName["girafa"]!!] = "§fExclusivo para §6MVP §fou superior."
        exclusiveGroupName[petsByName["gorila"]!!] = "§fExclusivo para §7Membro §fou superior."
        exclusiveGroupName[petsByName["dragão de gelo"]!!] = "§fExclusivo para §aVIP §fou superior."
        exclusiveGroupName[petsByName["coala"]!!] = "§fExclusivo para §7Membro §fou superior."
        exclusiveGroupName[petsByName["leão"]!!] = "§fExclusivo para §7Membro §fou superior."
        exclusiveGroupName[petsByName["minime"]!!] = "§fExclusivo para §7Membro §fou superior."
        exclusiveGroupName[petsByName["bb-8"]!!] = "§fExclusivo para §6MVP §fou superior."
        exclusiveGroupName[petsByName["ursinho de pelúcia"]!!] = "§fExclusivo para §7Membro §fou superior."
        exclusiveGroupName[petsByName["pinguim"]!!] = "§fExclusivo para §7Membro §fou superior."
        exclusiveGroupName[petsByName["pug"]!!] = "§fExclusivo para §bMVP§6+ §fou superior."
        exclusiveGroupName[petsByName["tartaruga"]!!] = "§fExclusivo para §bMVP§6+ §fou superior."
        exclusiveGroupName[petsByName["logo do youtube"]!!] = "§fExclusivo para §cYouTuber §fou superior."
        exclusiveGroupName[petsByName["logo da twitch"]!!] = "§fExclusivo para §9Streamer §fou superior."

        for (pet in PetLoader.getPets()) {
            rarity[pet] = "comum"
            petsByName[pet.name.toLowerCase()] = pet
        }
        rarity[petsByName["urso"]!!] = "raro"
        rarity[petsByName["panda gigante"]!!] = "divino"
        rarity[petsByName["boxer"]!!] = "epico"
        rarity[petsByName["macaco"]!!] = "epico"
        rarity[petsByName["diglet"]!!] = "comum"
        rarity[petsByName["pato"]!!] = "raro"
        rarity[petsByName["dragão de fogo"]!!] = "divino"
        rarity[petsByName["girafa"]!!] = "divino"
        rarity[petsByName["gorila"]!!] = "divino"
        rarity[petsByName["dragão de gelo"]!!] = "divino"
        rarity[petsByName["coala"]!!] = "epico"
        rarity[petsByName["leão"]!!] = "divino"
        rarity[petsByName["minime"]!!] = "raro"
        rarity[petsByName["bb-8"]!!] = "epico"
        rarity[petsByName["ursinho de pelúcia"]!!] = "comum"
        rarity[petsByName["pinguim"]!!] = "raro"
        rarity[petsByName["pug"]!!] = "epico"
        rarity[petsByName["tartaruga"]!!] = "epico"
        rarity[petsByName["logo do youtube"]!!] = "divino"
        rarity[petsByName["logo da twitch"]!!] = "divino"
    }
}