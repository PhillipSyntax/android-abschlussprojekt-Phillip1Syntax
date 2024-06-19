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

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val database = MöbelHawiDatabase.getDatabase(application)

    private val repository: Repository = Repository(MöbelHawiApi, database)

    private val _subTotalPrice = MutableLiveData<Double>()

    val subTotalPrice: LiveData<Double>
        get() = _subTotalPrice

    private val _deliveryPrice = MutableLiveData<Double>()

    private val _totalPriceWithDelivery = MutableLiveData<Double>()

    val deliveryCost: LiveData<Double>
        get() = _deliveryPrice

    val totalPriceWithDelivery: LiveData<Double>
        get() = _totalPriceWithDelivery

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

        val shoppingCartItem = product.thumbnails.firstOrNull()?.lastOrNull()
            ?.let { ShoppingCartItem(0, product.productId, 1, product.price, product.title, it) }

        viewModelScope.launch {
            if (shoppingCartItem != null) {
                // Hier erfolgt die Prüfung, ob es bereits im Warenkorb ist
                val availableInCart = repository.getShoppingCartItemByProductId(product.productId)
                if (availableInCart != null) {
                    // Wenn ja, wird es kopiert und die Anzahl erhöht
                    val updatedItem = availableInCart.copy(quantity = availableInCart.quantity + 1)
                    repository.updateItem(updatedItem)
                } else {
                    // Wenn nicht, wird das Produkt neu hinzugefügt
                    repository.insertShoppingCartItem(shoppingCartItem)
                }
                calculateSubTotalPrice()
            }
        }
    }

    fun deleteItemFromShoppingCart(shoppingCartItem: ShoppingCartItem) {
        viewModelScope.launch {
            val alreadyInCart =
                repository.getShoppingCartItemByProductId(shoppingCartItem.productId)
            if (alreadyInCart != null) {
                if (alreadyInCart.quantity > 1) {
                    val reduceItem = alreadyInCart.copy(quantity = alreadyInCart.quantity - 1)
                    repository.updateItem(reduceItem)
                } else {
                    repository.deleteItem(shoppingCartItem.id)
                }
            }
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

    fun calculateSubTotalPrice() {

        val cartItems = cartItems.value ?: emptyList()
        var subTotalPrice = 0.0
        for (item in cartItems) {
            subTotalPrice += item.price * item.quantity
        }
        _subTotalPrice.postValue(subTotalPrice)
    }

    fun calculateDeliveryPrice(items: List<ShoppingCartItem>): Double {
        var deliveryCost = 0.0
        items.forEach { item ->
            deliveryCost += when {
                item.title.contains("Sofa", ignoreCase = true) -> 30.0
                item.title.contains("Refrigerator", ignoreCase = true) -> 30.0
                item.title.contains("furniture", ignoreCase = true) -> 30.0
                item.title.contains("bed", ignoreCase = true) -> 30.0
                item.title.contains("living room", ignoreCase = true) -> 30.0
                else -> 5.0
            }
        }
        return deliveryCost
    }

    fun calculateTotalPrice() {
        viewModelScope.launch {
            val items = cartItems.value ?: emptyList()
            val total = items.sumOf { it.price * it.quantity }
            _subTotalPrice.value = total

            val deliveryCost = calculateDeliveryPrice(items)
            _deliveryPrice.value = deliveryCost
            _totalPriceWithDelivery.value = total + deliveryCost
        }
    }

    fun deleteAllCartItems() {
        viewModelScope.launch {
            repository.deleteAllCartItems()
        }

    }
}