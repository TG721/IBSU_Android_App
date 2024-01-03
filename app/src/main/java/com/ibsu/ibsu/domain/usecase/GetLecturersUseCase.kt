package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.domain.model.Administration
import com.ibsu.ibsu.domain.model.Lecturers
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLecturersUseCase @Inject
constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getLecturers(schoolNameVal: String): Flow<ResponseState<com.ibsu.ibsu.domain.model.Lecturers>> {
        return ibsuRepository.getLecturers(schoolNameVal)
    }
}