package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.data.remote.model.News
import com.ibsu.ibsu.data.remote.model.UsefulDocs
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUseFulDocsUseCase @Inject
constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getUsefulDocs(): Flow<ResponseState<UsefulDocs>> {
        return ibsuRepository.getUsefulDocs()
    }
}