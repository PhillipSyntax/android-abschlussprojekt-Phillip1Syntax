package de.phillipw.moebelhawi.kotlin.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.phillipw.moebelhawi.kotlin.BuildConfig
import de.phillipw.moebelhawi.kotlin.data.models.Product
import de.phillipw.moebelhawi.kotlin.data.models.ShoppingCartItem
import de.phillipw.moebelhawi.kotlin.local.MöbelHawiDatabase
import de.phillipw.moebelhawi.kotlin.remote.MöbelHawiApi

class Repository(private val api: MöbelHawiApi, private val database: MöbelHawiDatabase) {

    private val key = BuildConfig.apiKey

    private val _topSellers = MutableLiveData<List<Product>>()

    private val _topRated = MutableLiveData<List<Product>>()

    private val _searchResultProducts = MutableLiveData<List<Product>>()

    private val _categoriesResults = MutableLiveData<List<Product>>()

    val shoppingCartItems = database.itemCartDao.getCartItems()


    val categoryResults: LiveData<List<Product>>
        get() = _categoriesResults

    val searchResultProducts: LiveData<List<Product>>
        get() = _searchResultProducts

    val topSellerProducts: LiveData<List<Product>>
        get() = _topSellers

    val topRatedProducts: LiveData<List<Product>>
        get() = _topRated


    suspend fun getTopSellers() {
        try {
            val resultSellers = api.retrofitService.getProducts(
                "home_depot", "top_sellers", "kitchen", key
            ).products
            _topSellers.postValue(resultSellers)
        } catch (e: Exception) {
            Log.e("Repo", "$e")
        }
    }

    suspend fun getTopRated() {
        try {
            val resultRated = api.retrofitService.getProducts(
                "home_depot", "top_rated", "furniture", key
            ).products
            _topRated.postValue(resultRated)
        } catch (e: Exception) {
            Log.e("Repo", "$e")
        }
    }

    suspend fun getProductsBySearchQuery(query: String) {
        try {
            val resultSearch = api.retrofitService.getProducts(
                "home_depot", "best_match", query, key
            ).products
            _searchResultProducts.postValue(resultSearch)
        } catch (e: Exception) {
            Log.e("Repo", "$e")
        }
    }

    suspend fun getProductsByCategory(category: String) {
        try {
            val getResult = api.retrofitService.getProducts(
                "home_depot", "best_match", category, key
            ).products
            _categoriesResults.postValue(getResult)
        } catch (e: Exception) {
            Log.e("Repo", "$e")
        }
    }

    // Funktion zum Abrufen aller lokalen Notizen aus der Datenbank

    fun getShoppingCartItems() {
        try {
            database.itemCartDao.getCartItems()
        } catch (e: Exception) {
            Log.e("Repo", "$e")
        }
    }

    suspend fun insertShoppingCartItem(shoppingCartItem: ShoppingCartItem) {
        try {
            database.itemCartDao.insertCart(shoppingCartItem)
        } catch (e: Exception) {
            Log.e("Repo", "Failed to insert into databank $e")
        }
    }

    suspend fun deleteItem(id: Int) {
        try {
            database.itemCartDao.deleteById(id)
        } catch (e: Exception) {
            Log.e("Repo", "Failed to delete shoppingCartItem from databank $e")
        }
    }

    suspend fun updateItem(shoppingCartItem: ShoppingCartItem) {
        try {
            database.itemCartDao.updateCart(shoppingCartItem)
        } catch (e: Exception) {
            Log.e("Repo", "Failed to update into databank $e")
        }
    }

    suspend fun getShoppingCartItemByProductId(productId: String): ShoppingCartItem? {
        var shoppingCartItem: ShoppingCartItem? = null
        try {
           shoppingCartItem = database.itemCartDao.getCartItemByProductId(productId)
        } catch (e: Exception) {
            Log.e("Repo", "Failed to get shoppingCartItem by productId from databank $e")
        }
        return shoppingCartItem
    }

    suspend fun deleteAllCartItems(){
        try {
            database.itemCartDao.deleteAllCartItems()
        } catch (e:Exception) {
            Log.e("Repo", "Failed to delete CartItems from ShoppingCart")
        }
    }
}