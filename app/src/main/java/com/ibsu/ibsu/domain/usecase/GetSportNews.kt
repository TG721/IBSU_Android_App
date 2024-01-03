package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.domain.model.News
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSportNews @Inject
constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getSportNews(): Flow<ResponseState<com.ibsu.ibsu.domain.model.News>> {
        return ibsuRepository.getSportNews()
    }
}