package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.data.remote.model.CreditValue
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCreditValueUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository,
) {
    suspend fun getCreditValue(typeValue: String, programVar: String): Flow<ResponseState<CreditValue>> {
        return ibsuRepository.getCreditValue(typeValue, programVar)
    }
}