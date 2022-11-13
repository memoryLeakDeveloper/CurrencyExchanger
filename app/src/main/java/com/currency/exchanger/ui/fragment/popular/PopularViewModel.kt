package com.currency.exchanger.ui.fragment.popular

import androidx.lifecycle.*
import com.currency.exchanger.data.currency.CurrencyResponse
import com.currency.exchanger.data.favourite.FavouriteData
import com.currency.exchanger.domain.currency.GetAllCurrencyUseCase
import com.currency.exchanger.domain.favourite.GetFavouritesUseCase
import com.currency.exchanger.domain.favourite.SaveFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val getAllCurrencyUseCase: GetAllCurrencyUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val saveFavouriteUseCase: SaveFavouriteUseCase
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

    suspend fun saveFavourite(data: FavouriteData) = saveFavouriteUseCase.invoke(data)

    fun getAllFavourite() = getFavouritesUseCase.invoke().asLiveData()

}