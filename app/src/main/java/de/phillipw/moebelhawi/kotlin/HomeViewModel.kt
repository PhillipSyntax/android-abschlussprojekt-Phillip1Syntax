package de.phillipw.moebelhawi.kotlin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.phillipw.moebelhawi.kotlin.data.Repository
import de.phillipw.moebelhawi.kotlin.data.models.Product
import de.phillipw.moebelhawi.kotlin.remote.MöbelHawiApi
import kotlinx.coroutines.launch
import org.jetbrains.annotations.ApiStatus

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository(MöbelHawiApi)

    private val _loading = MutableLiveData<ApiStatus>()

    val loading: LiveData<ApiStatus>
        get() = _loading

    val topSellers = repository.topSellers

    val topRated = repository.topRated

    val results = repository.results



    private val _resultInput = MutableLiveData<String>()

    val resultInput: LiveData<String>
        get() = _resultInput


    private val _selectedProduct = MutableLiveData<Product>()


    val selectedProduct : LiveData<Product>
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
            repository.getResultSearch(query)
        }
    }
    fun updateInputText(query: String) {
        _resultInput.value = query
    }
}