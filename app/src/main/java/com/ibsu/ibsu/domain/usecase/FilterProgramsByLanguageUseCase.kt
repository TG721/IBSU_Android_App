package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.domain.model.ProgramItem
import com.ibsu.ibsu.utils.ProgramLanguageFilter
import javax.inject.Inject

class FilterProgramsByLanguageUseCase @Inject constructor(){
    fun execute(programs: List<com.ibsu.ibsu.domain.model.ProgramItem>, languageFilter: ProgramLanguageFilter) : List<com.ibsu.ibsu.domain.model.ProgramItem>{
        when(languageFilter){
            ProgramLanguageFilter.ENGLISH -> { return programs.filter { it.englishSectorAvailable }}
            ProgramLanguageFilter.GEORGIAN -> { return programs.filter { it.georgianSectorAvailable }}
        }
    }
}