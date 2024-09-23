
package com.example.inventory.data

import kotlinx.coroutines.flow.Flow

class OfflineFlowerRepository(private val flowerDao: FlowerDao) : FlowerRepository {
    override fun getAllItemsStream(): Flow<List<Flower>> = flowerDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Flower?> = flowerDao.getItem(id)

    override fun searchItemsByName(query: String): Flow<List<Flower>> = flowerDao.searchItemsByName(query)

    override suspend fun insertItem(flower: Flower) = flowerDao.insert(flower)

    override suspend fun deleteItem(flower: Flower) = flowerDao.delete(flower)

    override suspend fun updateItem(flower: Flower) = flowerDao.update(flower)
}