package com.ilhomsoliev.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ilhomsoliev.data.local.model.DishEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DishDao {
    @Query("SELECT * FROM dish_entity")
    fun getDishes(): Flow<List<DishEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: DishEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<DishEntity>)

    @Update
    suspend fun update(data: DishEntity)

    @Delete
    suspend fun delete(data: DishEntity)
}