package com.example.inventory.data

import android.content.Context

interface AppContainer {
    val flowerRepository: FlowerRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val flowerRepository: FlowerRepository by lazy {
        OfflineFlowerRepository(FloristryDatabase.getDatabase(context).itemDao())
    }
}
