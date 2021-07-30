package com.mikael.rmcosmetics.menu

import com.mikael.rmcosmetics.core.BannerSystem
import com.mikael.rmcosmetics.core.ClosetSystem
import com.mikael.rmcosmetics.core.HatAnimatedSystem
import com.mikael.rmcosmetics.core.HatSystem
import com.mikael.rmcosmetics.percentColor
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.game.SoundEffect
import net.eduard.api.lib.kotlin.percent
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenEffect
import net.eduard.redemikael.core.soundWhenNoEffect
import org.bukkit.Material
import org.bukkit.Sound

class MenuBanners : Menu("Banners", 6) {

    companion object {
        lateinit var instance: MenuBanners
    }

    init {
        instance = this

        isAutoAlignItems = true
        autoAlignSkipLines = listOf(1, 5, 6)
        autoAlignSkipColumns = listOf(9, 1)
        autoAlignPerLine = 7
        autoAlignPerPage = 3 * autoAlignPerLine


        for (banner in BannerSystem.banners) {
            button {

                iconPerPlayer = {
                    val item = banner.icon()

                    if (hasPermission(banner.permission)) {

                        if (BannerSystem.hasSelected(player)) {
                            val bannerUsed = BannerSystem.getSelectedBanner(player)
                            if (bannerUsed == banner) {
                                item.lore(
                                    "§8Banner",
                                    "",
                                    "§7Exiba seu estilo em nossos lobbies",
                                    "§7utilizando o banner ${banner.display}§7.",
                                    "",
                                    "§eClique para remover!"
                                )
                            } else {
                                item.lore(
                                    "§8Banner",
                                    "",
                                    "§7Exiba seu estilo em nossos lobbies",
                                    "§7utilizando o banner ${banner.display}§7.",
                                    "",
                                    "§eClique para utilizar!"
                                )
                            }
                        } else {
                            item.lore(
                                "§8Banner",
                                "",
                                "§7Exiba seu estilo em nossos lobbies",
                                "§7utilizando o banner ${banner.display}§7.",
                                "",
                                "§eClique para utilizar!"
                            )
                        }
                    } else {
                        item.lore(
                            "§8Banner",
                            "",
                            "§7Exiba seu estilo em nossos lobbies",
                            "§7utilizando o banner ${banner.display}§7.",
                            "",
                            "§cVocê não possui esse banner."
                        )
                    }

                    item
                }
                click = ClickEffect {
                    val player = it.player

                    if (player.hasPermission(banner.permission)) {
                        if (BannerSystem.hasSelected(player)) {
                            val bannerUsed = BannerSystem.getSelectedBanner(player)
                            if (bannerUsed == banner) {
                                player.soundWhenEffect()
                                player.sendMessage("§cVocê removeu o banner ${banner.display}.")
                                player.equipment.helmet = null
                                BannerSystem.deselect(player)
                                open(player, getPageOpen(player))
                            } else {
                                player.soundWhenEffect()

                                if (HatAnimatedSystem.hasSelected(player)) {
                                    HatAnimatedSystem.deselect(player)
                                }
                                if (HatSystem.hasSelected(player)) {
                                    HatSystem.deselect(player)
                                }
                                val closet = ClosetSystem.getPlayerCloset(player)
                                if (closet.helmet != null) {
                                    closet.helmet = null
                                    closet.helmetBright = false
                                    closet.helmetName = null
                                    closet.updateQueue()
                                }
                                player.equipment.helmet = null
                                player.sendMessage("§aVocê equipou o banner ${banner.display}.")
                                player.equipment.helmet = banner.icon()
                                BannerSystem.select(player, banner)
                                open(player, getPageOpen(player))
                            }
                        } else {
                            player.soundWhenEffect()

                            if (HatAnimatedSystem.hasSelected(player)) {
                                HatAnimatedSystem.deselect(player)
                            }
                            if (HatSystem.hasSelected(player)) {
                                HatSystem.deselect(player)
                            }
                            val closet = ClosetSystem.getPlayerCloset(player)
                            if (closet.helmet != null) {
                                closet.helmet = null
                                closet.helmetBright = false
                                closet.helmetName = null
                                closet.updateQueue()
                            }
                            player.equipment.helmet = null
                            player.sendMessage("§aVocê equipou o banner ${banner.display}.")
                            player.equipment.helmet = banner.icon()
                            BannerSystem.select(player, banner)
                            open(player, getPageOpen(player))
                        }
                    } else {
                        player.soundWhenNoEffect()
                    }
                }

                button("remove-banner") {
                    setPosition(4, 6)

                    fixed = true
                    iconPerPlayer = {
                        ItemBuilder(Material.BARRIER)
                            .name("§cRemover Banner")
                            .lore("§7Remove seu banner atual.")
                    }
                    click = ClickEffect {
                        val player = it.player

                        player.soundWhenEffect()
                        if (BannerSystem.hasSelected(player)) {
                            player.equipment.helmet = null
                            BannerSystem.deselect(player)
                            player.sendMessage("§cSeu banner atual foi removido.")
                            open(player, getPageOpen(player))
                        } else {
                            player.sendMessage("§cVocê não possui um banner selecionado.")
                        }
                    }
                }
            }
        }
        button("info") {
            setPosition(6, 6)

            fixed = true
            iconPerPlayer = {
                val player = this
                var usedBannerName = "Nenhum"

                if (BannerSystem.hasSelected(player)) {

                    val usedBanner = BannerSystem.getSelectedBanner(player)
                    val bannerName = usedBanner.display
                    usedBannerName = bannerName
                }

                val bannersDesbloqueados = BannerSystem.banners.count {
                    hasPermission(it.permission)
                }

                var porcentagemDesbloqueada = bannersDesbloqueados.toDouble() / BannerSystem.banners.size
                var porcentagemTexto = porcentagemDesbloqueada.percent() + "%"
                val corNumero = porcentagemDesbloqueada.percentColor()

                ItemBuilder(Material.PAPER)
                    .name("§aInformações")
                    .lore(
                        "§8Banners",
                        "",
                        "§7Você pode encontrar novos",
                        "§7banners em §bCaixas Misteriosas§7.",
                        "",
                        "§fDesbloqueados: ${corNumero}${bannersDesbloqueados}/${BannerSystem.banners.size} §8(${porcentagemTexto})",
                        "§fSelecionado atualmente:",
                        "§a▸ ${usedBannerName}"
                    )
            }
            click = ClickEffect {
                it.player.soundWhenNoEffect()
            }
        }

        backPage.item = ItemBuilder(Material.INK_SACK).data(1)
            .name("§cVoltar")
            .lore("§7Para Cosméticos.")
        backPage.setPosition(5, 6)
        backPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

        nextPage.item = ItemBuilder(Material.INK_SACK).data(10)
            .name("§aPágina %page")
            .lore("§7Ir até a página %page.")
        nextPage.setPosition(9, 6)
        nextPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

        previousPage.item = ItemBuilder(Material.INK_SACK).data(8)
            .name("§aPágina %page")
            .lore("§7Ir até a página %page.")
        previousPage.setPosition(1, 6)
        previousPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

    }
}
