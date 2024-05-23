package de.phillipw.moebelhawi.kotlin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.phillipw.moebelhawi.kotlin.data.Repository
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
}