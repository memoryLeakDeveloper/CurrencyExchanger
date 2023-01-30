package com.currency.exchanger.ui.fragment.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.currency.exchanger.data.currency.CurrencyData
import com.currency.exchanger.data.currency.CurrencyResponse
import com.currency.exchanger.data.currency.usecases.GetAllCurrencyUseCaseImpl
import com.currency.exchanger.data.favourite.FavouriteData
import com.currency.exchanger.data.favourite.usecases.GetFavouritesUseCaseImpl
import com.currency.exchanger.data.favourite.usecases.SaveFavouriteUseCaseImpl
import com.currency.exchanger.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val getAllCurrencyUseCase: GetAllCurrencyUseCaseImpl,
    private val getFavouritesUseCase: GetFavouritesUseCaseImpl,
    private val saveFavouriteUseCase: SaveFavouriteUseCaseImpl,
    val dataStoreManager: DataStoreManager
) : ViewModel() {

    var lastSortPref = 3
    var mapPopular = mutableMapOf<String, Double>()
    var listFavourites = mutableListOf<String>()

    private val _popularLiveData = MutableLiveData<CurrencyResponse>()
    val popularLiveData = _popularLiveData

    suspend fun getPopularCurrency(query: String?): LiveData<CurrencyResponse> {
        getAllCurrencyUseCase.invoke(query).collect {
            _popularLiveData.postValue(it)
        }
        return getAllCurrencyUseCase.invoke(query).asLiveData()
    }

    fun getDataStoreLiveData() = dataStoreManager.getFromDataStore().asLiveData()

    suspend fun saveFavourite(data: FavouriteData) = saveFavouriteUseCase.invoke(data)

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

}