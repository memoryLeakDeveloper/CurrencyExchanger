package com.currency.exchanger.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DataStoreManager(val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SORTING_KEY)

    init {
        CoroutineScope(Dispatchers.Main).launch {
            setDefault()
        }
    }

    private suspend fun setDefault() {
        context.dataStore.edit {
            it[NAME] = 3
        }
    }

    suspend fun saveToDataStore(pref: Int) {
        context.dataStore.edit {
            it[NAME] = pref
        }
    }

    fun getFromDataStore() = context.dataStore.data.map {
        it
    }

    companion object {
        const val SORTING_KEY = "sorting_type"
        val NAME = intPreferencesKey("Pref")
    }

}