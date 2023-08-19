package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.data.remote.IBSUApi
import com.ibsu.ibsu.data.remote.model.CurrentWeek
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeekValueUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getCurrentWeek(): Flow<ResponseState<CurrentWeek>> {
        return ibsuRepository.getCurrentWeek()
    }
}