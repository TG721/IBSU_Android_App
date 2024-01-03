package com.ibsu.ibsu.domain.usecase

import com.ibsu.ibsu.domain.repository.AppSettingsRepository
import javax.inject.Inject

class GetLanguageSettingUseCase @Inject
constructor(
    private val appSettingsRepository: AppSettingsRepository,
) {
    fun execute(): Int {
        return appSettingsRepository.getLanguageSetting()
    }
}
