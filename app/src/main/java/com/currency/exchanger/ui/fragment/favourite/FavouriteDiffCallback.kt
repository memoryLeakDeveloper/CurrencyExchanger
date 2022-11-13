package com.currency.exchanger.ui.fragment.favourite

import androidx.recyclerview.widget.DiffUtil
import com.currency.exchanger.data.currency.CurrencyData

class FavouriteDiffCallback(
    private val oldList: List<CurrencyData>,
    private val newList: List<CurrencyData>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].rate == newList[newItemPosition].rate &&
                oldList[oldItemPosition].isFavourite == newList[newItemPosition].isFavourite
    }

}