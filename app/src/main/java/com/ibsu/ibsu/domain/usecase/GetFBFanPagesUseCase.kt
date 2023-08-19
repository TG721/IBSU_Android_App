package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.data.remote.model.FBFanPages
import com.ibsu.ibsu.data.remote.model.Games
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFBFanPagesUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getFBFanPages(): Flow<ResponseState<FBFanPages>> {
        return ibsuRepository.getFBFanPages()
    }
}