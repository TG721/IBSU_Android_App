package com.ibsu.ibsu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.data.remote.model.Courses
import com.ibsu.ibsu.data.remote.model.CreditValue
import com.ibsu.ibsu.domain.usecase.GetBachelorCoursesUseCase
import com.ibsu.ibsu.domain.usecase.GetBachelorCreditValueUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BachelorCreditValueViewModel @Inject constructor(private val getBachelorCreditValueUseCase: GetBachelorCreditValueUseCase) : ViewModel(){
    private val _myState =
        MutableStateFlow<ResponseState<CreditValue>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<CreditValue>> = _myState //immutable state flow

    fun getBachelorCreditValue(programVar: String) {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getBachelorCreditValueUseCase.getBachelorCreditValue(programVar)
            data.collect {
                _myState.value = it
            }
        }

    }

}