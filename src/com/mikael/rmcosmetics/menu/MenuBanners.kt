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
        instance = this@MenuBanners
        isAutoAlignItems = true
        autoAlignSkipLines = listOf(1, 5, 6)
        autoAlignSkipColumns = listOf(9, 1)
        autoAlignPerLine = 7
        autoAlignPerPage = 3 * autoAlignPerLine
        update()
    }

    override fun update() {
        removeAllButtons()

        for (banner in BannerSystem.banners) {
            button(banner.display) {
                iconPerPlayer = {
                    val player = this
                    val item = banner.icon()

                    if (!player.hasPermission(banner.permission)) {
                        item.type(Material.INK_SACK).data(8)
                    }

                    if (BannerSystem.hasSelected(player)) {
                        if (BannerSystem.getSelectedBanner(player) == banner) {
                            item.name("§6${banner.display}")
                            item.glowed()
                        } else if (player.hasPermission(banner.permission)) {
                            item.name("§a${banner.display}")
                        } else {
                            item.name("§c${banner.display}")
                        }
                    } else if (player.hasPermission(banner.permission)) {
                        item.name("§a${banner.display}")
                    } else {
                        item.name("§c${banner.display}")
                    }

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
                            "§eCaixas contendo este item:",
                            " §8▪ §bCaixa Misteriosa de Cosméticos",
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
                                player.sendMessage("§cVocê removeu o banner '${banner.display}'.")
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
                                if (player.hasPermission("rmcore.benefits.vip")) {
                                    val closet = ClosetSystem.getPlayerCloset(player)
                                    if (closet.helmet != null) {
                                        closet.helmet = null
                                        closet.helmetBright = false
                                        closet.helmetName = null
                                        closet.updateQueue()
                                    }
                                }
                                player.equipment.helmet = null
                                player.sendMessage("§aVocê equipou o banner '${banner.display}'.")
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
                            player.sendMessage("§aVocê equipou o banner '${banner.display}'.")
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

                val porcentagemDesbloqueada = bannersDesbloqueados.toDouble() / BannerSystem.banners.size
                val porcentagemTexto = porcentagemDesbloqueada.percent() + "%"
                val corNumero = porcentagemDesbloqueada.percentColor()

                ItemBuilder(Material.PAPER)
                    .name("§aInformações")
                    .lore(
                        "§8Banners",
                        "",
                        "§7Você pode encontrar novos banners",
                        "§7em §bCaixas Misteriosas de Cosméticos§7.",
                        "",
                        "§fDesbloqueados: ${corNumero}${bannersDesbloqueados}/${BannerSystem.banners.size} §8(${porcentagemTexto})",
                        "§fSelecionado atualmente:",
                        "§a▸ $usedBannerName"
                    )
            }
            click = ClickEffect {
                it.player.soundWhenNoEffect()
            }
        }

        backPage.item = ItemBuilder(Material.ARROW)
            .name("§aVoltar")
        backPage.setPosition(5, 6)
        backPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

        nextPage.item = ItemBuilder(Material.ARROW)
            .name("§aPágina %page")
        nextPage.setPosition(9, 3)
        nextPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

        previousPage.item = ItemBuilder(Material.ARROW)
            .name("§aPágina %page")
        previousPage.setPosition(1, 3)
        previousPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

    }
}
