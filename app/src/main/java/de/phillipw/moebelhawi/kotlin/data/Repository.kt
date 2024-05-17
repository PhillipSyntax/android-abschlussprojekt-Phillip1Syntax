package de.phillipw.moebelhawi.kotlin.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.phillipw.moebelhawi.kotlin.BuildConfig
import de.phillipw.moebelhawi.kotlin.data.models.Product
import de.phillipw.moebelhawi.kotlin.remote.MöbelHawiApi

class Repository(private val api: MöbelHawiApi) {

    private val key = BuildConfig.apiKey

    private val _products = MutableLiveData<List<Product>>()

    val products: LiveData<List<Product>>
        get() = _products

    suspend fun getProducts(hdSort: String, q: String) {
        try {
            val result = api.retrofitService.getProducts(
                "home_depot", hdSort,q,key).products
            _products.postValue(result)
        } catch (e:Exception) {
            Log.e("Repo", "$e")
        }
    }
}