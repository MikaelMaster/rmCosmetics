package com.mikael.rmcosmetics.core

import com.mikael.rmcosmetics.objects.CosmeticsData
import net.eduard.redemikael.core.api.miftCore
import net.eduard.redemikael.core.objects.MiftProfile
import net.eduard.redemikael.core.user
import org.bukkit.entity.Player

object CosmeticsUtils {

    // Lista de Players que os cosméticos estão sendo carregados.
    val loadingCosmetics = mutableSetOf<Player>()

    // Lista de Profiles que possuem um item selecionado.
    val selecteds = mutableMapOf<MiftProfile, CosmeticsData>()

    /**
     * Carrega todos os cosméticos de um Player.
     *
     * Retorna:
     * TRUE caso tudo tenha sido carregado corretamente sem erros.
     * FALSE caso ecorra um erro durante o carregamento ou caso
     * o Player não possuem nenhum cosmético selecionado.
     */
    fun loadAllCosmetics(player: Player): CosmeticsData? {
        loadingCosmetics.add(player)
        val user = player.user

        // Carrega o Closet do player.
        if (player.hasPermission("rmcore.benefits.vip")) {
            ClosetSystem.loadCloset(user)
        }

        val query = miftCore.sqlManager.getDataOf<CosmeticsData>(user)
        if (query == null) {
            selecteds.remove(user)
            loadingCosmetics.remove(player)
            return null
        } else {
            selecteds[user] = query
        }

        BannerSystem.bannersSelected[user] = query
        val banner = BannerSystem.banners.firstOrNull { it.display == query.banner }
        if (banner != null) {
            BannerSystem.usingBanner[player] = banner
        }

        GadgetSystem.gadgetsSelected[user] = query
        val gadget = GadgetSystem.gadgets.firstOrNull { it.name == query.gadget }
        if (gadget != null) {
            GadgetSystem.usingGadget[player] = gadget
        }

        HatAnimatedSystem.hatsSelected[user] = query
        val animtedHat = HatAnimatedSystem.animatedHats.firstOrNull { it.display == query.animatedHat }
        if (animtedHat != null) {
            HatAnimatedSystem.usingAnimatedHat[player] = animtedHat
        }

        HatSystem.hatsSelected[user] = query
        val hat = HatSystem.hats.firstOrNull { it.display == query.hat }
        if (hat != null) {
            HatSystem.usingHat[player] = hat
        }
        ParticleSystem.particlesSelected[user] = query
        val particle = ParticleSystem.particles.firstOrNull { it.display == query.particle }
        if (particle != null) {
            ParticleSystem.usingParticle[player] = particle
        }
        PetSystem.petsSelected[user] = query
        val pet = PetSystem.pets.firstOrNull { it.name == query.pet }
        if (pet != null) {
            PetSystem.usingPet[player] = pet
        }
        CompanionSystem.companionsSelected[user] = query
        loadingCosmetics.remove(player)
        return query
    }

}