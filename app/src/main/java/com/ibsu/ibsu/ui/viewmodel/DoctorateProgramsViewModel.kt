package com.ibsu.ibsu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.data.remote.model.Programs
import com.ibsu.ibsu.domain.usecase.GetDoctorateProgramsUseCase
import com.ibsu.ibsu.domain.usecase.getMasterProgramsUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorateProgramsViewModel @Inject constructor(private val getDoctorateProgramsUseCase: GetDoctorateProgramsUseCase) : ViewModel(){
    private val _myState =
        MutableStateFlow<ResponseState<Programs>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<Programs>> = _myState //immutable state flow

    fun getDoctoratePrograms() {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getDoctorateProgramsUseCase.getDoctoratePrograms()
            data.collect {
                _myState.value = it
            }
        }

    }

}