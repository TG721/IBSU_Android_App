package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.domain.model.Address
import com.ibsu.ibsu.domain.model.ContactInfo
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAddressUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getAddress(): Flow<ResponseState<com.ibsu.ibsu.domain.model.Address>> {
        return ibsuRepository.getAddress()
    }
}