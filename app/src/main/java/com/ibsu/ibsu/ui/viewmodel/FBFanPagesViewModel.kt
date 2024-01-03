package com.ibsu.ibsu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.domain.model.FBFanPages
import com.ibsu.ibsu.domain.usecase.GetFBFanPagesUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FBFanPagesViewModel @Inject constructor(private val getFBFanPagesUseCase: GetFBFanPagesUseCase) :
    ViewModel() {
    private val _myState =
        MutableStateFlow<ResponseState<com.ibsu.ibsu.domain.model.FBFanPages>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<com.ibsu.ibsu.domain.model.FBFanPages>> = _myState //immutable state flow

    fun getFBFanPages() {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getFBFanPagesUseCase.getFBFanPages()
            data.collect {
                _myState.value = it
            }
        }

    }

}