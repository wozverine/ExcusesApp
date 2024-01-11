package com.glitch.excuser.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

	private var _homeState = MutableLiveData<HomeState>()
	val homeState: LiveData<HomeState> get() = _homeState

	fun changeLanguage(language: String) {
		_homeState.value = HomeState.Loading

		_homeState.value = HomeState.Language(language)
	}
}

sealed interface HomeState {
	data object Loading : HomeState

	data class Language(val language: String) : HomeState
}