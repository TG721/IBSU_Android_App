package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.data.remote.model.Administration
import com.ibsu.ibsu.data.remote.model.SliderEvents
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSchoolAdminStaffUseCase @Inject
constructor(
    private val ibsuRepository: IBSURepository
) {
    suspend fun getAdminStaff(schoolNameVal: String): Flow<ResponseState<Administration>> {
        return ibsuRepository.getAdminStaff(schoolNameVal)
    }
}