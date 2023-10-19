package com.ibsu.ibsu.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.ibsu.ibsu.utils.SportMenu.SportTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SportMenuViewModel @Inject constructor(): ViewModel() {

    private val _myState1 = MutableStateFlow<Int>(SportTypes)
    val myState1: StateFlow<Int> get() = _myState1


    fun setSportMenuActiveItem(activeItem: Int) {
        _myState1.value = activeItem
    }

    fun getSportMenuActiveItem(): Int {
        return _myState1.value
    }


}