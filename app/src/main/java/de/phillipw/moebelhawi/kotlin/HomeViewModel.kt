package de.phillipw.moebelhawi.kotlin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.phillipw.moebelhawi.kotlin.data.Repository
import de.phillipw.moebelhawi.kotlin.data.models.Product
import de.phillipw.moebelhawi.kotlin.data.models.ShoppingCartItem
import de.phillipw.moebelhawi.kotlin.local.MöbelHawiDatabase
import de.phillipw.moebelhawi.kotlin.remote.MöbelHawiApi
import kotlinx.coroutines.launch
import org.jetbrains.annotations.ApiStatus

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val database = MöbelHawiDatabase.getDatabase(application)

    private val repository: Repository = Repository(MöbelHawiApi, database)

    private val _loading = MutableLiveData<ApiStatus>()

    private val _totalPrice = MutableLiveData<Double>()

    val totalPrice: LiveData<Double>
        get() = _totalPrice

    val loading: LiveData<ApiStatus>
        get() = _loading

    val topSellers = repository.topSellerProducts

    val topRated = repository.topRatedProducts

    val results = repository.searchResultProducts

    val categoryResults = repository.categoryResults

    val cartItems = repository.shoppingCartItems


    private val _resultInput = MutableLiveData<String>()

    val resultInput: LiveData<String>
        get() = _resultInput


    private val _selectedProduct = MutableLiveData<Product>()


    val selectedProduct: LiveData<Product>
        get() = _selectedProduct



    fun loadTopSellers() {
        viewModelScope.launch {
            repository.getTopSellers()
        }
    }

    fun loadTopRated() {
        viewModelScope.launch {
            repository.getTopRated()
        }
    }

    fun setSelectedProduct(product: Product) {
        _selectedProduct.value = product
    }

    fun loadResultInput(query: String) {
        viewModelScope.launch {
            repository.getProductsBySearchQuery(query)
        }
    }

    fun updateInputText(query: String) {
        _resultInput.value = query
    }

    fun loadByCategory(query: String) {
        viewModelScope.launch {
            repository.getProductsByCategory(query)
        }
    }

    fun getCartItems() {
        viewModelScope.launch {
            repository.getShoppingCartItems()
        }
    }

    fun addToShoppingCart(product: Product) {

        val shoppingCartItem = ShoppingCartItem(0,product.productId,1, product.price)
        viewModelScope.launch {
            repository.insertShoppingCartItem(shoppingCartItem)

        }
    }
    fun deleteItemFromShoppingCart(shoppingCartItem: ShoppingCartItem) {
        viewModelScope.launch {
            repository.deleteItem(shoppingCartItem.id)
            updateCartTotal()
        }
    }
        fun updateCartItem(item: ShoppingCartItem) {
            viewModelScope.launch {
                repository.updateItem(item)

            }
        }
    fun updateCartTotal() {
        viewModelScope.launch {
            repository.getShoppingCartItems()
        }
    }
    fun calculateTotalPrice() {
        viewModelScope.launch {
            val items = repository.getShoppingCartItems()
            //Hier hänge ich fest

        }

    }
}
