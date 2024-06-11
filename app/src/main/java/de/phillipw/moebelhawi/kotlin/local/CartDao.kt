package de.phillipw.moebelhawi.kotlin.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import de.phillipw.moebelhawi.kotlin.data.models.ShoppingCartItem

/** FÜr den Warenkorb*/
@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(shoppingCartItem: ShoppingCartItem)

    @Update
    suspend fun updateCart(shoppingCartItem: ShoppingCartItem)

    @Query("SELECT * FROM shopping_card_item")
    fun getCartItems(): LiveData<List<ShoppingCartItem>>

    @Query("DELETE FROM shopping_card_item WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM shopping_card_item")
    fun clearShoppingCart()

}