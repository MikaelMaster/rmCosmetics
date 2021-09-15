package com.mikael.rmcosmetics.objects

import net.eduard.api.lib.game.ItemBuilder
import org.bukkit.DyeColor
import org.bukkit.block.banner.Pattern
import org.bukkit.inventory.ItemFlag

class Banner(
    var display: String = "§aBanner",
    var lore: List<String> = listOf(""),
    var rarity: String = "comum",
    var permission: String = "rmcosmetics.banner.banner",

    var basecolor: DyeColor = DyeColor.BLACK,
    vararg var patterns: Pattern = arrayOf(),
) {

    fun icon(): ItemBuilder {
        val item = ItemBuilder()

            .name("§a$display")
            .lore(lore)

        for (pattern in patterns) {
            item.banner(basecolor, pattern.color, pattern.pattern)
        }
        item.addFlags(*ItemFlag.values())
        return item
    }
}