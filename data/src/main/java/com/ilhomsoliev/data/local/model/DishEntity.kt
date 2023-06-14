package com.ilhomsoliev.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dish_entity")
data class DishEntity (
    @ColumnInfo(name = "description") val description:String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int,
    @ColumnInfo(name = "image_url") val image_url:String,
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "price") val price:Int,
    @ColumnInfo(name = "weight") val weight:Int,
)
