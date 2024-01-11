package com.glitch.excuser.ui.home

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.glitch.excuser.R
import com.glitch.excuser.data.mapper.CategoryMapping
import com.glitch.excuser.databinding.DialogChangeLanguageBinding
import com.glitch.excuser.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!

	private lateinit var sharedPref: SharedPreferences

	private val viewModel by viewModels<HomeViewModel>()

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
		sharedPref = requireActivity().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)

		if (sharedPref.getBoolean("firstTime", true)) {
			sharedPref.edit().putString("language", getString(R.string.current_language)).apply()
			sharedPref.edit().putBoolean("firstTime", false).apply()
		}
		observeData()
		viewModel.changeLanguage(
			buildString {
				append(getString(R.string.language))
				append(" : ")
				append(sharedPref.getString("language", "eng"))
			}
		)

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

			btnLanguage.setOnClickListener {
				showLanguageDialog()
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun observeData() = with(binding) {
		viewModel.homeState.observe(viewLifecycleOwner) { state ->
			when (state) {
				HomeState.Loading -> {
					btnLanguage.isVisible = false
				}

				is HomeState.Language -> {
					btnLanguage.text = state.language
					btnLanguage.isVisible = true
				}
			}
		}
	}

	private fun onCategoryClick(id: Int) {
		val localizedCategory = categoryAdapter.currentList[id].lowercase()

		val englishCategory = if(localizedCategory == getString(R.string.unbelievable)){
			R.string.unbelievable_fixed.toString()
		} else {
			CategoryMapping.localizedToEnglish[localizedCategory] ?: localizedCategory
		}

		findNavController().navigate(
			HomeFragmentDirections.actionHomeFragmentToExcuseFragment(englishCategory)
		)
	}

	private fun showLanguageDialog() {
		val builder = AlertDialog.Builder(requireContext())
		val dialogBinding = DialogChangeLanguageBinding.inflate(layoutInflater)
		builder.setView(dialogBinding.root)
		val dialog = builder.create()

		with(dialogBinding) {
			btnSelect.setOnClickListener {
				val push = when (rgLanguage.checkedRadioButtonId) {
					R.id.rbEng -> "eng"
					R.id.rbTur -> "tr"
					R.id.rbFrench -> "fren"
					R.id.rbHin -> "hin"
					R.id.rbPtbr -> "pt-br"
					else -> "ben"
				}
				sharedPref.edit().putString("language", push).apply()
				viewModel.changeLanguage(
					buildString {
						append(getString(R.string.language))
						append(" : ")
						append(sharedPref.getString("language", "eng"))
					}
				)
				dialog.dismiss()
			}
		}
		dialog.show()
	}
}