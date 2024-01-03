package com.ibsu.ibsu.ui.viewmodel

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.domain.model.Administration
import com.ibsu.ibsu.domain.usecase.GetSchoolAdminStaffUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminStaffViewModel @Inject constructor(private val getSchoolAdminStaffUseCase: GetSchoolAdminStaffUseCase) :
    ViewModel() {
    private val _myState =
        MutableStateFlow<ResponseState<com.ibsu.ibsu.domain.model.Administration>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<com.ibsu.ibsu.domain.model.Administration>> = _myState //immutable state flow

    fun getAdminStaff(schoolPathVariable: String) {
        viewModelScope.launch {
            d("givenVariableValue", schoolPathVariable)
            _myState.emit(ResponseState.Loading())
            val data = getSchoolAdminStaffUseCase.getAdminStaff(schoolPathVariable)
            data.collect {
                _myState.value = it
            }
        }

    }

}