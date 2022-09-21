package com.rasyidin.hi_fi.presentation.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rasyidin.hi_fi.databinding.ItemCategoryBinding
import com.rasyidin.hi_fi.domain.model.category.Category

class CategoryAdapter(private val showWithName: Boolean = true) : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(diffCallback) {

    var onItemClickCallback: ((Category) -> Unit)? = null

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            val context = binding.root.context
            binding.apply {
                imgCategory.setImageDrawable(ContextCompat.getDrawable(context, item.imageCategory))
                cardCategory.setCardBackgroundColor(ContextCompat.getColor(context, item.bgColor))
                tvName.text = if (item.name != null) context.getString(item.name!!) else item.nameString
                tvName.isVisible = showWithName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onItemClickCallback?.invoke(item)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }
        }
    }
}