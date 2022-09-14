package com.rasyidin.hi_fi.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rasyidin.hi_fi.R
import com.rasyidin.hi_fi.databinding.ItemTransactionBinding
import com.rasyidin.hi_fi.domain.model.balance.TransactionCategorize
import com.rasyidin.hi_fi.domain.model.transaction.SourceBalanceAndTransaction
import com.rasyidin.hi_fi.utils.NORMAL_DATE_FORMAT
import com.rasyidin.hi_fi.utils.formatRupiah
import com.rasyidin.hi_fi.utils.isToday
import com.rasyidin.hi_fi.utils.toDateFormat

class HistoryTransactionAdapter :
    ListAdapter<SourceBalanceAndTransaction, HistoryTransactionAdapter.ItemViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<SourceBalanceAndTransaction>() {
            override fun areItemsTheSame(
                oldItem: SourceBalanceAndTransaction,
                newItem: SourceBalanceAndTransaction
            ): Boolean {
                return oldItem.transaction.id == newItem.transaction.id
            }

            override fun areContentsTheSame(
                oldItem: SourceBalanceAndTransaction,
                newItem: SourceBalanceAndTransaction
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ItemViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SourceBalanceAndTransaction) {
            binding.apply {
                val context = root.context
                with(item) {
                    val transaction = transaction
                    val sourceBalance = sourceBalance
                    imgTransaction.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            sourceBalance.iconPath ?: R.drawable.ic_tagihan
                        )
                    )
                    bgIcon.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            sourceBalance.bgColor ?: R.color.bg_purple
                        )
                    )
                    val nominal = "Rp.${formatRupiah(transaction.nominal ?: 0)}"
                    tvPrice.text = nominal
                    tvDesc.text =
                        if (transaction.description.isNullOrEmpty()) "-" else transaction.description
                    val isOutcome = transaction.idTypeTransaction == TransactionCategorize.OUTCOME
                    if (isOutcome) {
                        tvPrice.setTextColor(ContextCompat.getColor(context, R.color.color_red))
                        tvTypeTransaction.text = sourceBalance.name
                    } else {
                        tvPrice.setTextColor(ContextCompat.getColor(context, R.color.color_green))
                        tvTypeTransaction.text = sourceBalance.name
                    }
                    if (isToday(transaction.date ?: "")) {
                        tvDate.text = context.getString(R.string.today)
                    } else {
                        tvDate.text = transaction.date?.toDateFormat(NORMAL_DATE_FORMAT)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}