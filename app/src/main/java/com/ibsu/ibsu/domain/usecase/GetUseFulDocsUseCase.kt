package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.domain.model.News
import com.ibsu.ibsu.domain.model.UsefulDocs
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUseFulDocsUseCase @Inject
constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getUsefulDocs(): Flow<ResponseState<com.ibsu.ibsu.domain.model.UsefulDocs>> {
        return ibsuRepository.getUsefulDocs()
    }
}