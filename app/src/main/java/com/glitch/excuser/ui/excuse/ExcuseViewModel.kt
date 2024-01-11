package com.glitch.excuser.ui.excuse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glitch.excuser.common.Resource
import com.glitch.excuser.data.model.response.GetExcuseResponse
import com.glitch.excuser.data.repository.ExcuseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExcuseViewModel @Inject constructor(
	private val excuseRepository: ExcuseRepository
) : ViewModel() {
	private var _excuseState = MutableLiveData<ExcuseState>()
	val excuseState: LiveData<ExcuseState> get() = _excuseState

	private var excuseData: List<GetExcuseResponse>? = null

	fun getExcuse(category: String) = viewModelScope.launch {
		_excuseState.value = ExcuseState.Loading

		_excuseState.value = when (val result = excuseRepository.getExcuse(category)) {
			is Resource.Success -> {
				excuseData = result.data
				ExcuseState.SuccessState(result.data)
			}

			is Resource.Fail -> ExcuseState.EmptyScreen(result.failMessage)
			is Resource.Error -> ExcuseState.ShowMessage(result.errorMessage)
		}
	}
}


sealed interface ExcuseState {
	data object Loading : ExcuseState
	data class SuccessState(val excuseResponses: List<GetExcuseResponse>) : ExcuseState
	data class EmptyScreen(val failMessage: String) : ExcuseState
	data class ShowMessage(val errorMessage: String) : ExcuseState
}

