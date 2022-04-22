package com.shoparty.android.database.dao

import androidx.room.*
import com.shoparty.android.common_modal.CartProduct
import com.shoparty.android.common_modal.Product
import com.shoparty.android.ui.productdetails.AddItemToBagRequestModel

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

    //cart product
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCartProduct(items: CartProduct)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCartProduct(order: List<CartProduct>)

    @Query("SELECT * FROM cart_product")
    fun getAllCartProduct(): List<CartProduct>

    @Query("SELECT * FROM cart_product WHERE id=:id ")
    fun getCartProduct(id: String): CartProduct?

    @Delete
    fun deleteCartProduct(model: CartProduct)

    @Update
    fun updateCartProduct(model: CartProduct)

    @Query("DELETE FROM cart_product")
    fun deleteAllCartProduct()
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertBagProduct(items: AddItemToBagRequestModel)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAllBagProduct(order: List<AddItemToBagRequestModel>)
//
//    @Query("SELECT * FROM bag")
//    fun getAllBagProduct(): List<AddItemToBagRequestModel>
//
//    @Query("SELECT * FROM bag WHERE product_id=:product_id ")
//    fun getBag(product_id: String): AddItemToBagRequestModel
//
//    @Delete
//    fun deleteBagProduct(model: AddItemToBagRequestModel)
//
//    @Update
//    fun updateBagProduct(model: AddItemToBagRequestModel)
//
//    @Query("DELETE FROM bag")
//    fun deleteAllBagProduct()
}