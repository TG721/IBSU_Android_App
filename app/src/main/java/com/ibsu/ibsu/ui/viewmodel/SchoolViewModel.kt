package com.ibsu.ibsu.ui.viewmodel

import android.opengl.Visibility
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SchoolViewModel @Inject constructor(): ViewModel() {

    private val _myState = MutableStateFlow<String>("")
    val myState: StateFlow<String> get() = _myState

    fun setSchoolValue(schoolValue: String) {
        _myState.value = schoolValue
    }

    fun getSchoolValue(): String {
        return _myState.value
    }

    private val _myState2 = MutableStateFlow<Boolean>(false)
    val myState2: StateFlow<Boolean> get() = _myState2

    fun setEmailVisibility(visibility: Boolean) {
        _myState2.value = visibility
    }

    fun getEmailVisibility(): Boolean {
        return _myState2.value
    }


}