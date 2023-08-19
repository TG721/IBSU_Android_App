package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.data.remote.model.Games
import com.ibsu.ibsu.data.remote.model.SliderEvents
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGamesUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getGames(): Flow<ResponseState<Games>> {
        return ibsuRepository.getGames()
    }
}