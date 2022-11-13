package com.currency.exchanger.utils

import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun AppCompatImageView.setDrawable(@DrawableRes id: Int) = setImageDrawable(context.resources.getDrawable(id, null))