package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.domain.model.ProgramAdmin
import com.ibsu.ibsu.domain.model.Programs
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProgramAdministrationUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getProgramAdministration(typeValue: String, programVar: String): Flow<ResponseState<com.ibsu.ibsu.domain.model.ProgramAdmin>> {
        return ibsuRepository.getProgramAdministration(typeValue, programVar)
    }
}