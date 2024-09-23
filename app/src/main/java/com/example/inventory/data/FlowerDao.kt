
package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FlowerDao {

    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Flower>>

    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Flower>

    @Query("SELECT * from items WHERE name LIKE :query")
    fun searchItemsByName(query: String): Flow<List<Flower>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(flower: Flower)

    @Update
    suspend fun update(flower: Flower)

    @Delete
    suspend fun delete(flower: Flower)
}
