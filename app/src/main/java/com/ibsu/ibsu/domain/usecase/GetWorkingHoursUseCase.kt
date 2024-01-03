package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.domain.model.Governance
import com.ibsu.ibsu.domain.model.WorkingHours
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkingHoursUseCase @Inject
constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getWorkingHours(): Flow<ResponseState<com.ibsu.ibsu.domain.model.WorkingHours>> {
        return ibsuRepository.getWorkingHours()
    }
}