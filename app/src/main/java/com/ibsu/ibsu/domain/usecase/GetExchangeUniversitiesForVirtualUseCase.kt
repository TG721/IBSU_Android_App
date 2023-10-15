package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.data.remote.model.ExchangeUniversity
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExchangeUniversitiesForVirtualUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getExchangeUniversitiesForVirtual(): Flow<ResponseState<ExchangeUniversity>> {
        return ibsuRepository.getExchangeUniversitiesForVirtual()
    }
}