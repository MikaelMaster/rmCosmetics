package com.mikael.rmcosmetics.objects


import net.eduard.api.lib.database.annotations.*
import net.eduard.redemikael.core.api.MiftData
import net.eduard.redemikael.core.objects.MiftProfile

@TableName("mift_cosmetics_companions")
class CompanionData : MiftData {

    class CompanionNames {
        var customNames = mutableMapOf<String, String>()
    }

    @ColumnPrimary
    var id = 0L

    @ColumnUnique
    @ColumnRelation
    lateinit var player: MiftProfile

    @ColumnNullable
    var companion: String? = null

    @ColumnSize(1000)
    @ColumnJson
    var customNames = CompanionNames()
}