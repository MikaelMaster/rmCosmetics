package com.mikael.rmcosmetics.core

import com.mikael.rmcosmetics.gadgets.*
import com.mikael.rmcosmetics.objects.Gadget
import com.mikael.rmcosmetics.objects.GadgetData
import net.eduard.redemikael.core.api.miftCore
import net.eduard.redemikael.core.objects.MiftProfile
import net.eduard.redemikael.core.user
import org.bukkit.Location
import org.bukkit.entity.Player
import javax.swing.Painter

object GadgetSystem {

    var blocksList = mutableListOf<Location>()

    var activeGadget = mutableListOf<Player>()

    var usingGadget = mutableMapOf<Player, Gadget>()
    var gadgets = mutableListOf<Gadget>()
    var gadgetsSelected = mutableMapOf<MiftProfile, GadgetData>()

    fun hasActiveGadget(player: Player): Boolean {
        return activeGadget.contains(player)
    }

    fun addBlockToList(location: Location) {
        blocksList.add(location)
    }

    fun removeBlockToList(location: Location) {
        blocksList.remove(location)
    }

    fun putActiveGadget(player: Player) {
        activeGadget.add(player)
    }

    fun removeActiveGadget(player: Player) {
        activeGadget.remove(player)
    }

    fun select(player: Player, gadget: Gadget) {
        val user = player.user
        val selected = getOrCreate(user)
        selected.gadget = gadget.name
        usingGadget[player] = gadget
        selected.updateOnlyQueue("gadget")
    }

    fun hasSelected(player: Player): Boolean {
        return usingGadget.containsKey(player)
    }

    fun getSelectedGadget(player: Player): Gadget {
        return usingGadget[player]!!
    }

    fun deselect(player: Player) {
        val user = player.user
        val selected = getOrCreate(user)
        selected.gadget = null
        usingGadget.remove(player)
        selected.updateOnlyQueue("gadget")
    }

    fun load(player: Player) {
        val profile = player.user
        val gadgetSelected = miftCore.sqlManager.getDataOf<GadgetData>(profile) ?: return
        gadgetsSelected[profile] = gadgetSelected
        val gadget = gadgets.firstOrNull { it.name == gadgetSelected.gadget } ?: return
        usingGadget[player] = gadget
    }

    fun getOrCreate(profile: MiftProfile): GadgetData {
        if (gadgetsSelected.containsKey(profile)) {
            return gadgetsSelected[profile]!!
        }
        val gadgetSelected = GadgetData()
        gadgetSelected.player = profile
        gadgetSelected.insert()
        gadgetsSelected[profile] = gadgetSelected
        return gadgetSelected
    }

    init {
        gadgets.add(FireworkShowGadget())
        gadgets.add(BombManGadget())
        gadgets.add(ThunderGadget())
        gadgets.add(JumpJumpGadget())
        gadgets.add(EggRainfallGadget())
        // gadgets.add(MountGadget())
        gadgets.add(RabbitsFamilyGadget())
        gadgets.add(FlyingHorseGadget())
        // gadgets.add(FlyingCarGadget())
        // gadgets.add(EnderDragonGadget())
        gadgets.add(FireworkGadget())
        gadgets.add(StrawManGadget())
        gadgets.add(GhostsGadget())
        gadgets.add(GoldFountainGadget())
        gadgets.add(BreadsGadget())
        gadgets.add(CreeperGadget())
        gadgets.add(GiftGadget())
        gadgets.add(DiscothequeGadget())
        gadgets.add(BatsGadget())
        gadgets.add(FireFootsGadget())
        gadgets.add(PainterGadget())
        gadgets.add(ExplosiveSheepGadget())
        // gadgets.add(SnowStormGadget())
        // gadgets.add(ChristmasTreeGadget())
        // gadgets.add(GraveStoneGadget())
    }
}