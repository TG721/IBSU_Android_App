package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.data.remote.model.Administration
import com.ibsu.ibsu.data.remote.model.Lecturers
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLecturersUseCase @Inject
constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getLecturers(schoolNameVal: String): Flow<ResponseState<Lecturers>> {
        return ibsuRepository.getLecturers(schoolNameVal)
    }
}