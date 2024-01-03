package com.ibsu.ibsu.domain.repository

interface AppSettingsRepository {
    fun setDarkModeSetting(darkModeSetting: Int)
    fun getDarkModeSetting(): Int //0 dark, 1 light, 2 system default
    fun setLanguageSetting(languageSetting: Int)
    fun getLanguageSetting(): Int //0 english, 1 georgian, 2 system default
    fun getBottomNavBarLabelVisibilitySetting(): Boolean
    fun setBottomNavBarLabelVisibilitySetting(visibilitySetting: Boolean)

}