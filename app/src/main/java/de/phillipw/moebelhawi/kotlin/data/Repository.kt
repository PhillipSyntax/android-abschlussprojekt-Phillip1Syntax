package de.phillipw.moebelhawi.kotlin.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.phillipw.moebelhawi.kotlin.BuildConfig
import de.phillipw.moebelhawi.kotlin.data.models.Product
import de.phillipw.moebelhawi.kotlin.remote.MöbelHawiApi

class Repository(private val api: MöbelHawiApi) {

    private val key = BuildConfig.apiKey

    private val _topSellers = MutableLiveData<List<Product>>()

    private val _topRated = MutableLiveData<List<Product>>()

    private val _results = MutableLiveData<List<Product>>()

    val results: LiveData<List<Product>>
        get() = _results

    val topSellers: LiveData<List<Product>>
        get() = _topSellers

    val topRated: LiveData<List<Product>>
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

    suspend fun getResultSearch(query: String) {
        try {
            val resultSearch = api.retrofitService.searchProducts(
                "home_depot", "best_match", query, key
            ).products
            _results.postValue(resultSearch)
        } catch (e:Exception) {
            Log.e("Repo", "$e")
        }
    }


}