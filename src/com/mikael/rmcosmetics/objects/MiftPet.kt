package com.mikael.rmcosmetics.objects

import org.bukkit.entity.EntityType

class MiftPet(
    var name: String = "pet",
    var display: String = "Pet",
    var rarity: String = "comum",
    var url: String = "http://textures.minecraft.net/texture/96c0b36d53fff69a49c7d6f3932f2b0fe948e032226d5e8045ec58408a36e951",
    var type: EntityType = EntityType.ENDERMAN,
    var gender: Boolean = false,
    var groupName: String = "vip",
    var group: String = "rmcore.benefits.${groupName}",
    var permission: String = "rmcosmetics.pet.${name}",
)