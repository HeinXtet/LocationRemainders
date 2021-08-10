package com.udacity.project4.domain.model

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
    val latitude: Double,
    val longitude: Double,
    val place: String,
    val placeId: String
)