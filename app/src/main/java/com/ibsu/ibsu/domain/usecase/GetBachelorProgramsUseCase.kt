package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.domain.model.Programs
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBachelorProgramsUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun execute(): Flow<ResponseState<com.ibsu.ibsu.domain.model.Programs>> {
        return ibsuRepository.getBachelorPrograms()
    }
}