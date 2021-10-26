package com.mikael.rmcosmetics.objects

import net.eduard.api.lib.database.annotations.*
import net.eduard.redemikael.core.api.MiftData
import net.eduard.redemikael.core.objects.MiftProfile

@TableName("mift_cosmetics_banners")
class BannerData : MiftData {

    @ColumnPrimary
    var id = 0L

    @ColumnUnique
    @ColumnRelation
    lateinit var player: MiftProfile

    @ColumnNullable
    var banner: String? = null
}