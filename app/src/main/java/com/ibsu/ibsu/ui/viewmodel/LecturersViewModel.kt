package com.ibsu.ibsu.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.data.remote.model.Administration
import com.ibsu.ibsu.data.remote.model.Lecturers
import com.ibsu.ibsu.domain.usecase.GetLecturersUseCase
import com.ibsu.ibsu.domain.usecase.GetSchoolAdminStaffUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LecturersViewModel @Inject constructor(private val getLecturersUseCase: GetLecturersUseCase) :
    ViewModel() {
    private val _myState =
        MutableStateFlow<ResponseState<Lecturers>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<Lecturers>> = _myState //immutable state flow

    fun getLecturers(schoolPathVariable: String) {
        viewModelScope.launch {
            Log.d("givenVariableValue", schoolPathVariable)
            _myState.emit(ResponseState.Loading())
            val data = getLecturersUseCase.getLecturers(schoolPathVariable)
            data.collect {
                _myState.value = it
            }
        }

    }

}