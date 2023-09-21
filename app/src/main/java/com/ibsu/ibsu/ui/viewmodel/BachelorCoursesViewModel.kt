package com.ibsu.ibsu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.data.remote.model.Courses
import com.ibsu.ibsu.data.remote.model.Programs
import com.ibsu.ibsu.domain.usecase.GetBachelorCoursesUseCase
import com.ibsu.ibsu.domain.usecase.GetBachelorProgramsUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BachelorCoursesViewModel @Inject constructor(private val getBachelorCoursesUseCase: GetBachelorCoursesUseCase) : ViewModel(){
    private val _myState =
        MutableStateFlow<ResponseState<Courses>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<Courses>> = _myState //immutable state flow

    fun getBachelorCourses(programVar: String) {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getBachelorCoursesUseCase.getBachelorCourses(programVar)
            data.collect {
                _myState.value = it
            }
        }

    }

}