package com.ibsu.ibsu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.data.remote.model.ExchangeUniversity
import com.ibsu.ibsu.data.remote.model.Programs
import com.ibsu.ibsu.domain.usecase.GetDoctorateProgramsUseCase
import com.ibsu.ibsu.domain.usecase.GetExchangeUniversitiesForErasmusPlusUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ErasmusPlusViewModel @Inject constructor(private val getExchangeUniversitiesForErasmusPlusUseCase: GetExchangeUniversitiesForErasmusPlusUseCase) : ViewModel(){
    private val _myState =
        MutableStateFlow<ResponseState<ExchangeUniversity>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<ExchangeUniversity>> = _myState //immutable state flow

    fun getExchangeUniversitiesForErasmusPlus() {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getExchangeUniversitiesForErasmusPlusUseCase.getExchangeUniversitiesForErasmusPlus()
            data.collect {
                _myState.value = it
            }
        }

    }

}