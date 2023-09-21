package com.ibsu.ibsu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.data.remote.model.ProgramAdmin
import com.ibsu.ibsu.domain.usecase.GetProgramAdministrationUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgramAdministrationViewModel @Inject constructor(private val getProgramAdministrationUseCase: GetProgramAdministrationUseCase) :
    ViewModel() {
    private val _myState =
        MutableStateFlow<ResponseState<ProgramAdmin>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<ProgramAdmin>> = _myState //immutable state flow

    fun getProgramAdministration(programVar: String) {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getProgramAdministrationUseCase.getProgramAdministration(programVar)
            data.collect {
                _myState.value = it
            }
        }

    }

}