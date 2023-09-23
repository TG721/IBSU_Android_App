package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.data.remote.model.FBFanPages
import com.ibsu.ibsu.data.remote.model.Programs
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDoctorateProgramsUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getDoctoratePrograms(): Flow<ResponseState<Programs>> {
        return ibsuRepository.getDoctoratePrograms()
    }
}