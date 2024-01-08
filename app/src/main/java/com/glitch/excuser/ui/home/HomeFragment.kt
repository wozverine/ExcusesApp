package com.glitch.excuser.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.glitch.excuser.R
import com.glitch.excuser.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!

	private val categoryAdapter = CategoryAdapter(
		onCategoryClick = ::onCategoryClick
	)

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View {
		_binding = FragmentHomeBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val categoryList: MutableList<String> =
			mutableListOf(
				getString(R.string.children),
				getString(R.string.college),
				getString(R.string.family),
				getString(R.string.funny),
				getString(R.string.gaming),
				getString(R.string.office),
				getString(R.string.party),
				getString(R.string.unbelievable)
			)
		categoryAdapter.submitList(categoryList)

		with(binding) {
			rvCategories.adapter = categoryAdapter
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun onCategoryClick(id: Int) {
		Log.v("HomeFragment", categoryAdapter.currentList[id])
		Log.v("HomeFragment", "clicked")
		findNavController().navigate(
			HomeFragmentDirections.actionHomeFragmentToExcuseFragment(categoryAdapter.currentList[id])
		)
	}
}