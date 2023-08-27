package com.ibsu.ibsu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.data.remote.model.News
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
        MutableStateFlow<ResponseState<News>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<News>> = _myState //immutable state flow

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