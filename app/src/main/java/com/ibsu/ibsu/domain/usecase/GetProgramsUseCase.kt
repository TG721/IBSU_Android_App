package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.data.remote.model.Programs
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProgramsUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getPrograms(): Flow<ResponseState<Programs>> {
        return ibsuRepository.getPrograms()
    }
}