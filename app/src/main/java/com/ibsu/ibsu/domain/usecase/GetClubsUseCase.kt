package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.data.remote.model.Clubs
import com.ibsu.ibsu.data.remote.model.Programs
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClubsUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getClubs(): Flow<ResponseState<Clubs>> {
        return ibsuRepository.getClubs()
    }
}