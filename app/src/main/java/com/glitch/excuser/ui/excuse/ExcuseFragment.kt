package com.glitch.excuser.ui.excuse

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.glitch.excuser.R
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

		viewModel.getExcuse(args.category)
		observeData()
		with(binding) {
			btnAnother.setOnClickListener {
				viewModel.getExcuse(args.category)
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
					progressBar.isVisible = true
					btnAnother.isVisible = false
				}

				is ExcuseState.SuccessState -> {
					tvExcuse.text = state.excuseResponses[0].excuse
					progressBar.isVisible = false
					btnAnother.isVisible = true
				}

				is ExcuseState.EmptyScreen -> {
					Log.d("ExcuseFragment", "Empty Screen: ${state.failMessage}")
					tvExcuse.text = getString(R.string.error)
					progressBar.isVisible = false
					btnAnother.isVisible = false
				}

				is ExcuseState.ShowMessage -> {
					Log.d("ExcuseFragment", "Show Message: ${state.errorMessage}")
					tvExcuse.text = getString(R.string.error)
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