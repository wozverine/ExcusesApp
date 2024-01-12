package com.glitch.excuser.ui.excuse

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.glitch.excuser.R
import com.glitch.excuser.data.mapper.CategoryMapping
import com.glitch.excuser.databinding.FragmentExcuseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExcuseFragment : Fragment(R.layout.fragment_excuse) {
	private var _binding: FragmentExcuseBinding? = null
	private val binding get() = _binding!!

	private val viewModel by viewModels<ExcuseViewModel>()

	private val args by navArgs<ExcuseFragmentArgs>()
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View {
		_binding = FragmentExcuseBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val localizedCategory = args.category.lowercase()
		val englishCategory = if (localizedCategory == getString(R.string.unbelievable)) {
			R.string.unbelievable_fixed.toString()
		} else {
			CategoryMapping.localizedToEnglish[localizedCategory] ?: localizedCategory
		}

		viewModel.getExcuse(englishCategory)
		observeData()
		with(binding) {
			tvCategory.text = args.category
			btnAnother.setOnClickListener {
				viewModel.getExcuse(englishCategory)
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun observeData() = with(binding) {
		val isConnected = isNetworkConnected(requireContext())

		if (!isConnected) {
			tvExcuse.text = getString(R.string.no_connection)
			return
		}
		viewModel.excuseState.observe(viewLifecycleOwner) { state ->
			when (state) {
				ExcuseState.Loading -> {
					tvExcuse.isVisible = false
					progressBar.isVisible = true
					btnAnother.isVisible = false
				}

				is ExcuseState.SuccessState -> {
					tvExcuse.text = state.excuseResponses[0].excuse
					tvExcuse.isVisible = true
					progressBar.isVisible = false
					btnAnother.isVisible = true
				}

				is ExcuseState.EmptyScreen -> {
					tvExcuse.text = getString(R.string.error)
					tvExcuse.isVisible = true
					progressBar.isVisible = false
					btnAnother.isVisible = false
				}

				is ExcuseState.ShowMessage -> {
					tvExcuse.text = getString(R.string.error)
					tvExcuse.isVisible = true
					progressBar.isVisible = false
					btnAnother.isVisible = false
				}
			}
		}
	}
}

private fun isNetworkConnected(context: Context): Boolean {
	val connectivityManager =
		context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

	val networkCapabilities = connectivityManager.activeNetwork ?: return false
	val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

	return actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}