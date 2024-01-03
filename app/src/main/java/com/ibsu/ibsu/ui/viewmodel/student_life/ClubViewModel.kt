package com.ibsu.ibsu.ui.viewmodel.student_life

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.domain.model.Clubs
import com.ibsu.ibsu.domain.usecase.GetClubsUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubViewModel @Inject constructor(private val getClubsUseCase: GetClubsUseCase) : ViewModel(){
    private val _myState =
        MutableStateFlow<ResponseState<com.ibsu.ibsu.domain.model.Clubs>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<com.ibsu.ibsu.domain.model.Clubs>> = _myState //immutable state flow

    fun getClubs() {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getClubsUseCase.execute()
            data.collect {
                _myState.value = it
            }
        }

    }

}