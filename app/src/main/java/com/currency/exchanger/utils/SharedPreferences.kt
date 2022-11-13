package com.currency.exchanger.utils

import android.content.Context
import androidx.core.content.edit

object SharedPreferences {

    private const val sharedPreferencesName = "com.currency.exchanger"
    private const val SORTING_KEY = "sorting_type"

    fun getSharedPreference(context: Context) = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)!!

    var Context.sorting: Int
        get() = getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).getInt(SORTING_KEY, 3)
        set(type) = getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).edit { putInt(SORTING_KEY, type) }
}