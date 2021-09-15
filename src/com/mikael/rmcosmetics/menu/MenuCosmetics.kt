package com.mikael.rmcosmetics.menu

import com.kirelcodes.miniaturepets.loader.PetLoader
import com.kirelcodes.miniaturepets.pets.PetManager
import com.mikael.rmcosmetics.core.*
import com.mikael.rmcosmetics.percentColor
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.kotlin.percent
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenNoEffect
import net.eduard.redemikael.core.soundWhenSwitchMenu
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.block.banner.PatternType
import org.bukkit.inventory.ItemFlag

class MenuCosmetics : Menu("Cosméticos", 5) {

    companion object {
        lateinit var instance: MenuCosmetics
    }

    init {
        instance = this

        cooldownBetweenInteractions = 0
        openWithCommand = "/cosmeticos"
        openWithCommandText = "/cosmetics"

        button("companions") {
            setPosition(3, 2)

            iconPerPlayer = {
                val player = this
                val companionsDesbloqueados = PetLoader.getPets().count {
                    hasPermission(it.permission)
                }

                var porcentagemDesbloqueada = companionsDesbloqueados.toDouble() / PetLoader.getPets().size
                var porcentagemTexto = porcentagemDesbloqueada.percent() + "%"
                val corNumero = porcentagemDesbloqueada.percentColor()

                val item =
                    ItemBuilder(Material.SKULL_ITEM).skin("http://textures.minecraft.net/texture/47b4f84e19b52f31217712e7ba9f51d56da59d2445b4d7f39ef6c323b8166")
                        .name("§aCompanheiros")

                if (PetManager.hasPet(player)) {
                    item.lore(
                        "§7Passeie em nossos lobbies",
                        "§7com estilo acompanhado de",
                        "§7um companheiro exclusivo.",
                        "",
                        "§fDesbloqueados: ${corNumero}${companionsDesbloqueados}/${PetLoader.getPets().size} §8(${porcentagemTexto})",
                        "§fSelecionado atualmente:",
                        "§a▸ ${PetManager.getPet(player).container.name}",
                        "",
                        "§eClique para abrir!"
                    )
                } else {
                    item.lore(
                        "§7Passeie em nossos lobbies",
                        "§7com estilo acompanhado de",
                        "§7um companheiro exclusivo.",
                        "",
                        "§fDesbloqueados: ${corNumero}${companionsDesbloqueados}/${PetLoader.getPets().size} §8(${porcentagemTexto})",
                        "§fSelecionado atualmente:",
                        "§a▸ Nenhum",
                        "",
                        "§eClique para abrir!"
                    )
                }
            }
            menu = MenuCompanions()
            click = ClickEffect {
                it.player.soundWhenSwitchMenu()
            }
        }
        button("hats") {
            setPosition(5, 2)

            iconPerPlayer = {
                val player = this
                var usedHatName = "Nenhum"

                if (HatSystem.hasSelected(player)) {

                    val usedHat = HatSystem.getSelectedHat(player)
                    val hatName = usedHat.display
                    usedHatName = hatName
                }

                val hatsDesbloqueados = HatSystem.hats.count {
                    hasPermission(it.permission)
                }

                var porcentagemDesbloqueada = hatsDesbloqueados.toDouble() / HatSystem.hats.size
                var porcentagemTexto = porcentagemDesbloqueada.percent() + "%"
                val corNumero = porcentagemDesbloqueada.percentColor()

                ItemBuilder(Material.SKULL_ITEM)
                    .skin("http://textures.minecraft.net/texture/d43d4b7ac24a1d650ddf73bd140f49fc12d2736fc14a8dc25c0f3f29d85f8f")
                    .name("§aChapéus")
                    .lore(
                        "§7Passeie em nossos lobbies",
                        "§7com estilo utilizando um",
                        "§7chapéu exclusivo.",
                        "",
                        "§fDesbloqueados: ${corNumero}${hatsDesbloqueados}/${HatSystem.hats.size} §8(${porcentagemTexto})",
                        "§fSelecionado atualmente:",
                        "§a▸ $usedHatName",
                        "",
                        "§eClique para abrir!"
                    )
            }
            menu = MenuHats()
            click = ClickEffect {
                it.player.soundWhenSwitchMenu()
            }
        }
        button("animated-hats") {
            setPosition(7, 2)

            iconPerPlayer = {
                val player = this
                var usedHatName = "Nenhum"

                if (HatAnimatedSystem.hasSelected(player)) {

                    val usedHat = HatAnimatedSystem.getSelectedAnimatedHat(player)
                    val hatName = usedHat.display
                    usedHatName = hatName
                }

                val animatedHatsDesbloqueados = HatAnimatedSystem.animatedHats.count {
                    hasPermission(it.permission)
                }

                var porcentagemDesbloqueada = animatedHatsDesbloqueados.toDouble() / HatAnimatedSystem.animatedHats.size
                var porcentagemTexto = porcentagemDesbloqueada.percent() + "%"
                val corNumero = porcentagemDesbloqueada.percentColor()


                ItemBuilder(Material.SKULL_ITEM).skin("http://textures.minecraft.net/texture/732fe121a63eaabd99ced6d1acc91798652d1ee8084d2f9127d8a315cad5ce4")
                    .name("§aChapéus animados")
                    .lore(
                        "§7Passeie em nossos lobbies com",
                        "§7um chapéu animado que irá",
                        "§7exibir suas reações.",
                        "",
                        "§fDesbloqueados: ${corNumero}${animatedHatsDesbloqueados}/${HatAnimatedSystem.animatedHats.size} §8(${porcentagemTexto})",
                        "§fSelecionado atualmente:",
                        "§a▸ $usedHatName",
                        "",
                        "§eClique para abrir!"
                    )
            }
            menu = MenuAnimatedHats()
            click = ClickEffect {
                it.player.soundWhenSwitchMenu()
            }
        }
        button("closet") {
            setPosition(2, 4)

            iconPerPlayer = {
                val closet = ClosetSystem.getPlayerCloset(player)

                var helmet = "§a▸ Vazio"
                if (closet.helmet != null) {
                    helmet = "§a▸ ${closet.helmetName} §c(Brilho inativo)"
                }
                if (closet.helmetBright) {
                    helmet = "§a▸ ${closet.helmetName} §e(Brilho ativo)"
                }
                var chestplate = "§a▸ Vazio"
                if (closet.chestplate != null) {
                    chestplate = "§a▸ ${closet.chestplateName} §c(Brilho inativo)"
                }
                if (closet.chestplateBright) {
                    chestplate = "§a▸ ${closet.chestplateName} §e(Brilho ativo)"
                }
                var leggings = "§a▸ Vazio"
                if (closet.leggings != null) {
                    leggings = "§a▸ ${closet.leggingsName} §c(Brilho inativo)"
                }
                if (closet.leggingsBright) {
                    leggings = "§a▸ ${closet.leggingsName} §e(Brilho ativo)"
                }
                var boots = "§a▸ Vazio"
                if (closet.boots != null) {
                    boots = "§a▸ ${closet.bootsName} §c(Brilho inativo)"
                }
                if (closet.bootsBright) {
                    boots = "§a▸ ${closet.bootsName} §e(Brilho ativo)"
                }

                val item = ItemBuilder(Material.ARMOR_STAND).name("§aGuarda-Roupa")

                if (player.hasPermission("rmcosmetics.use.closet")) {
                    item.lore(
                        "§7Ande sempre com estilo em",
                        "§7nossos lobbies com armaduras",
                        "§7que você poderá personalizar!",
                        "",
                        "§fCombinação de roupas atual:",
                        helmet,
                        chestplate,
                        leggings,
                        boots,
                        "",
                        "§eClique para abrir!"
                    )
                } else {
                    item.lore(
                        "§7Ande sempre com estilo em",
                        "§7nossos lobbies com armaduras",
                        "§7que você poderá personalizar!",
                        "",
                        "§fAdquira seu VIP acessando nossa loja:",
                        "§awww.redemift.com/loja",
                        "",
                        "§fExclusivo para §aVIP §fou superior.",
                    )
                }
            }
            click = ClickEffect {
                val player = it.player

                if (player.hasPermission("rmcosmetics.use.closet")) {
                    player.soundWhenSwitchMenu()
                    MenuCloset.instance.open(player)
                } else {
                    player.soundWhenNoEffect()
                }
            }
        }
        button("pets") {
            setPosition(3, 4)

            iconPerPlayer = {
                ItemBuilder(Material.BONE)
                    .name("§aPets")
                    .lore(
                        "§7Passeie em nossos lobbies",
                        "§7com estilo utilizando um",
                        "§7pet exclusivo.",
                        "",
                        "§fDesbloqueados: §c0/0 §8(0%)",
                        "§fSelecionado atualmente:",
                        "§a▸ Nenhum",
                        "",
                        "§eClique para abrir!"
                    )
            }
            menu = MenuPets()
            click = ClickEffect {
                it.player.soundWhenSwitchMenu()
            }
        }

        button("banners") {
            setPosition(5, 4)

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


                ItemBuilder(Material.BANNER)
                    .name("§aBanners")
                    .lore(
                        "§7Escolha banners com diversos",
                        "§7desenhos, formatos e cores",
                        "§7diferentes para utilizar",
                        "§7em nossos lobbies.",
                        "",
                        "§fDesbloqueados: ${corNumero}${bannersDesbloqueados}/${BannerSystem.banners.size} §8(${porcentagemTexto})",
                        "§fSelecionado atualmente:",
                        "§a▸ $usedBannerName",
                        "",
                        "§eClique para abrir!"
                    )
                    .banner(DyeColor.BLUE, DyeColor.WHITE, PatternType.BRICKS)
                    .addBanner(DyeColor.BLUE, PatternType.STRIPE_BOTTOM)
                    .addBanner(DyeColor.BLACK, PatternType.STRAIGHT_CROSS)
                    .addBanner(DyeColor.BLUE, PatternType.STRAIGHT_CROSS)
                    .addBanner(DyeColor.BLACK, PatternType.BORDER)
                    .addBanner(DyeColor.BLUE, PatternType.BORDER)
                    .addFlags(*ItemFlag.values())
            }
            menu = MenuBanners()
            click = ClickEffect {
                it.player.soundWhenSwitchMenu()
            }
        }
        button("particles") {
            setPosition(7, 4)

            iconPerPlayer = {
                val player = this
                var usedParticleName = "Nenhuma"

                if (ParticleSystem.hasSelected(player)) {

                    val usedParticle = ParticleSystem.getSelectedParticle(player)
                    val particleName = usedParticle.display
                    usedParticleName = particleName
                }

                val particlesDesbloqueados = ParticleSystem.particles.count {
                    hasPermission(it.permission)
                }

                var porcentagemDesbloqueada = particlesDesbloqueados.toDouble() / ParticleSystem.particles.size
                var porcentagemTexto = porcentagemDesbloqueada.percent() + "%"
                val corNumero = porcentagemDesbloqueada.percentColor()


                ItemBuilder(Material.BLAZE_POWDER)
                    .name("§aPartículas")
                    .lore(
                        "§7Escolha partículas de diversas",
                        "§7cores e formatos para andar com",
                        "§7estilo em nossos lobbies.",
                        "",
                        "§fDesbloqueadas: ${corNumero}${particlesDesbloqueados}/${ParticleSystem.particles.size} §8(${porcentagemTexto})",
                        "§fSelecionada atualmente:",
                        "§a▸ $usedParticleName",
                        "",
                        "§eClique para abrir!",
                    )
            }
            menu = MenuParticles()
            click = ClickEffect {
                it.player.soundWhenSwitchMenu()
            }
        }
        button("gadgets") {
            setPosition(8, 4)

            iconPerPlayer = {
                val player = this
                var usedGadgetName = "Nenhuma"

                if (GadgetSystem.hasSelected(player)) {

                    val usedGadget = GadgetSystem.getSelectedGadget(player)
                    val gadgetName = usedGadget.name
                    usedGadgetName = gadgetName
                }

                val gadgetsDesbloqueados = GadgetSystem.gadgets.count {
                    hasPermission(it.permission)
                }

                var porcentagemDesbloqueada = gadgetsDesbloqueados.toDouble() / GadgetSystem.gadgets.size
                var porcentagemTexto = porcentagemDesbloqueada.percent() + "%"
                val corNumero = porcentagemDesbloqueada.percentColor()

                ItemBuilder(Material.DISPENSER)
                    .name("§aEngenhocas")
                    .lore(
                        "§7Escolha entre diversos brinquedos",
                        "§7para se divertir nos lobbies.",
                        "",
                        "§fDesbloqueadas: ${corNumero}${gadgetsDesbloqueados}/${GadgetSystem.gadgets.size} §8(${porcentagemTexto})",
                        "§fSelecionada atualmente:",
                        "§a▸ $usedGadgetName",
                        "",
                        "§eClique para abrir!"
                    )
            }
            menu = MenuGadgets()
            click = ClickEffect {
                it.player.soundWhenSwitchMenu()
            }
        }
    }
}