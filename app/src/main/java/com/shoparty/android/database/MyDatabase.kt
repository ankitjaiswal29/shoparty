package com.shoparty.android.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.shoparty.android.common_modal.CartProduct
import com.shoparty.android.common_modal.Product
import com.shoparty.android.database.dao.ProductDao

/**
 * Created by Amit Gupta on 21-03-2022.
 */
@Database(entities = [Product::class, CartProduct::class], version = 1, exportSchema = false)
@androidx.room.TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao

    companion object {
        private var instance: MyDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, MyDatabase::class.java,
                    "shoparty_database"
                )
                    .fallbackToDestructiveMigration()
//                    .addTypeConverter(Converters::class.java)
//                    .addCallback(roomCallback)
                    .build()

            return instance!!
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                //populateDatabase(instance!!)
            }
        }

//        private fun populateDatabase(db: MyDatabase) {
//            val noteDao = db.getProductDao()
//            subscribeOnBackground {
//                noteDao.insert(Note("title 1", "desc 1", 1))
//                noteDao.insert(Note("title 2", "desc 2", 2))
//                noteDao.insert(Note("title 3", "desc 3", 3))
//
//            }
//        }
    }
}