package com.shoparty.android.database.dao

import androidx.room.*
import com.shoparty.android.common_modal.Product
import com.shoparty.android.ui.search.SearchProductHistory


/**
 * Created by Amit Gupta on 22-03-2022.
 */
@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(items: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProduct(order: List<Product>)

    @Query("SELECT * FROM product")
    suspend fun getAllProduct(): List<Product>

    @Delete
    suspend fun deleteProduct(model: Product)

    @Query("DELETE FROM product")
    suspend fun deleteAllProduct()

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertSearchProduct(items: SearchProductHistory)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAllSearchProductHistory(order: List<SearchProductHistory>)
//
//    @Query("SELECT * FROM searchproducthistory")
//    fun getAllSearchProductHistory(): List<SearchProductHistory>
//
//    @Delete
//    fun deleteSearchProductHistory(model: SearchProductHistory)
//
//    @Query("DELETE FROM searchproducthistory")
//    fun deleteAllSearchProductHistory()
}