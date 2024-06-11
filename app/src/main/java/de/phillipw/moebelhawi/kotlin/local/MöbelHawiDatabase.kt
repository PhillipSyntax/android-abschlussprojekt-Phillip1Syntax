package de.phillipw.moebelhawi.kotlin.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.phillipw.moebelhawi.kotlin.data.models.Product
import de.phillipw.moebelhawi.kotlin.data.models.ShoppingCartItem


@Database(entities = [Product::class,ShoppingCartItem::class], version = 1)
abstract class MöbelHawiDatabase : RoomDatabase(){

    abstract val itemCartDao: CartDao
    abstract val productDao: ProductDao

    companion object {

        private lateinit var dbInstance: MöbelHawiDatabase

        fun getDatabase(context: Context): MöbelHawiDatabase {
            if (!this::dbInstance.isInitialized) {
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    MöbelHawiDatabase::class.java,
                    "moebelhawi_database"
                ).build()
            }
            return dbInstance
        }
    }
}