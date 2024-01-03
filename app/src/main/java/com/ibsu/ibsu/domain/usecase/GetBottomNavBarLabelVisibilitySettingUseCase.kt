package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.domain.repository.AppSettingsRepository
import javax.inject.Inject

class GetBottomNavBarLabelVisibilitySettingUseCase @Inject
constructor(
    private val appSettingsRepository: AppSettingsRepository,
) {
    fun execute(): Boolean {
        return appSettingsRepository.getBottomNavBarLabelVisibilitySetting()
    }
}
