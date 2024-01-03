package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.domain.model.Governance
import com.ibsu.ibsu.domain.repository.AppSettingsRepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDarkModeSettingUseCase @Inject
constructor(
    private val appSettingsRepository: AppSettingsRepository,
) {
    fun execute(): Int {
        return appSettingsRepository.getDarkModeSetting()
    }
}