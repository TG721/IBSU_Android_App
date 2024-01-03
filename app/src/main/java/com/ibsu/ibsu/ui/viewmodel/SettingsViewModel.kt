package com.ibsu.ibsu.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.ibsu.ibsu.domain.usecase.GetBottomNavBarLabelVisibilitySettingUseCase
import com.ibsu.ibsu.domain.usecase.GetDarkModeSettingUseCase
import com.ibsu.ibsu.domain.usecase.GetLanguageSettingUseCase
import com.ibsu.ibsu.domain.usecase.SetBottomNavBarLabelVisibilitySettingUseCase
import com.ibsu.ibsu.domain.usecase.SetDarkModeSettingUseCase
import com.ibsu.ibsu.domain.usecase.SetLanguageSettingUseCase
import com.ibsu.ibsu.utils.SportMenu
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getDarkModeSettingUseCase: GetDarkModeSettingUseCase,
    private val setDarkModeSettingUseCase: SetDarkModeSettingUseCase,
    private val getLanguageSettingUseCase: GetLanguageSettingUseCase,
    private val setLanguageSettingUseCase: SetLanguageSettingUseCase,
    private val getBottomNavBarLabelVisibilitySettingUseCase: GetBottomNavBarLabelVisibilitySettingUseCase,
    private val setBottomNavBarLabelVisibilitySettingUseCase: SetBottomNavBarLabelVisibilitySettingUseCase
) : ViewModel() {

    fun getDarkModeSetting() = getDarkModeSettingUseCase.execute()
    fun getLanguageSetting() = getLanguageSettingUseCase.execute()
    fun setLanguageSetting(setting: Int) {
        setLanguageSettingUseCase.execute(setting)
    }
    fun setDarkModeSetting(setting: Int) {
        setDarkModeSettingUseCase.execute(setting)
    }
    fun setBottomNavBarLabelVisibilitySetting(enable: Boolean){
        setBottomNavBarLabelVisibilitySettingUseCase.execute(enable)
    }
    fun getBottomNavBarLabelVisibilitySetting() = getBottomNavBarLabelVisibilitySettingUseCase.execute()

}