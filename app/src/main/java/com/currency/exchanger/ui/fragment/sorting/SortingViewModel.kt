package com.currency.exchanger.ui.fragment.sorting

import androidx.lifecycle.ViewModel
import com.currency.exchanger.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SortingViewModel @Inject constructor(val dataStoreManager: DataStoreManager) : ViewModel()