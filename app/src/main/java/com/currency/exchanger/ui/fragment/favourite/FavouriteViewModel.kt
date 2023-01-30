package com.currency.exchanger.ui.fragment.favourite

import androidx.lifecycle.*
import com.currency.exchanger.data.currency.CurrencyData
import com.currency.exchanger.data.currency.CurrencyResponse
import com.currency.exchanger.data.currency.usecases.GetAllCurrencyUseCaseImpl
import com.currency.exchanger.data.favourite.FavouriteData
import com.currency.exchanger.data.favourite.usecases.DeleteFavouriteUseCaseImpl
import com.currency.exchanger.data.favourite.usecases.GetFavouritesUseCaseImpl
import com.currency.exchanger.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getAllCurrencyUseCase: GetAllCurrencyUseCaseImpl,
    private val deleteFavouriteUseCase: DeleteFavouriteUseCaseImpl,
    private val getFavouritesUseCase: GetFavouritesUseCaseImpl,
    val dataStoreManager: DataStoreManager
) : ViewModel() {

    var mapPopular = mutableMapOf<String, Double>()
    var listFavourites = mutableListOf<String>()
    var lastSortPref = 3

    private val _popularLiveData = MutableLiveData<CurrencyResponse>()
    val popularLiveData = _popularLiveData

    suspend fun getPopularCurrency(query: String?): LiveData<CurrencyResponse> {
        viewModelScope.launch {
            getAllCurrencyUseCase.invoke(query).collect {
                _popularLiveData.postValue(it)
            }
        }
        return getAllCurrencyUseCase.invoke(query).asLiveData()
    }

    suspend fun deleteFavourite(data: FavouriteData) = deleteFavouriteUseCase.invoke(data)

    fun getAllFavourite() = getFavouritesUseCase.invoke().asLiveData()

    fun sortListByPref(list: List<CurrencyData>): List<CurrencyData> {
        val result = mutableListOf<CurrencyData>()
        when (lastSortPref) {
            1 -> result.addAll(list.sortedBy { it.rate })
            2 -> result.addAll(list.sortedBy { it.rate }.reversed())
            3 -> result.addAll(list.sortedBy { it.name })
            4 -> result.addAll(list.sortedBy { it.name }.reversed())
        }
        return result
    }

    fun sortListContainsInDb() = mapPopular
        .filter { listFavourites.contains(it.key) }
        .map { CurrencyData(it.key, it.value, listFavourites.contains(it.key)) }

}