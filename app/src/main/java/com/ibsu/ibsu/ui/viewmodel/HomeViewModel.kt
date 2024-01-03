package com.ibsu.ibsu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.domain.model.SliderEvents
import com.ibsu.ibsu.domain.usecase.GetSliderEventsUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getSliderEventsUseCase: GetSliderEventsUseCase) : ViewModel(){
    private val _myState =
        MutableStateFlow<ResponseState<com.ibsu.ibsu.domain.model.SliderEvents>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<com.ibsu.ibsu.domain.model.SliderEvents>> = _myState //immutable state flow

    fun getSliderEvents() {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getSliderEventsUseCase.getSliderEvents()
            data.collect {
                _myState.value = it
            }
        }

    }

}