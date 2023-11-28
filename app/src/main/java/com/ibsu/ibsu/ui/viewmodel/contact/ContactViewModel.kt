package com.ibsu.ibsu.ui.viewmodel.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.data.remote.model.Address
import com.ibsu.ibsu.data.remote.model.ContactInfo
import com.ibsu.ibsu.domain.usecase.GetAddressUseCase
import com.ibsu.ibsu.domain.usecase.GetContactInfoUseCase
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val getContactInfoUseCase: GetContactInfoUseCase,
    private val getAddressUseCase: GetAddressUseCase
    ) :
    ViewModel() {
    private val _myState =
        MutableStateFlow<ResponseState<ContactInfo>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<ContactInfo>> = _myState //immutable state flow

    private val _myState2 =
        MutableStateFlow<ResponseState<Address>>(ResponseState.Empty())
    val myState2: StateFlow<ResponseState<Address>> = _myState2

    fun getContactInfo() {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getContactInfoUseCase.getContactInfo()
            data.collect {
                _myState.value = it
            }
        }

    }

    fun getAddress() {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getAddressUseCase.getAddress()
            data.collect {
                _myState2.value = it
            }
        }
    }

}