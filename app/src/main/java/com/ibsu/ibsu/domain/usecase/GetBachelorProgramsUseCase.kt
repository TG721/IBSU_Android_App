package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.data.remote.model.Programs
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBachelorProgramsUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getBachelorPrograms(): Flow<ResponseState<Programs>> {
        return ibsuRepository.getPrograms()
    }
}