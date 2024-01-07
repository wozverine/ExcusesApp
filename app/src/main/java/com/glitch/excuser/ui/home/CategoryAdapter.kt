package com.glitch.excuser.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glitch.excuser.databinding.ItemCategoryRvBinding

class CategoryAdapter(
	private val onCategoryClick: (Int) -> Unit
) : ListAdapter<String, CategoryAdapter.CategoryViewHolder>(CategoryDiffUtilCallBack()) {
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): CategoryAdapter.CategoryViewHolder {
		TODO("Not yet implemented")
	}

	override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
		TODO("Not yet implemented")
	}

	class CategoryViewHolder(
		private val binding: ItemCategoryRvBinding,
		val onCategoryClick: (Int) -> Unit
	) : RecyclerView.ViewHolder(binding.root) {
		fun bind(category: String) {
			with(binding) {

			}
		}
	}

	class CategoryDiffUtilCallBack: DiffUtil.ItemCallback<String>() {
		override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
			return oldItem == newItem
		}

		override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
			return oldItem == newItem
		}
	}
}