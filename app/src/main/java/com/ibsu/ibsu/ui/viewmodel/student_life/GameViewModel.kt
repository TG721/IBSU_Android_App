package com.ibsu.ibsu.ui.viewmodel.student_life

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.domain.model.GameRoomLocation
import com.ibsu.ibsu.domain.model.Games
import com.ibsu.ibsu.domain.usecase.GetGameRoomLocationUseCase
import com.ibsu.ibsu.domain.usecase.GetGamesUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase,
    private val getGameRoomLocation: GetGameRoomLocationUseCase,
) : ViewModel() {
    private val _myState =
        MutableStateFlow<ResponseState<com.ibsu.ibsu.domain.model.Games>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<com.ibsu.ibsu.domain.model.Games>> = _myState //immutable state flow

    private val _myState2 =
        MutableStateFlow<ResponseState<com.ibsu.ibsu.domain.model.GameRoomLocation>>(ResponseState.Empty()) //mutable state flow
    val myState2: StateFlow<ResponseState<com.ibsu.ibsu.domain.model.GameRoomLocation>> = _myState2 //immutable state flow

    fun getGames() {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getGamesUseCase.execute()
            data.collect {
                _myState.value = it
            }
        }

    }

    fun getGameRoomLocation() {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getGameRoomLocation.execute()
            data.collect {
                _myState2.value = it
            }
        }
    }

}