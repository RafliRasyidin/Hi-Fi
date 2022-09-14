package com.rasyidin.hi_fi.presentation.home.saldo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rasyidin.hi_fi.databinding.ItemSourceBalanceBinding
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance
import com.rasyidin.hi_fi.utils.formatRupiah

class SourcesBalanceAdapter :
    ListAdapter<SourceBalance, SourcesBalanceAdapter.ItemViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SourceBalance>() {
            override fun areItemsTheSame(oldItem: SourceBalance, newItem: SourceBalance): Boolean {
                return oldItem.sourceId == newItem.sourceId
            }

            override fun areContentsTheSame(
                oldItem: SourceBalance,
                newItem: SourceBalance
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    var onItemClick: ((SourceBalance) -> Unit)? = null

    inner class ItemViewHolder(private val binding: ItemSourceBalanceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: SourceBalance) {
            val context = binding.root.context
            binding.apply {
                with(item) {
                    imgTransaction.setImageDrawable(ContextCompat.getDrawable(context, iconPath!!))
                    tvName.text = name
                    tvBalance.text = "Rp ${formatRupiah(balance ?: 0L)}"
                    bgIcon.setCardBackgroundColor(ContextCompat.getColor(context, bgColor!!))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ItemSourceBalanceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }
}