package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.data.remote.model.News
import com.ibsu.ibsu.data.remote.model.SelfGovernance
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSportNews @Inject
constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getSportNews(): Flow<ResponseState<News>> {
        return ibsuRepository.getSportNews()
    }
}