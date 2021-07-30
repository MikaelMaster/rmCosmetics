package com.mikael.rmcosmetics.core

import com.mikael.rmcosmetics.objects.Banner
import com.mikael.rmcosmetics.objects.BannerData
import net.eduard.redemikael.core.api.miftCore
import net.eduard.redemikael.core.objects.MiftProfile
import net.eduard.redemikael.core.user
import org.bukkit.DyeColor
import org.bukkit.block.banner.Pattern
import org.bukkit.block.banner.PatternType
import org.bukkit.entity.Player

object BannerSystem {

    var usingBanner = mutableMapOf<Player, Banner>()
    var banners = mutableListOf<Banner>()
    var bannersSelected = mutableMapOf<MiftProfile, BannerData>()

    fun select(player: Player, banner: Banner) {
        val user = player.user
        val selected = getOrCreate(user)
        selected.banner = banner.display
        usingBanner[player] = banner
        selected.updateQueue()
    }

    fun hasSelected(player: Player): Boolean {
        return usingBanner.containsKey(player)
    }

    fun getSelectedBanner(player: Player): Banner {
        return usingBanner[player]!!
    }

    fun deselect(player: Player) {
        val user = player.user
        val selected = getOrCreate(user)
        selected.banner = null
        usingBanner.remove(player)
        selected.updateQueue()
    }

    fun load(player: Player) {
        val profile = player.user
        val bannerSelected = miftCore.sqlManager.getDataOf<BannerData>(profile) ?: return
        bannersSelected[profile] = bannerSelected
        bannersSelected[profile] = bannerSelected
        val banner = banners.firstOrNull { it.display == bannerSelected.banner } ?: return
        usingBanner[player] = banner
    }

    fun getOrCreate(profile: MiftProfile): BannerData {
        if (bannersSelected.containsKey(profile)) {
            return bannersSelected[profile]!!
        }
        val bannerSelected = BannerData()
        bannerSelected.player = profile
        bannerSelected.insert()
        bannersSelected[profile] = bannerSelected
        return bannerSelected
    }

    init {

        val banner1 = Banner(
            "Parede de Tijolos", listOf(""),
            DyeColor.BLACK,
            Pattern(DyeColor.RED, PatternType.BRICKS)
        )

        val banner2 = Banner(
            "Tardis", listOf(""),
            DyeColor.BLUE,
            Pattern(DyeColor.WHITE, PatternType.BRICKS),
            Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM),
            Pattern(DyeColor.BLACK, PatternType.STRAIGHT_CROSS),
            Pattern(DyeColor.BLUE, PatternType.STRAIGHT_CROSS),
            Pattern(DyeColor.BLACK, PatternType.BORDER),
            Pattern(DyeColor.BLUE, PatternType.BORDER)
        )

        val banner3 = Banner(
            "Trilha de Lava", listOf(""),
            DyeColor.ORANGE,
            Pattern(DyeColor.RED, PatternType.STRIPE_TOP),
            Pattern(DyeColor.ORANGE, PatternType.GRADIENT),
            Pattern(DyeColor.BLACK, PatternType.CURLY_BORDER),
            Pattern(DyeColor.BLACK, PatternType.BORDER),
            Pattern(DyeColor.BLACK, PatternType.SQUARE_BOTTOM_RIGHT),
            Pattern(DyeColor.BLACK, PatternType.SQUARE_TOP_LEFT),
            Pattern(DyeColor.BLACK, PatternType.BRICKS)
        )

        val banner4 = Banner(
            "Lua Cheia", listOf(""),
            DyeColor.BLACK,
            Pattern(DyeColor.WHITE, PatternType.MOJANG),
            Pattern(DyeColor.BLACK, PatternType.FLOWER),
            Pattern(DyeColor.BLUE, PatternType.GRADIENT),
            Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE),
            Pattern(DyeColor.BLACK, PatternType.SQUARE_BOTTOM_LEFT),
            Pattern(DyeColor.BLACK, PatternType.TRIANGLES_BOTTOM)
        )

        /*
        val banner5 = Banner("Taco", listOf(""),
            DyeColor.WHITE,
            Pattern(DyeColor.RED, PatternType.SKULL),
            Pattern(DyeColor.LIME, PatternType.FLOWER),
            Pattern(DyeColor.YELLOW, PatternType.MOJANG),
            Pattern(DyeColor.YELLOW, PatternType.CIRCLE_MIDDLE),
            Pattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM)
            )
            */

        banners.add(banner1)
        banners.add(banner2)
        banners.add(banner3)
        banners.add(banner4)
    }
}