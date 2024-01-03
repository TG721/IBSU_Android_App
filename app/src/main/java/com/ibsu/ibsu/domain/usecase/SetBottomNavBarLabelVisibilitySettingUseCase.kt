package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.domain.repository.AppSettingsRepository
import javax.inject.Inject

class SetBottomNavBarLabelVisibilitySettingUseCase @Inject
constructor(
    private val appSettingsRepository: AppSettingsRepository,
) {
    fun execute(enable: Boolean) {
        return appSettingsRepository.setBottomNavBarLabelVisibilitySetting(!enable)
    }
}