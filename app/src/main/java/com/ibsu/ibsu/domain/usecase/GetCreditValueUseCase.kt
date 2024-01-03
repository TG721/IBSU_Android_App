package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.domain.model.CreditValue
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCreditValueUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository,
) {
    suspend fun execute(typeValue: String, programVar: String): Flow<ResponseState<com.ibsu.ibsu.domain.model.CreditValue>> {
        return ibsuRepository.getCreditValue(typeValue, programVar)
    }
}