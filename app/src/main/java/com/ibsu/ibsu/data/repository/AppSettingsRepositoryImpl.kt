package com.ibsu.ibsu.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.ibsu.ibsu.domain.repository.AppSettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppSettingsRepositoryImpl @Inject constructor(@ApplicationContext context: Context) : AppSettingsRepository {
    private val appSettingPrefs: SharedPreferences =
        context.getSharedPreferences("appSettingPrefs", Context.MODE_PRIVATE)
    private val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()
    override fun setDarkModeSetting(darkModeSetting: Int) {
        sharedPrefsEdit.putInt("darkModeVal", darkModeSetting)
        sharedPrefsEdit.apply()
    }

    override fun getDarkModeSetting(): Int { //0 dark, 1 light, 2 system default
        return appSettingPrefs.getInt("darkModeVal", 2)
    }

    override fun setLanguageSetting(languageSetting: Int) {
        sharedPrefsEdit.putInt("languageVal", languageSetting)
        sharedPrefsEdit.apply()
    }

    override fun getLanguageSetting(): Int { //0 english, 1 georgian, 2 system default
        return appSettingPrefs.getInt("languageVal", 2)
    }

    override fun getBottomNavBarLabelVisibilitySetting() = !appSettingPrefs.getBoolean("disableLabel", false)

    override fun setBottomNavBarLabelVisibilitySetting(visibilitySetting: Boolean) {
        sharedPrefsEdit.putBoolean("disableLabel", !visibilitySetting)
        sharedPrefsEdit.apply()
    }
}