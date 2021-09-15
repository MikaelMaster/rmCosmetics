package com.mikael.rmcosmetics.menu

import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.game.SoundEffect
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import net.eduard.redemikael.core.soundWhenEffect
import net.eduard.redemikael.core.soundWhenNoEffect
import org.bukkit.Material
import org.bukkit.Sound

class MenuPets : Menu("Pets", 6) {

    init {
        openWithCommand = "/pets"
        isAutoAlignItems = true

        cooldownBetweenInteractions = 0
        autoAlignSkipLines = listOf(1, 5, 6)
        autoAlignSkipColumns = listOf(9, 1)
        autoAlignPerLine = 7
        autoAlignPerPage = 3 * autoAlignPerLine

        button("soon") {
            setPosition(5, 3)

            fixed = true
            iconPerPlayer = {
                ItemBuilder(Material.WEB)
                    .name("§cEm Breve!")
                    .lore(
                        "§7O sistema de Pets está em desenvolvimento",
                        "§7e estará disponível em futuras versões do",
                        "§7sistema de cosméticos da Rede Mift."
                    )
            }
            click = ClickEffect {
                it.player.soundWhenNoEffect()
            }
        }

        button("remove-pet") {
            setPosition(4, 6)

            fixed = true
            iconPerPlayer = {
                ItemBuilder(Material.BARRIER)
                    .name("§cRemover Pet")
                    .lore("§7Remove seu pet atual.")
            }
            click = ClickEffect {
                val player = it.player

                player.soundWhenEffect()
                player.sendMessage("§cSeu pet atual foi removido.")
                open(player)
            }
        }
        button("info") {
            setPosition(6, 6)

            fixed = true
            iconPerPlayer = {
                ItemBuilder(Material.PAPER)
                    .name("§aInformações")
                    .lore(
                        "§8Pets",
                        "",
                        "§7Você pode encontrar novos pets em",
                        "§bCaixas Misteriosas de Cosméticos §7ou",
                        "§7comprá-los utilizando §6Gold §7e §bCash§7.",
                        "",
                        "§fDesbloqueados: §c0/0 §8(0%)",
                        "§fSelecionado atualmente:",
                        "§a▸ Nenhum"
                    )
            }
            click = ClickEffect {
                it.player.soundWhenNoEffect()
            }
        }
        button("rename-pet") {
            setPosition(9, 1)

            fixed = true
            iconPerPlayer = {
                ItemBuilder(Material.NAME_TAG)
                    .name("§aRenomear Pet")
                    .lore(
                        "§fNome atual: §cNenhum",
                        "",
                        "§cVocê não possui um pet."
                    )
            }
            click = ClickEffect {
                val player = it.player

                player.soundWhenNoEffect()
            }
        }

        backPage.item = ItemBuilder(Material.ARROW)
            .name("§aVoltar")
        backPage.setPosition(5, 6)
        backPageSound = SoundEffect(Sound.NOTE_STICKS, 2f, 1f)
    }
}