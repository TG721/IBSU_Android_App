package com.ibsu.ibsu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.data.remote.model.CurrentWeek
import com.ibsu.ibsu.domain.usecase.GetWeekValueUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeekValueViewModel @Inject constructor(private val getWeekValueUseCase: GetWeekValueUseCase) : ViewModel(){
    private val _myState =
        MutableStateFlow<ResponseState<CurrentWeek>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<CurrentWeek>> = _myState //immutable state flow

    fun getCurrentWeek() {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getWeekValueUseCase.getCurrentWeek()
            data.collect {
                _myState.value = it
            }
        }

    }

}