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

    private val _results = MutableLiveData<List<Product>>()

    private val _categorieResults = MutableLiveData<List<Product>>()


    private val _items = database.itemCartDao.getCartItems()

    private val _products = database.productDao.getProducts()



    val categorieResults: LiveData<List<Product>>
        get() = _categorieResults

    val results: LiveData<List<Product>>
        get() = _results

    val topSellers: LiveData<List<Product>>
        get() = _topSellers

    val topRated: LiveData<List<Product>>
        get() = _topRated

    val items: LiveData<List<ShoppingCartItem>>
        get() = _items

    val products: LiveData<List<Product>>
        get() = _products


    fun getAllProducts(): LiveData<List<Product>> {
        return database.productDao.getProducts()
    }

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

    suspend fun getResultSearch(query: String) {
        try {
            val resultSearch = api.retrofitService.getProducts(
                "home_depot", "best_match", query, key
            ).products
            _results.postValue(resultSearch)
        } catch (e: Exception) {
            Log.e("Repo", "$e")
        }
    }

    suspend fun getResultByCategorie(query: String) {
        try {
            val getResult = api.retrofitService.getProducts(
                "home_depot", "best_match", query, key
            ).products
            _categorieResults.postValue(getResult)
        } catch (e: Exception) {
            Log.e("Repo", "$e")
        }
    }

    // Funktion zum Abrufen aller lokalen Notizen aus der Datenbank

    fun getShoppingCartItems() {
        try {
            val item =
                items.value!! // Hinweis: Hier wird auf das LiveData-Objekt direkt zugegriffen.
            database.itemCartDao.getCartItems()
        } catch (e: Exception) {
            Log.e("Repo", "$e")
        }
    }

    suspend fun insertItems(shoppingCartItem: ShoppingCartItem) {
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

    suspend fun fetchAndStoreProducts(query: String) {
        try {
            val products =
                api.retrofitService.getProducts("home_depot", "best_match", query, key)
                    .products.forEach { product ->
                        database.productDao.insertProduct(product)
                    }
        } catch (e: Exception) {
            Log.e("Repo", "Failed to fetch products from API: $e")

        }
    }

    suspend fun addProductToShoppingCartItems(product: ShoppingCartItem) {
        try {
            database.itemCartDao.insertCart(product)
        } catch (e: Exception) {
            Log.e("Repo", "Failed to add products to ShoppingCart: $e")
        }
    }
}