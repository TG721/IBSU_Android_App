package com.ibsu.ibsu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.domain.model.News
import com.ibsu.ibsu.domain.model.UsefulDocs
import com.ibsu.ibsu.domain.usecase.GetSportNews
import com.ibsu.ibsu.domain.usecase.GetUseFulDocsUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsefulDocsViewModel @Inject constructor(private val getUseFulDocsUseCase: GetUseFulDocsUseCase) : ViewModel(){
    private val _myState =
        MutableStateFlow<ResponseState<com.ibsu.ibsu.domain.model.UsefulDocs>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<com.ibsu.ibsu.domain.model.UsefulDocs>> = _myState //immutable state flow

    fun getUsefulDoc() {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getUseFulDocsUseCase.getUsefulDocs()
            data.collect {
                _myState.value = it
            }
        }

    }

}