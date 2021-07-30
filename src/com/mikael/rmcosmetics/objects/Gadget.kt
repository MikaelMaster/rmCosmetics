package com.mikael.rmcosmetics.objects

import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.EventsManager
import org.bukkit.Material

open class Gadget(
    var name: String = "Engenhoca",
    var lore: List<String> = listOf(""),
    var material: ItemBuilder = ItemBuilder(Material.BARRIER),
    var delay: Int = 35,
    var permission: String = "rmcosmetics.gadget.gadget"
) : EventsManager() {

    var icon = ItemBuilder(Material.PISTON_BASE).name("Engenhoca").lore("")
}