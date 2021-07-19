package com.deevvdd.locationremainder.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by heinhtet deevvdd@gmail.com on 18,July,2021
 */

@Entity(tableName = "remainder")
data class Remainder(
    @PrimaryKey @ColumnInfo(name = "remainderId")
    var id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val latitude: Long,
    val longitude: Long,
    val place: String
)