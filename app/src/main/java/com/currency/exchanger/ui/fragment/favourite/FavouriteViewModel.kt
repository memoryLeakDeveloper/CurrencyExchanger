package com.currency.exchanger.ui.fragment.favourite

import androidx.lifecycle.*
import com.currency.exchanger.data.currency.CurrencyResponse
import com.currency.exchanger.data.favourite.FavouriteData
import com.currency.exchanger.domain.currency.GetAllCurrencyUseCase
import com.currency.exchanger.domain.favourite.DeleteFavouriteUseCase
import com.currency.exchanger.domain.favourite.GetFavouritesUseCase
import com.currency.exchanger.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getAllCurrencyUseCase: GetAllCurrencyUseCase,
    private val deleteFavouriteUseCase: DeleteFavouriteUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase,
    val dataStoreManager: DataStoreManager
) : ViewModel() {

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

}