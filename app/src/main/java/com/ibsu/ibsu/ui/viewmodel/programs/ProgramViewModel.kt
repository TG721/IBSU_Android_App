package com.ibsu.ibsu.ui.viewmodel.programs

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProgramViewModel @Inject constructor(): ViewModel() {

    private val _myState1 = MutableStateFlow<String>("")
    val myState1: StateFlow<String> get() = _myState1


    fun setProgramValue(programValue: String) {
        _myState1.value = programValue
    }

    fun getProgramValue(): String {
        return _myState1.value
    }

    private val _myState2 = MutableStateFlow<String>("")
    val myState2: StateFlow<String> get() = _myState2

    fun setTypeValue(TypeValue: String) {
        _myState2.value = TypeValue
    }

    fun getTypeValue(): String {
        return _myState2.value
    }


}