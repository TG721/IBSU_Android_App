package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.domain.model.SliderEvents
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSliderEventsUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getSliderEvents(): Flow<ResponseState<com.ibsu.ibsu.domain.model.SliderEvents>> {
        return ibsuRepository.getSliderEvents()
    }
}