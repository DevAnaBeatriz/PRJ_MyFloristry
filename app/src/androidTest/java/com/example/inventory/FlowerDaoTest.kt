
package com.example.inventory

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.inventory.data.FloristryDatabase
import com.example.inventory.data.Flower
import com.example.inventory.data.FlowerDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FlowerDaoTest {

    private lateinit var flowerDao: FlowerDao
    private lateinit var floristryDatabase: FloristryDatabase
    private val flower1 = Flower(1, "Apples", 10.0, 20, "")
    private val flower2 = Flower(2, "Bananas", 15.0, 97, "")

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Exemplo: Adicionando uma nova coluna
            database.execSQL("ALTER TABLE item ADD COLUMN photoUri TEXT")
        }
    }

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        floristryDatabase = Room.inMemoryDatabaseBuilder(context, FloristryDatabase::class.java)
            .addMigrations(MIGRATION_1_2) // Adicione a migração aqui
            .allowMainThreadQueries()
            .build()
        flowerDao = floristryDatabase.itemDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        floristryDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsItemIntoDB() = runBlocking {
        addOneItemToDb()
        val allItems = flowerDao.getAllItems().first()
        assertEquals(allItems[0], flower1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        val allItems = flowerDao.getAllItems().first()
        assertEquals(allItems[0], flower1)
        assertEquals(allItems[1], flower2)
    }


    @Test
    @Throws(Exception::class)
    fun daoGetItem_returnsItemFromDB() = runBlocking {
        addOneItemToDb()
        val item = flowerDao.getItem(1)
        assertEquals(item.first(), flower1)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteItems_deletesAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        flowerDao.delete(flower1)
        flowerDao.delete(flower2)
        val allItems = flowerDao.getAllItems().first()
        assertTrue(allItems.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateItems_updatesItemsInDB() = runBlocking {
        addTwoItemsToDb()
        flowerDao.update(Flower(1, "Apples", 15.0, 25, ""))
        flowerDao.update(Flower(2, "Bananas", 5.0, 50, ""))

        val allItems = flowerDao.getAllItems().first()
        assertEquals(allItems[0], Flower(1, "Apples", 15.0, 25, ""))
        assertEquals(allItems[1], Flower(2, "Bananas", 5.0, 50, ""))
    }

    private suspend fun addOneItemToDb() {
        flowerDao.insert(flower1)
    }

    private suspend fun addTwoItemsToDb() {
        flowerDao.insert(flower1)
        flowerDao.insert(flower2)
    }
}
