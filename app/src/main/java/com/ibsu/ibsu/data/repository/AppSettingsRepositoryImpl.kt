package com.ibsu.ibsu.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.ibsu.ibsu.domain.repository.AppSettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val SHARED_PREFS_NAME = "appSettingPrefs"
private const val KEY_DARK_MODE = "darkModeVal"
private const val KEY_LANGUAGE_MODE = "languageVal"
private const val KEY_DISABLE_LABEL_MODE = "disableLabel"
class AppSettingsRepositoryImpl @Inject constructor(@ApplicationContext context: Context) : AppSettingsRepository {
    private val appSettingPrefs: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    private val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()
    override fun setDarkModeSetting(darkModeSetting: Int) {
        sharedPrefsEdit.putInt(KEY_DARK_MODE, darkModeSetting)
        sharedPrefsEdit.apply()
    }

    override fun getDarkModeSetting(): Int { //0 dark, 1 light, 2 system default
        return appSettingPrefs.getInt(KEY_DARK_MODE, 2)
    }

    override fun setLanguageSetting(languageSetting: Int) {
        sharedPrefsEdit.putInt(KEY_LANGUAGE_MODE, languageSetting)
        sharedPrefsEdit.apply()
    }

    override fun getLanguageSetting(): Int { //0 english, 1 georgian, 2 system default
        return appSettingPrefs.getInt(KEY_LANGUAGE_MODE, 2)
    }

    override fun getBottomNavBarLabelVisibilitySetting() = !appSettingPrefs.getBoolean(KEY_DISABLE_LABEL_MODE, false)

    override fun setBottomNavBarLabelVisibilitySetting(visibilitySetting: Boolean) {
        sharedPrefsEdit.putBoolean(KEY_DISABLE_LABEL_MODE, !visibilitySetting)
        sharedPrefsEdit.apply()
    }
}