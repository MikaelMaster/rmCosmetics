package com.mikael.rmcosmetics.gadgets

import com.mikael.rmcosmetics.MiftCosmetics
import com.mikael.rmcosmetics.core.GadgetSystem
import com.mikael.rmcosmetics.objects.Gadget
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.manager.CooldownManager
import net.eduard.api.lib.modules.Extra
import net.eduard.api.lib.modules.Mine
import net.eduard.redemikael.core.spigot.CoreMain
import net.eduard.redemikael.parkour.ParkourMain
import net.eduard.redemikael.parkour.core.ParkourManager
import net.eduard.redemikael.parkour.isPlaying
import net.eduard.redemikael.parkour.listener.ParkourController
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBurnEvent
import org.bukkit.event.block.BlockIgniteEvent
import org.bukkit.event.entity.EntityCombustEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable

class PainterGadget : Gadget(
    "Pintor",
    "divino",
    listOf(
        "§7Pinte com os pés e vire um Leonardo",
        "§7da Vinci em nossos lobbies!"
    ), ItemBuilder(Material.INK_SACK).data(9), 35, "rmcosmetics.gadget.painter"
) {

    val cooldown = CooldownManager(20 * 35)

    init {
        cooldown.msgCooldown = "§cVocê precisa esperar mais %times para utilizar esta engenhoca novamente!"
    }

    @EventHandler
    fun clicando(event: PlayerInteractEvent) {
        val player = event.player
        if (event.item == null) return
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        if (icon != event.item) return
        if (CoreMain.instance.getBoolean("is-minigame-lobby")) {
            if (player.isPlaying) {
                player.sendMessage("§cVocê não pode ativar uma engenhoca enquanto percorre o parkour.")
                return
            }
        }

        if (GadgetSystem.hasActiveGadget(player)) {
            player.sendMessage("§cVocê já possui uma engenhoca ativa no momento!")
            return
        }

        if (cooldown.cooldown(player)) {
            player.sendMessage("§aVocê ativou a engenhoca Pintor! Duração: §f15s")
            GadgetSystem.putActiveGadget(player)

            var time = 15
            object : BukkitRunnable() {
                override fun run() {
                    time -= 1
                    if (time == 0) {
                        cancel()
                    }
                }
            }.runTaskTimer(MiftCosmetics.instance, 20, 20)

            object : BukkitRunnable() {
                override fun run() {
                    val listaDeBlocosParaResetar = mutableListOf<Location>()
                    if (time != 0) {
                        val locToChange = player.location.clone().add(0.0, -1.0, 0.0)
                        if (locToChange.block.type == Material.STAINED_CLAY) return
                        if (locToChange.block.type != Material.AIR &&
                            locToChange.block.type != Material.SKULL &&
                            locToChange.block.type != Material.STEP &&
                            locToChange.block.type != Material.WATER &&
                            locToChange.block.type != Material.LAVA &&
                            locToChange.block.type != Material.SLIME_BLOCK &&
                            locToChange.block.type != Material.BARRIER &&
                            locToChange.block.type != Material.WOOD_PLATE &&
                            locToChange.block.type != Material.IRON_PLATE &&
                            locToChange.block.type != Material.GOLD_PLATE &&
                            locToChange.block.type != Material.STONE_PLATE &&
                            locToChange.block.type != Material.TORCH &&
                            locToChange.block.type != Material.REDSTONE_TORCH_OFF &&
                            locToChange.block.type != Material.REDSTONE_TORCH_ON &&
                            locToChange.block.type != Material.STONE_BUTTON &&
                            locToChange.block.type != Material.WOOD_BUTTON &&
                            locToChange.block.type != Material.LEVER &&
                            locToChange.block.type != Material.LADDER &&
                            locToChange.block.type != Material.LEAVES &&
                            locToChange.block.type != Material.LEAVES_2 &&
                            locToChange.block.type != Material.FENCE &&
                            locToChange.block.type != Material.FENCE_GATE &&
                            locToChange.block.type != Material.RED_ROSE &&
                            locToChange.block.type != Material.DOUBLE_PLANT &&
                            locToChange.block.type != Material.SIGN &&
                            locToChange.block.type != Material.BANNER &&
                            locToChange.block.type != Material.ITEM_FRAME &&
                            locToChange.block.type != Material.PAINTING &&
                            locToChange.block.type != Material.GLASS &&
                            locToChange.block.type != Material.CARPET &&
                            locToChange.block.type != Material.TRAP_DOOR &&
                            locToChange.block.type != Material.IRON_TRAPDOOR &&
                            locToChange.clone().add(0.0, 1.0, 0.0).block.type == Material.AIR
                        ) {
                            val lastBlock = locToChange.block.type
                            val lastBlockData = locToChange.block.data
                            listaDeBlocosParaResetar.add(locToChange)
                            locToChange.block.type = Material.STAINED_CLAY
                            locToChange.block.data = Extra.getRandomInt(1, 14).toByte()
                            locToChange.world.playSound(locToChange, Sound.ITEM_PICKUP, 2f, 1f)

                            object : BukkitRunnable() {
                                override fun run() {
                                    for (blockToReset in listaDeBlocosParaResetar) {
                                        if (blockToReset.block.type == Material.STAINED_CLAY) {
                                            blockToReset.block.type = lastBlock
                                            blockToReset.block.data = lastBlockData
                                        }
                                    }
                                }
                            }.runTaskLater(MiftCosmetics.instance, 20 * 2)
                        }
                    }

                    if (time == 0 && listaDeBlocosParaResetar.isEmpty()) {
                        player.sendMessage("§cA tinta de sua engenhoca pintor acabou.")
                        listaDeBlocosParaResetar.clear()
                        GadgetSystem.removeActiveGadget(player)
                        cancel()
                    }
                }
            }.runTaskTimer(MiftCosmetics.instance, 1, 1)
        }
    }

    init {

        icon = ItemBuilder(Material.INK_SACK).data(9).name("§aEngenhoca: §ePintor")
            .lore(
                "§7Pinte com os pés e vire um Leonardo",
                "§7da Vinci em nossos lobbies!"
            )
    }
}