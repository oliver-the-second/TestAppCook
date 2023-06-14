package com.ilhomsoliev.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ilhomsoliev.data.local.dao.DishDao
import com.ilhomsoliev.data.local.model.DishEntity

@Database(
    entities = [DishEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun dishDao(): DishDao
}