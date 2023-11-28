package com.ibsu.ibsu.ui.viewmodel.programs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.data.remote.model.Programs
import com.ibsu.ibsu.domain.usecase.getMasterProgramsUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MasterViewModel @Inject constructor(private val getProgramsUseCase: getMasterProgramsUseCase) : ViewModel(){
    private val _myState =
        MutableStateFlow<ResponseState<Programs>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<Programs>> = _myState //immutable state flow

    fun getMasterPrograms() {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getProgramsUseCase.getMasterPrograms()
            data.collect {
                _myState.value = it
            }
        }

    }

}