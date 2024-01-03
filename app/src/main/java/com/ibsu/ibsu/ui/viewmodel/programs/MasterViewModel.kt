package com.ibsu.ibsu.ui.viewmodel.programs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibsu.ibsu.domain.model.ProgramItem
import com.ibsu.ibsu.domain.model.Programs
import com.ibsu.ibsu.domain.usecase.FilterProgramsByLanguageUseCase
import com.ibsu.ibsu.domain.usecase.GetMasterProgramsUseCase
import com.ibsu.ibsu.utils.ProgramLanguageFilter
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MasterViewModel @Inject constructor(
    private val getMastersProgramsUseCase: GetMasterProgramsUseCase,
    private val filterProgramsByLanguageUseCase: FilterProgramsByLanguageUseCase
) : ViewModel(){
    private val _myState =
        MutableStateFlow<ResponseState<com.ibsu.ibsu.domain.model.Programs>>(ResponseState.Empty()) //mutable state flow
    val myState: StateFlow<ResponseState<com.ibsu.ibsu.domain.model.Programs>> = _myState //immutable state flow

    fun getMasterPrograms() {
        viewModelScope.launch {
            _myState.emit(ResponseState.Loading())
            val data = getMastersProgramsUseCase.execute()
            data.collect {
                _myState.value = it
            }
        }

    }

    fun filterProgramsByLanguage(
        programs: List<com.ibsu.ibsu.domain.model.ProgramItem>,
        languageFilter: ProgramLanguageFilter,
    ): List<com.ibsu.ibsu.domain.model.ProgramItem> {
        return filterProgramsByLanguageUseCase.execute(programs, languageFilter)
    }
}