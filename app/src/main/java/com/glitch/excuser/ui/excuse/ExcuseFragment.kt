package com.glitch.excuser.ui.excuse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentExcuseBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewModel.getExcuse(args.category)
		observeData()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

    private fun observeData() = with(binding) {
        viewModel.excuseState.observe(viewLifecycleOwner) { state ->
            when (state) {

                is ExcuseState.SuccessState -> {
                    tvExcuse.text = state.excuseResponse.excuse
                }

                else -> {
                    tvExcuse.text = "Error"
                }
            }
        }
    }
}