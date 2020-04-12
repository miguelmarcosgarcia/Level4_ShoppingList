package com.androidcourse.shoppinglistkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.androidcourse.shoppinglistkotlin.model.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ShoppingListRoomDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        private const val DATABASE_NAME = "SHOPPING_LIST_DATABASE"

        //The DB is also a Singleton to prevent there being multiple instances of the DB
        // because creating such an instance is heavy work.
        @Volatile
        private var shoppingListRoomDatabaseInstance: ShoppingListRoomDatabase? = null

        fun getDatabase(context: Context): ShoppingListRoomDatabase? {
            if (shoppingListRoomDatabaseInstance == null) {
                synchronized(ShoppingListRoomDatabase::class.java) {
                    if (shoppingListRoomDatabaseInstance == null) {
                        shoppingListRoomDatabaseInstance =
                            Room.databaseBuilder(context.applicationContext,
                                ShoppingListRoomDatabase::class.java,
                                DATABASE_NAME
                            ).build()
                        //The actual DB is constructed using the databaseBuilder from Room
                    }
                }
            }
            return shoppingListRoomDatabaseInstance
        }
    }

}