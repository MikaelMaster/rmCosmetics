package com.mikael.rmcosmetics.objects

import net.eduard.api.lib.database.annotations.*
import net.eduard.redemikael.core.api.MiftData
import net.eduard.redemikael.core.objects.MiftProfile

@TableName("mift_cosmetics_hats")
class HatData : MiftData {

    @ColumnPrimary
    var id = 0L

    @ColumnUnique
    @ColumnRelation
    lateinit var player: MiftProfile

    @ColumnNullable
    var hat: String? = null
}