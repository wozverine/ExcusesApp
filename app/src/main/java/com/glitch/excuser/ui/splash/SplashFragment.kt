package com.glitch.excuser.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.glitch.excuser.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

	private val viewModel by viewModels<SplashViewModel>()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		goToHome()
	}

	private fun goToHome() {
		viewModel.splashState.observe(viewLifecycleOwner) { state ->
			when (state) {
				SplashState.GoToHome -> {
					findNavController().navigate(R.id.homeFragment)
				}
			}
		}
	}
}