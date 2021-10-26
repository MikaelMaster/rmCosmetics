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

    /**
     * Sistema de Banners do rmCosmetics
     *
     * Desenvolvido por Mikael e Eduard
     */

    var usingBanner = mutableMapOf<Player, Banner>()
    var banners = mutableListOf<Banner>()
    var bannersSelected = mutableMapOf<MiftProfile, BannerData>()

    fun select(player: Player, banner: Banner) {
        val user = player.user
        val selected = getOrCreate(user)
        selected.banner = banner.display
        usingBanner[player] = banner
        selected.updateOnlyQueue("banner")
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
        selected.updateOnlyQueue("banner")
    }

    fun load(player: Player) {
        val profile = player.user
        val bannerSelected = miftCore.sqlManager.getDataOf<BannerData>(profile) ?: return
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
            "comum",
            "rmcosmetics.banner.parede_de_tijolos",
            DyeColor.BLACK,
            Pattern(DyeColor.RED, PatternType.BRICKS)
        )
        val banner2 = Banner(
            "Tardis", listOf(""),
            "divino",
            "rmcosmetics.banner.tardis",
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
            "raro",
            "rmcosmetics.banner.trilha_de_lava",
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
            "epico",
            "rmcosmetics.banner.lua_cheia",
            DyeColor.BLACK,
            Pattern(DyeColor.WHITE, PatternType.MOJANG),
            Pattern(DyeColor.BLACK, PatternType.FLOWER),
            Pattern(DyeColor.BLUE, PatternType.GRADIENT),
            Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE),
            Pattern(DyeColor.BLACK, PatternType.SQUARE_BOTTOM_LEFT),
            Pattern(DyeColor.BLACK, PatternType.TRIANGLES_BOTTOM)
        )
        /*
        val banner5 = Banner(
            "Salwyrr Launcher", listOf(""),
            DyeColor.WHITE,
            Pattern(DyeColor.BLACK, PatternType.HALF_VERTICAL),
            Pattern(DyeColor.BLACK, PatternType.HALF_HORIZONTAL_MIRROR),
            Pattern(DyeColor.ORANGE, PatternType.TRIANGLE_TOP),
            Pattern(DyeColor.WHITE, PatternType.TRIANGLE_BOTTOM),
            Pattern(DyeColor.ORANGE, PatternType.SQUARE_TOP_RIGHT),
            Pattern(DyeColor.ORANGE, PatternType.SQUARE_TOP_LEFT),
            Pattern(DyeColor.BLACK, PatternType.RHOMBUS_MIDDLE),
            Pattern(DyeColor.ORANGE, PatternType.STRIPE_DOWNRIGHT),
            Pattern(DyeColor.BLACK, PatternType.BORDER)
        )
         */
        val banner6 = Banner(
            "Taco", listOf(""),
            "raro",
            "rmcosmetics.banner.taco",
            DyeColor.BLACK,
            Pattern(DyeColor.RED, PatternType.SKULL),
            Pattern(DyeColor.RED, PatternType.CREEPER),
            Pattern(DyeColor.LIME, PatternType.FLOWER),
            Pattern(DyeColor.YELLOW, PatternType.MOJANG),
            Pattern(DyeColor.YELLOW, PatternType.CIRCLE_MIDDLE),
            Pattern(DyeColor.BLACK, PatternType.HALF_HORIZONTAL_MIRROR)
        )
        val banner7 = Banner(
            "Meia Lua", listOf(""),
            "epico",
            "rmcosmetics.banner.meialua",
            DyeColor.BLUE,
            Pattern(DyeColor.WHITE, PatternType.MOJANG),
            Pattern(DyeColor.BLUE, PatternType.STRIPE_RIGHT),
            Pattern(DyeColor.BLUE, PatternType.CIRCLE_MIDDLE),
            Pattern(DyeColor.SILVER, PatternType.GRADIENT_UP),
            Pattern(DyeColor.PURPLE, PatternType.GRADIENT_UP)
        )
        val banner8 = Banner(
            "Olhos", listOf(""),
            "raro",
            "rmcosmetics.banner.olhos",
            DyeColor.BLACK,
            Pattern(DyeColor.LIGHT_BLUE, PatternType.FLOWER),
            Pattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE),
            Pattern(DyeColor.BLACK, PatternType.SKULL),
            Pattern(DyeColor.BLACK, PatternType.CIRCLE_MIDDLE),
            Pattern(DyeColor.BLACK, PatternType.HALF_HORIZONTAL)
        )
        val banner9 = Banner(
            "Superman", listOf(""),
            "comum",
            "rmcosmetics.banner.superman",
            DyeColor.YELLOW,
            Pattern(DyeColor.RED, PatternType.STRIPE_BOTTOM),
            Pattern(DyeColor.RED, PatternType.STRIPE_TOP),
            Pattern(DyeColor.YELLOW, PatternType.RHOMBUS_MIDDLE),
            Pattern(DyeColor.RED, PatternType.STRIPE_DOWNRIGHT),
            Pattern(DyeColor.RED, PatternType.BORDER),
            Pattern(DyeColor.BLUE, PatternType.BORDER)
        )
        val banner10 = Banner(
            "Onda", listOf(""),
            "raro",
            "rmcosmetics.banner.onda",
            DyeColor.LIGHT_BLUE,
            Pattern(DyeColor.BLUE, PatternType.CURLY_BORDER),
            Pattern(DyeColor.BLUE, PatternType.BORDER),
            Pattern(DyeColor.LIGHT_BLUE, PatternType.DIAGONAL_RIGHT_MIRROR),
            Pattern(DyeColor.BLUE, PatternType.MOJANG),
            Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM)
        )
        val banner11 = Banner(
            "Congelando", listOf(""),
            "divino",
            "rmcosmetics.banner.congelando",
            DyeColor.LIGHT_BLUE,
            Pattern(DyeColor.WHITE, PatternType.BRICKS),
            Pattern(DyeColor.WHITE, PatternType.DIAGONAL_RIGHT),
            Pattern(DyeColor.SILVER, PatternType.TRIANGLES_BOTTOM),
            Pattern(DyeColor.SILVER, PatternType.STRIPE_MIDDLE),
            Pattern(DyeColor.SILVER, PatternType.STRIPE_CENTER),
            Pattern(DyeColor.BLUE, PatternType.STRAIGHT_CROSS)
        )
        val banner12 = Banner(
            "Mundo Roxo", listOf(""),
            "epico",
            "rmcosmetics.banner.mundoroxo",
            DyeColor.BLACK,
            Pattern(DyeColor.PURPLE, PatternType.CROSS),
            Pattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE),
            Pattern(DyeColor.PURPLE, PatternType.FLOWER)

        )
        val banner13 = Banner(
            "Esqueleto do Demônio", listOf(""),
            "divino",
            "rmcosmetics.banner.esqueletododemonio",
            DyeColor.BLACK,
            Pattern(DyeColor.ORANGE, PatternType.GRADIENT_UP),
            Pattern(DyeColor.GRAY, PatternType.BRICKS),
            Pattern(DyeColor.ORANGE, PatternType.CURLY_BORDER),
            Pattern(DyeColor.GRAY, PatternType.STRIPE_DOWNLEFT),
            Pattern(DyeColor.BLACK, PatternType.SKULL),
            Pattern(DyeColor.BLACK, PatternType.BORDER)
        )
        val banner14 = Banner(
            "Sol", listOf(""),
            "raro",
            "rmcosmetics.banner.sol",
            DyeColor.WHITE,
            Pattern(DyeColor.CYAN, PatternType.CROSS),
            Pattern(DyeColor.YELLOW, PatternType.GRADIENT),
            Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE),
            Pattern(DyeColor.YELLOW, PatternType.GRADIENT_UP),
            Pattern(DyeColor.PINK, PatternType.FLOWER),
            Pattern(DyeColor.YELLOW, PatternType.FLOWER)
        )
        val banner15 = Banner(
            "Coala", listOf(""),
            "comum",
            "rmcosmetics.banner.coala",
            DyeColor.CYAN,
            Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE),
            Pattern(DyeColor.CYAN, PatternType.HALF_HORIZONTAL_MIRROR),
            Pattern(DyeColor.SILVER, PatternType.STRIPE_BOTTOM),
            Pattern(DyeColor.BLACK, PatternType.TRIANGLES_BOTTOM),
            Pattern(DyeColor.SILVER, PatternType.CURLY_BORDER),
            Pattern(DyeColor.CYAN, PatternType.HALF_HORIZONTAL)
        )
        val banner16 = Banner(
            "Castelo Demoníaco", listOf(""),
            "divino",
            "rmcosmetics.banner.castelofemoniaco",
            DyeColor.WHITE,
            Pattern(DyeColor.RED, PatternType.GRADIENT_UP),
            Pattern(DyeColor.BLACK, PatternType.GRADIENT),
            Pattern(DyeColor.GRAY, PatternType.BORDER),
            Pattern(DyeColor.GRAY, PatternType.STRIPE_DOWNLEFT),
            Pattern(DyeColor.BLACK, PatternType.SKULL),
            Pattern(DyeColor.BLACK, PatternType.FLOWER)
        )
        val banner17 = Banner(
            "Cristais", listOf(""),
            "epico",
            "rmcosmetics.banner.cristais",
            DyeColor.BLACK,
            Pattern(DyeColor.BLUE, PatternType.RHOMBUS_MIDDLE),
            Pattern(DyeColor.BLACK, PatternType.MOJANG),
            Pattern(DyeColor.BLACK, PatternType.GRADIENT_UP)
        )
        val banner18 = Banner(
            "Rosto Assustador", listOf(""),
            "raro",
            "rmcosmetics.banner.rostoassustador",
            DyeColor.BLACK,
            Pattern(DyeColor.RED, PatternType.TRIANGLES_BOTTOM),
            Pattern(DyeColor.RED, PatternType.CURLY_BORDER),
            Pattern(DyeColor.RED, PatternType.STRIPE_TOP),
            Pattern(DyeColor.RED, PatternType.CIRCLE_MIDDLE),
            Pattern(DyeColor.RED, PatternType.CREEPER)
        )
        val banner19 = Banner(
            "Sinaleira", listOf(""),
            "comum",
            "rmcosmetics.banner.sinaleira",
            DyeColor.LIME,
            Pattern(DyeColor.RED, PatternType.STRIPE_TOP),
            Pattern(DyeColor.YELLOW, PatternType.STRIPE_BOTTOM),
            Pattern(DyeColor.BLACK, PatternType.CURLY_BORDER),
            Pattern(DyeColor.BLACK, PatternType.BORDER)
        )

        banners.add(banner1)
        banners.add(banner2)
        banners.add(banner3)
        banners.add(banner4)
        // banners.add(banner5)
        banners.add(banner6)
        banners.add(banner7)
        banners.add(banner8)
        banners.add(banner9)
        banners.add(banner10)
        banners.add(banner11)
        banners.add(banner12)
        banners.add(banner13)
        banners.add(banner14)
        banners.add(banner15)
        banners.add(banner16)
        banners.add(banner17)
        banners.add(banner18)
        banners.add(banner19)
    }
}