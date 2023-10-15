package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.data.remote.model.Courses
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoursesUseCase @Inject constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getCourses(typeValue: String, programVar: String): Flow<ResponseState<Courses>> {
        return ibsuRepository.getCourses(typeValue, programVar)
    }
}