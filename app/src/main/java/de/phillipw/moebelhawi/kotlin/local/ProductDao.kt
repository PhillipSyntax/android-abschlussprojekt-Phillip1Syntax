package de.phillipw.moebelhawi.kotlin.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import de.phillipw.moebelhawi.kotlin.data.models.Product
import de.phillipw.moebelhawi.kotlin.data.models.ShoppingCartItem


/** Für Produkte*/
@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getAllProducts(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: Product)

    @Query("SELECT * FROM products")
    fun getProducts(): LiveData<List<Product>>

    @Update
    suspend fun updateProduct(product: Product)


    @Query("DELETE FROM products WHERE product_id = :productId")
    suspend fun deleteById(productId: String)


}


