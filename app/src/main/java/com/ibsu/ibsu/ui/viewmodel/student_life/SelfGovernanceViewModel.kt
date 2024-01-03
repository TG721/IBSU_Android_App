package com.ibsu.ibsu.ui.viewmodel.student_life

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.domain.model.Governance
import com.ibsu.ibsu.domain.usecase.GetSelfGovernanceUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelfGovernanceViewModel @Inject constructor(private val getSelfGovernanceUseCase: GetSelfGovernanceUseCase) : ViewModel(){
    private val _myState =
        MutableStateFlow<ResponseState<com.ibsu.ibsu.domain.model.Governance>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<com.ibsu.ibsu.domain.model.Governance>> = _myState //immutable state flow

    fun getSelfGovernance() {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getSelfGovernanceUseCase.getSelfGovernance()
            data.collect {
                _myState.value = it
            }
        }

    }

}