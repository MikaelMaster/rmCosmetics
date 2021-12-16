package com.mikael.rmcosmetics.core

import com.mikael.rmcosmetics.objects.CosmeticsData
import com.mikael.rmcosmetics.objects.MiftPet
import com.mikael.rmcosmetics.objects.MiftPetData
import net.eduard.redemikael.core.api.miftCore
import net.eduard.redemikael.core.isInvulnerable
import net.eduard.redemikael.core.objects.MiftProfile
import net.eduard.redemikael.core.user
import org.bukkit.entity.*
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object PetSystem {

    /**
     * Sistema de Pets do rmCosmetics
     *
     * Desenvolvido por Mikael (Início em 26/10/2021)
     */

    val spawnedNavigators = mutableMapOf<Player, Creature>()
    val spawnedPets = mutableMapOf<Entity, Entity>()

    val pets = mutableListOf<MiftPet>()

    // Retorna true se o jogador possui um pet (navigator) spawnado
    private fun hasSpawnedPet(player: Player): Boolean {
        return spawnedNavigators.containsKey(player)
    }

    // Remove um pet spawnado de um jogador (Navigator e pet)
    fun removePet(owner: Player, sendMessage: Boolean = false) {
        try {
            if (!hasSpawnedPet(owner)) return
            val petNavigator = spawnedNavigators[owner]!!
            val pet = spawnedPets[petNavigator]!!
            pet.remove()
            petNavigator.remove()
            spawnedPets.remove(petNavigator)
            spawnedNavigators.remove(owner)
            if (sendMessage) {
                owner.sendMessage("§6[Cosméticos] §cSeu pet foi removido.")
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            owner.sendMessage("§6[Cosméticos] §cOcorreu um erro interno ao remover seu pet. :c")
        }
    }

    // Cria um pet para um jogador (Navigator e pet)
    fun createPet(owner: Player, petType: EntityType, sendMessage: Boolean = false) {
        try {
            val petNavigator = owner.world.spawn(owner.location, Zombie::class.java)
            petNavigator.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, Int.MAX_VALUE, 1))
            petNavigator.isBaby = true
            petNavigator.isVillager = false
            petNavigator.isInvulnerable(true)
            petNavigator.equipment.clear()
            val pet = owner.world.spawnEntity(petNavigator.location, petType)
            if (pet is Creature) {
                pet.target = null
            }
            pet.isCustomNameVisible = true
            pet.isInvulnerable(true)
            spawnedNavigators[owner] = petNavigator
            spawnedPets[petNavigator] = pet
            if (sendMessage) {
                owner.sendMessage("§6[Cosméticos] §aSeu pet foi spawnado!")
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            owner.sendMessage("§6[Cosméticos] §cOcorreu um erro interno ao spawnar seu pet. :c")
        }
    }

    /**
     * Métodos relacionados a seleção e mysql
     */
    var usingPet = mutableMapOf<Player, MiftPet>()
    var petsSelected = mutableMapOf<MiftProfile, CosmeticsData>()

    fun select(player: Player, pet: MiftPet) {
        val user = player.user
        val selected = getOrCreate(user)
        selected.pet = pet.name
        usingPet[player] = pet
        selected.updateOnlyQueue("pet")
    }

    fun hasSelected(player: Player): Boolean {
        return usingPet.containsKey(player)
    }

    fun getSelected(player: Player): MiftPet {
        return usingPet[player]!!
    }

    fun deselect(player: Player) {
        val user = player.user
        val selected = getOrCreate(user)
        selected.pet = null
        usingPet.remove(player)
        selected.updateOnlyQueue("pet")
    }

    fun load(player: Player) {
        val profile = player.user
        val selected = miftCore.sqlManager.getDataOf<CosmeticsData>(profile) ?: return
        petsSelected[profile] = selected
        val pet = pets.firstOrNull { it.name == selected.pet } ?: return
        usingPet[player] = pet
    }

    private fun getOrCreate(profile: MiftProfile): CosmeticsData {
        if (CosmeticsUtils.selecteds.containsKey(profile)) {
            return CosmeticsUtils.selecteds[profile]!!
        }
        val selected = CosmeticsData()
        selected.player = profile
        selected.insert()
        petsSelected[profile] = selected
        CosmeticsUtils.selecteds[profile] = selected
        return selected
    }

    init {
        val pet1 = MiftPet(
            "enderman",
            "Enderman",
            "divino",
            "http://textures.minecraft.net/texture/96c0b36d53fff69a49c7d6f3932f2b0fe948e032226d5e8045ec58408a36e951",
            EntityType.ENDERMAN,
            false,
            "mvpplus",
            true,
            true,
            true,
            1500.0,
            2000.0
        )

        pets.add(pet1)
    }
}