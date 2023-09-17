package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.data.remote.model.GameRoomLocation
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGameRoomLocationUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getGameRoomLocation(): Flow<ResponseState<GameRoomLocation>> {
        return ibsuRepository.getGameRoomLocation()
    }
}