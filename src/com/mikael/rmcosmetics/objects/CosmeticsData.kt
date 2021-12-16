package com.mikael.rmcosmetics.objects

import net.eduard.api.lib.database.annotations.*
import net.eduard.redemikael.core.api.MiftData
import net.eduard.redemikael.core.objects.MiftProfile

@TableName("mift_cosmetics")
class CosmeticsData : MiftData {

    @ColumnPrimary
    var id = 0L

    @ColumnUnique
    @ColumnRelation
    lateinit var player: MiftProfile

    @ColumnNullable
    var companion: String? = null

    @ColumnSize(50)
    @ColumnNullable
    var companionName: String? = null

    @ColumnNullable
    var hat: String? = null

    @ColumnNullable
    var animatedHat: String? = null

    @ColumnNullable
    var pet: String? = null

    @ColumnSize(50)
    @ColumnNullable
    var petName: String? = null

    @ColumnNullable
    var banner: String? = null

    @ColumnNullable
    var particle: String? = null

    @ColumnNullable
    var gadget: String? = null

}