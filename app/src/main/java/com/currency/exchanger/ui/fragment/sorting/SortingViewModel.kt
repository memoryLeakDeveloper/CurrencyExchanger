package com.currency.exchanger.ui.fragment.sorting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.currency.exchanger.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SortingViewModel @Inject constructor(val dataStoreManager: DataStoreManager) : ViewModel() {

    fun dataStoreManagerLiveData() = dataStoreManager.getFromDataStore().asLiveData()

    fun updateSortPref(pref: Int) = viewModelScope.launch { dataStoreManager.saveToDataStore(pref) }
}

