package com.mikael.rmcosmetics.menu

import com.mikael.rmcosmetics.core.ParticleSystem
import com.mikael.rmcosmetics.objects.ParticleAnimation
import com.mikael.rmcosmetics.percentColor
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.game.SoundEffect
import net.eduard.api.lib.kotlin.format
import net.eduard.api.lib.kotlin.percent
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenEffect
import net.eduard.redemikael.core.soundWhenNoEffect
import net.eduard.redemikael.core.soundWhenSwitchMenu
import net.eduard.redemikael.core.user
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class MenuParticles : Menu("Partículas", 5) {

    companion object {
        var usingEffect = mutableMapOf<Player, ParticleAnimation>()
        lateinit var instance: MenuParticles
    }

    init {
        instance = this

        isAutoAlignItems = true
        autoAlignSkipLines = listOf(1, 4, 5)
        autoAlignSkipColumns = listOf(9, 1)
        autoAlignPerLine = 7
        autoAlignPerPage = 2 * autoAlignPerLine

        for (particleCosmetic in ParticleSystem.particles) {

            button {

                iconPerPlayer = {

                    val item = ItemBuilder(particleCosmetic.material)
                        .name("§a" + particleCosmetic.display)

                    val particle = particleCosmetic
                    val particleprecoemgold = ParticleSystem.precoemgold[particle]!!
                    val particleprecoemcash = ParticleSystem.precoemcash[particle]!!
                    val particlecompravel = ParticleSystem.compravel[particle]!!
                    val particleporgold = ParticleSystem.compravelporgold[particle]!!
                    val particleporcash = ParticleSystem.compravelporcash[particle]!!


                    if (hasPermission(particleCosmetic.permission)) {

                        if (ParticleSystem.hasSelected(player)) {
                            val particleUsed = ParticleSystem.getSelectedParticle(player)
                            if (particleUsed == particleCosmetic) {
                                val loreUsada = mutableListOf<String>()

                                loreUsada.add("§8Partícula")
                                loreUsada.add("")
                                loreUsada.add("§7Realce seu estilo em nossos lobbies")
                                loreUsada.add("§7utilizando a partícula ${particle.display}.")
                                loreUsada.add("")
                                loreUsada.add("§fFormato: §7Singular")
                                loreUsada.add("")
                                loreUsada.add("§eClique para remover!")
                                item.lore(loreUsada)
                            } else {
                                val loreUsada = mutableListOf<String>()

                                loreUsada.add("§8Partícula")
                                loreUsada.add("")
                                loreUsada.add("§7Realce seu estilo em nossos lobbies")
                                loreUsada.add("§7utilizando a partícula ${particle.display}.")
                                loreUsada.add("")
                                loreUsada.add("§fFormato: §7Singular")
                                loreUsada.add("")
                                loreUsada.add("§eClique para utilizar!")
                                item.lore(loreUsada)
                            }
                        } else {
                            val loreUsada = mutableListOf<String>()

                            loreUsada.add("§8Partícula")
                            loreUsada.add("")
                            loreUsada.add("§7Realce seu estilo em nossos lobbies")
                            loreUsada.add("§7utilizando a partícula ${particle.display}.")
                            loreUsada.add("")
                            loreUsada.add("§fFormato: §7Singular")
                            loreUsada.add("")
                            loreUsada.add("§eClique para utilizar!")
                            item.lore(loreUsada)
                        }
                    } else {
                        val loreUsada = mutableListOf<String>()

                        loreUsada.add("§8Partícula")
                        loreUsada.add("")
                        loreUsada.add("§7Realce seu estilo em nossos lobbies")
                        loreUsada.add("§7utilizando a partícula ${particle.display}.")
                        loreUsada.add("")
                        loreUsada.add("§fFormato: §7Singular")
                        loreUsada.add("")
                        if (particlecompravel) {
                            loreUsada.add("§eOpções de compra:")
                        }
                        if (particleporgold) {
                            loreUsada.add(" §8▪ §fComprar com Gold: §6${particleprecoemgold.format(false)} Gold")
                        }
                        if (particleporcash) {
                            loreUsada.add(" §8▪ §fComprar com Cash: §b${particleprecoemcash.format(false)} Cash")
                        }
                        if (particlecompravel) {
                            loreUsada.add("")
                        }
                        if (particlecompravel) {
                            loreUsada.add(
                                if (user.gold >= particleprecoemgold)
                                    "§aClique para comprar!" else
                                    "§cVocê não possui saldo suficiente."
                            )
                            item.lore(loreUsada)
                        } else {
                            loreUsada.add("§cIndisponível para compra.")
                            item.lore(loreUsada)
                        }
                        item.lore(loreUsada)
                    }

                    item
                }
                click = ClickEffect {
                    val player = it.player
                    val user = player.user

                    val particle = particleCosmetic
                    val particleprecoemgold = ParticleSystem.precoemgold[particle]!!
                    val particleprecoemcash = ParticleSystem.precoemcash[particle]!!
                    val particlecompravel = ParticleSystem.compravel[particle]!!
                    val particleporgold = ParticleSystem.compravelporgold[particle]!!
                    val particleporcash = ParticleSystem.compravelporcash[particle]!!

                    if (player.hasPermission(particleCosmetic.permission)) {
                        if (ParticleSystem.hasSelected(player)) {
                            val particleUsed = ParticleSystem.getSelectedParticle(player)
                            var particle = usingEffect[player]
                            if (particle == null) {
                                player.sendMessage("§cOcorreu um erro ao alterar sua partícula!")
                                player.sendMessage("§cPor favor, troque de lobby para resolver esse problema.")
                                return@ClickEffect
                            }

                            if (particleUsed == particleCosmetic) {
                                player.soundWhenEffect()
                                player.sendMessage("§cVocê removeu a partícula ${particleCosmetic.display}.")
                                particle.stop()
                                usingEffect.remove(player)
                                ParticleSystem.deselect(player)
                                open(player)
                            } else {
                                particle = particleCosmetic.animationClass.constructors
                                    .first().call(player)
                                player.soundWhenEffect()
                                player.sendMessage("§aVocê selecionou a partícula ${particleCosmetic.display}.")
                                particle.start()
                                usingEffect[player] = particle
                                ParticleSystem.select(player, particleCosmetic)
                                open(player)
                            }
                        } else {
                            val animation = particleCosmetic.animationClass.constructors
                                .first().call(player)
                            player.soundWhenEffect()
                            player.sendMessage("§aVocê selecionou a partícula ${particleCosmetic.display}.")
                            animation.restart()
                            usingEffect[player] = animation
                            ParticleSystem.select(player, particleCosmetic)
                            open(player)
                        }
                    } else {
                        if (ParticleSystem.compravel[particleCosmetic] == true) {
                            if (user.gold >= particleprecoemgold || user.cash >= particleprecoemcash) {
                                player.soundWhenSwitchMenu()
                                MenuSelectCoinTypeParticle.instance
                                    .comprando[player] = particle

                                MenuSelectCoinTypeParticle.instance.open(player)
                            } else {
                                player.soundWhenNoEffect()
                            }
                        } else {
                            player.soundWhenNoEffect()
                        }
                    }
                }
            }
            button("remove-particles") {
                setPosition(4, 5)

                fixed = true
                iconPerPlayer = {
                    ItemBuilder(Material.BARRIER)
                        .name("§cRemover Partícula")
                        .lore("§7Remove sua partícula atual.")
                }
                click = ClickEffect {
                    val player = it.player
                    val particle = usingEffect[player]

                    player.soundWhenEffect()
                    if (particle != null) {
                        particle.stop()
                        usingEffect.remove(player)
                        ParticleSystem.deselect(player)
                        player.sendMessage("§cSua partícula atual foi removida.")
                        open(player)
                    } else {
                        player.sendMessage("§cVocê não possui uma partícula selecionada.")
                    }
                }
            }
            button("info") {
                setPosition(6, 5)

                fixed = true
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

                    ItemBuilder(Material.PAPER)
                        .name("§aInformações")
                        .lore(
                            "§8Partículas",
                            "",
                            "§7Você pode encontrar novas partículas",
                            "§7em §bCaixas Misteriosas §7ou comprá-las",
                            "§7utilizando §6Gold §7e §bCash§7.",
                            "",
                            "§fDesbloqueados: ${corNumero}${particlesDesbloqueados}/${ParticleSystem.particles.size} §8(${porcentagemTexto})",
                            "§fSelecionada atualmente:",
                            "§a▸ ${usedParticleName}"
                        )
                }
                click = ClickEffect {
                    it.player.soundWhenNoEffect()
                }
            }

            backPage.item = ItemBuilder(Material.INK_SACK).data(1)
                .name("§cVoltar")
                .lore("§7Para Cosméticos.")
            backPage.setPosition(5, 5)
            backPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)

        }
    }
}
