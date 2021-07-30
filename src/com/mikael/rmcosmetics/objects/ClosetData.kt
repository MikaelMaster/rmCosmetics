package com.mikael.rmcosmetics.objects

import net.eduard.api.lib.database.annotations.*
import net.eduard.redemikael.core.api.MiftData
import net.eduard.redemikael.core.objects.MiftProfile
import org.bukkit.Material

@TableName("mift_cosmetics_closet")
class ClosetData : MiftData {

    @ColumnPrimary
    var id = 0L

    @ColumnUnique
    @ColumnRelation
    lateinit var player: MiftProfile

    @ColumnNullable
    var helmet: Material? = null

    @ColumnNullable
    var helmetName: String? = null

    @ColumnNullable
    var chestplate: Material? = null

    @ColumnNullable
    var chestplateName: String? = null

    @ColumnNullable
    var leggings: Material? = null

    @ColumnNullable
    var leggingsName: String? = null

    @ColumnNullable
    var boots: Material? = null

    @ColumnNullable
    var bootsName: String? = null

    var helmetBright: Boolean = false
    var chestplateBright: Boolean = false
    var leggingsBright: Boolean = false
    var bootsBright: Boolean = false
}