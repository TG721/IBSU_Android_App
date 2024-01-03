package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.domain.repository.AppSettingsRepository
import javax.inject.Inject

class SetDarkModeSettingUseCase @Inject
constructor(
    private val appSettingsRepository: AppSettingsRepository,
) {
    fun execute(setting: Int) {
        return appSettingsRepository.setDarkModeSetting(setting)
    }
}