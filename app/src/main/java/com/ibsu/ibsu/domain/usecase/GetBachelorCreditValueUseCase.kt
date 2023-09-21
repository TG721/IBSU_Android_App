package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.data.remote.model.CreditValue
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBachelorCreditValueUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository,
) {
    suspend fun getBachelorCreditValue(programVar: String): Flow<ResponseState<CreditValue>> {
        return ibsuRepository.getCreditValue(programVar)
    }
}