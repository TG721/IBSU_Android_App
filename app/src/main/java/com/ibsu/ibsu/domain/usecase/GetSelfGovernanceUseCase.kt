package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.data.remote.model.Governance
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSelfGovernanceUseCase @Inject
constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getSelfGovernance(): Flow<ResponseState<Governance>> {
        return ibsuRepository.getSelfGovernance()
    }
}