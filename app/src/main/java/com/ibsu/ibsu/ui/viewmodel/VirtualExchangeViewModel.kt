package com.ibsu.ibsu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.data.remote.model.ExchangeUniversity
import com.ibsu.ibsu.domain.usecase.GetExchangeUniversitiesForVirtualUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VirtualExchangeViewModel @Inject constructor(private val getExchangeUniversitiesForVirtualUseCase: GetExchangeUniversitiesForVirtualUseCase) :
    ViewModel() {
    private val _myState =
        MutableStateFlow<ResponseState<ExchangeUniversity>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<ExchangeUniversity>> = _myState //immutable state flow

    fun getExchangeUniversitiesForVirtual() {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getExchangeUniversitiesForVirtualUseCase.getExchangeUniversitiesForVirtual()
            data.collect {
                _myState.value = it
            }
        }

    }

}