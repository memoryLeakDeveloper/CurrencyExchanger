package com.currency.exchanger.ui.fragment.favourite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.currency.exchanger.R
import com.currency.exchanger.data.currency.CurrencyData
import com.currency.exchanger.databinding.ItemPopularBinding
import com.currency.exchanger.utils.setDrawable

class FavouriteAdapter(private var list: List<CurrencyData>, private val callback: FavouriteFragment.DeleteFavouriteCallback) :
    RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {

    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            if (position < lastPosition) R.xml.down_from_top else R.xml.up_from_bottom
        )
        lastPosition = holder.bindingAdapterPosition
        holder.itemView.startAnimation(animation)
        val item = list[position]
        with(holder.binding) {
            name.text = item.name
            value.text = item.rate.toString()
            button.setDrawable(R.drawable.ic_favourite)
            button.setOnClickListener {
                callback.delete(item.name)
            }
        }
    }

    fun repopulateData(list: List<CurrencyData>) {
        val diffResult = DiffUtil.calculateDiff(FavouriteDiffCallback(this.list, list))
        diffResult.dispatchUpdatesTo(this)
        this.list = list
    }

    override fun getItemCount() = list.size

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
    }

    class ViewHolder(val binding: ItemPopularBinding) : RecyclerView.ViewHolder(binding.root)

}
