package com.ibsu.ibsu.ui.viewmodel.iro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.domain.model.ExchangeUniversity
import com.ibsu.ibsu.domain.usecase.GetExchangeUniversitiesForBilateralUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BilateralViewModel @Inject constructor(private val getExchangeUniversitiesForBilateralUseCase: GetExchangeUniversitiesForBilateralUseCase) : ViewModel(){
    private val _myState =
        MutableStateFlow<ResponseState<com.ibsu.ibsu.domain.model.ExchangeUniversity>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<com.ibsu.ibsu.domain.model.ExchangeUniversity>> = _myState //immutable state flow

    fun getExchangeUniversitiesForBilateral() {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getExchangeUniversitiesForBilateralUseCase.execute()
            data.collect {
                _myState.value = it
            }
        }

    }

}