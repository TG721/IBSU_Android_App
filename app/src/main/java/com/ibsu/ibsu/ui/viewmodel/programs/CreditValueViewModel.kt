package com.ibsu.ibsu.ui.viewmodel.programs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.domain.model.CreditValue
import com.ibsu.ibsu.domain.usecase.GetCreditValueUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreditValueViewModel @Inject constructor(private val getBachelorCreditValueUseCase: GetCreditValueUseCase) : ViewModel(){
    private val _myState =
        MutableStateFlow<ResponseState<com.ibsu.ibsu.domain.model.CreditValue>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<com.ibsu.ibsu.domain.model.CreditValue>> = _myState //immutable state flow

    fun getBachelorCreditValue(typeValue: String, programVar: String) {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getBachelorCreditValueUseCase.execute(typeValue, programVar)
            data.collect {
                _myState.value = it
            }
        }

    }

}