package com.currency.exchanger.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToastLong(text: String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun Context.showToastLong(@StringRes id: Int) = Toast.makeText(this, resources.getString(id), Toast.LENGTH_LONG).show()