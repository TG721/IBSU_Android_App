package com.ibsu.ibsu.domain.usecase


import com.ibsu.ibsu.domain.model.ContactInfo
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContactInfoUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun execute(): Flow<ResponseState<com.ibsu.ibsu.domain.model.ContactInfo>> {
        return ibsuRepository.getContactInfo()
    }
}