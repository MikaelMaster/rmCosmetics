package com.mikael.rmcosmetics.menu

import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.game.SoundEffect
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenSwitchMenu
import org.bukkit.Material
import org.bukkit.Sound

class MenuAnimations : Menu("Animações", 4) {

    init {

        button("kills") {
            setPosition(2, 2)

            iconPerPlayer = {
                ItemBuilder(Material.DIAMOND_SWORD)
                    .name("§aAnimações de Abate")
                    .lore(
                        "§7Elimine jogadores com estilo",
                        "§7reproduzindo efeitos especiais.",
                        "",
                        "§fDesbloqueados: §70/0 §8(0%)",
                        "",
                        "§eClique para abrir!"
                    )
            }
            menu = null
            click = ClickEffect {
                it.player.soundWhenSwitchMenu()
            }
        }
        button("projectiles") {
            setPosition(3, 2)

            iconPerPlayer = {
                ItemBuilder(Material.ARROW)
                    .name("§aAnimações de Projéteis")
                    .lore(
                        "§7Ao atirar qualquer projétil, o mesmo",
                        "§7será seguido por uma estila animação.",
                        "",
                        "§fDesbloqueados: §70/0 §8(0%)",
                        "",
                        "§eClique para abrir!"
                    )
            }
            menu = null
            click = ClickEffect {
                it.player.soundWhenSwitchMenu()
            }
        }
        button("falls") {
            setPosition(4, 2)

            iconPerPlayer = {
                ItemBuilder(Material.IRON_BOOTS)
                    .name("§aAnimações de Quedas")
                    .lore(
                        "§7Quem disse que cair é perder estilo?",
                        "§7Com estas animações, cair vai virar",
                        "§7algo muito mais estilo!",
                        "",
                        "§fDesbloqueados: §70/0 §8(0%)",
                        "",
                        "§eClique para abrir!"
                    )
            }
            menu = null
            click = ClickEffect {
                it.player.soundWhenSwitchMenu()
            }
        }
        button("teleports") {
            setPosition(5, 2)

            iconPerPlayer = {
                ItemBuilder(Material.ENDER_PEARL)
                    .name("§aAnimações de Teleportes")
                    .lore(
                        "§7Ser igual não é muito legal...",
                        "§7Se diferencie dos outros jogadores",
                        "§7com animações de teleporte!",
                        "",
                        "§fDesbloqueados: §70/0 §8(0%)",
                        "",
                        "§eClique para abrir!"
                    )
            }
            menu = null
            click = ClickEffect {
                it.player.soundWhenSwitchMenu()
            }
        }

        backPage.item = ItemBuilder(Material.INK_SACK).data(1)
            .name("§cVoltar")
            .lore("§7Para Loja - Sky Wars.")
        backPage.setPosition(5, 4)
        backPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)


    }
}