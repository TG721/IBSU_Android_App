package com.ibsu.ibsu.ui.viewmodel.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.data.remote.model.WorkingHours
import com.ibsu.ibsu.domain.usecase.GetWorkingHoursUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkingHoursViewModel @Inject constructor(private val getWorkingHoursUseCase: GetWorkingHoursUseCase) :
    ViewModel() {
    private val _myState =
        MutableStateFlow<ResponseState<WorkingHours>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<WorkingHours>> = _myState //immutable state flow

    fun getWorkingHours() {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getWorkingHoursUseCase.getWorkingHours()
            data.collect {
                _myState.value = it
            }
        }

    }

}