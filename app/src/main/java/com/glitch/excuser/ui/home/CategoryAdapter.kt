package com.glitch.excuser.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glitch.excuser.R
import com.glitch.excuser.databinding.ItemCategoryRvBinding

class CategoryAdapter(
	private val onCategoryClick: (Int) -> Unit
) : ListAdapter<String, CategoryAdapter.CategoryViewHolder>(CategoryDiffUtilCallBack()) {
	override fun onCreateViewHolder(
		parent: ViewGroup, viewType: Int
	): CategoryViewHolder {
		return CategoryViewHolder(
			ItemCategoryRvBinding.inflate(LayoutInflater.from(parent.context), parent, false),
			onCategoryClick
		)
	}

	override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
		holder.bind(getItem(position))
	}

	class CategoryViewHolder(
		private val binding: ItemCategoryRvBinding, private val onCategoryClick: (Int) -> Unit
	) : RecyclerView.ViewHolder(binding.root) {
		fun bind(category: String) {
			with(binding) {
				tvCategory.text = category
				ivCategory.setBackgroundResource(
					when (adapterPosition) {
						0 -> R.drawable.children
						1 -> R.drawable.college
						2 -> R.drawable.family
						3 -> R.drawable.funny
						4 -> R.drawable.gaming
						5 -> R.drawable.office
						6 -> R.drawable.party
						else -> R.drawable.unbelievable
					}
				)

				root.setOnClickListener {
					onCategoryClick(adapterPosition)
				}
			}
		}
	}

	class CategoryDiffUtilCallBack : DiffUtil.ItemCallback<String>() {
		override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
			return oldItem == newItem
		}

		override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
			return oldItem == newItem
		}
	}
}