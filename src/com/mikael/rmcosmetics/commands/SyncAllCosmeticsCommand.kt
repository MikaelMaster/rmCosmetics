package com.mikael.rmcosmetics.commands

import com.kirelcodes.miniaturepets.loader.PetLoader
import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.*
import net.eduard.api.lib.manager.CommandManager
import net.eduard.redemikael.core.api.miftCore
import net.eduard.redemikael.core.objects.MiftMysteryBoxItens
import org.bukkit.command.CommandSender

class SyncAllCosmeticsCommand : CommandManager("synccosmetics") {

    init {
        permission = "rmcore.group.gerente"
        permissionMessage = "§cVocê precisa do Grupo §4Gerente §cou superior para utilizar este comando!"
    }

    override fun command(sender: CommandSender, args: Array<String>) {
        MiftCosmetics.instance.asyncTask {
            sender.sendMessage("§eSincronizando cosméticos com mysql...")
            try {
                val values = miftCore.sqlManager.getAllData(MiftMysteryBoxItens::class.java)
                for (value in values) {
                    if (value.minigame == "lobby") {
                        value.delete()
                    }
                }
                for (item in HatSystem.hats) {
                    val insert = MiftMysteryBoxItens()
                    insert.minigame = "lobby"
                    insert.item = item.display
                    insert.itemDisplay = item.display
                    insert.rarity = item.rarity
                    insert.isCosmeticsBoxWinnable = true
                    insert.isSkyWarsBoxWinnable = false
                    insert.permission = item.permission
                    insert.itemType = "lobbyhat"
                    insert.insert()
                }
                for (item in HatAnimatedSystem.animatedHats) {
                    val insert = MiftMysteryBoxItens()
                    insert.minigame = "lobby"
                    insert.item = item.display
                    insert.itemDisplay = item.display
                    insert.rarity = item.rarity
                    insert.isCosmeticsBoxWinnable = true
                    insert.isSkyWarsBoxWinnable = false
                    insert.permission = item.permission
                    insert.itemType = "lobbyanimatedhat"
                    insert.insert()
                }
                for (item in BannerSystem.banners) {
                    val insert = MiftMysteryBoxItens()
                    insert.minigame = "lobby"
                    insert.item = item.display
                    insert.itemDisplay = item.display
                    insert.rarity = item.rarity
                    insert.isCosmeticsBoxWinnable = true
                    insert.isSkyWarsBoxWinnable = false
                    insert.permission = item.permission
                    insert.itemType = "lobbybanner"
                    insert.insert()
                }
                for (item in ParticleSystem.particles) {
                    val insert = MiftMysteryBoxItens()
                    insert.minigame = "lobby"
                    insert.item = item.display
                    insert.itemDisplay = item.display
                    insert.rarity = item.rarity
                    insert.isCosmeticsBoxWinnable = true
                    insert.isSkyWarsBoxWinnable = false
                    insert.permission = item.permission
                    insert.itemType = "lobbyparticle"
                    insert.insert()
                }
                for (item in GadgetSystem.gadgets) {
                    val insert = MiftMysteryBoxItens()
                    insert.minigame = "lobby"
                    insert.item = item.name
                    insert.itemDisplay = item.name
                    insert.rarity = item.rarity
                    insert.isCosmeticsBoxWinnable = true
                    insert.isSkyWarsBoxWinnable = false
                    insert.permission = item.permission
                    insert.itemType = "lobbygadget"
                    insert.insert()
                }
                for (item in PetLoader.getPets()) {
                    val insert = MiftMysteryBoxItens()
                    val petCosmetic = CompanionSystem.rarity[item]!!
                    insert.minigame = "lobby"
                    insert.item = item.name
                    insert.itemDisplay = item.name
                    insert.rarity = petCosmetic
                    insert.isCosmeticsBoxWinnable = true
                    insert.isSkyWarsBoxWinnable = false
                    insert.permission = item.permission
                    insert.itemType = "lobbycompanion"
                    insert.insert()
                }
                sender.sendMessage("§aTodos os cosméticos foram sincronizados com sucesso!")
            } catch (ex: Exception) {
                ex.printStackTrace()
                sender.sendMessage("§cOcorreu um erro interno ao sincronizar os cosméticos.")
            }
        }
    }
}