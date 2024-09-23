
package com.example.inventory.data

import kotlinx.coroutines.flow.Flow

interface FlowerRepository {

    fun getAllItemsStream(): Flow<List<Flower>>

    fun getItemStream(id: Int): Flow<Flower?>

    fun searchItemsByName(query: String): Flow<List<Flower>>

    suspend fun insertItem(flower: Flower)

    suspend fun deleteItem(flower: Flower)

    suspend fun updateItem(flower: Flower)
}


