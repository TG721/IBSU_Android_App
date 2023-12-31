package com.ibsu.ibsu.ui.viewmodel.student_life

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.domain.model.News
import com.ibsu.ibsu.domain.usecase.GetSportNews
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportNewsViewModel @Inject constructor(private val getSportNews: GetSportNews) : ViewModel(){
    private val _myState =
        MutableStateFlow<ResponseState<com.ibsu.ibsu.domain.model.News>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<com.ibsu.ibsu.domain.model.News>> = _myState //immutable state flow

    fun getNews() {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getSportNews.getSportNews()
            data.collect {
                _myState.value = it
            }
        }

    }

}