
package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Flower::class], version = 2, exportSchema = false)
abstract class FloristryDatabase : RoomDatabase() {

    abstract fun itemDao(): FlowerDao

    companion object {
        @Volatile
        private var Instance: FloristryDatabase? = null

        fun getDatabase(context: Context): FloristryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FloristryDatabase::class.java, "item_database")

                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }


}
