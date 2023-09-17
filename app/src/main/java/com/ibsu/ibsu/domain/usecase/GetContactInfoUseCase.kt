package com.ibsu.ibsu.domain.usecase


import com.ibsu.ibsu.data.remote.model.ContactInfo
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContactInfoUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getContactInfo(): Flow<ResponseState<ContactInfo>> {
        return ibsuRepository.getContactInfo()
    }
}