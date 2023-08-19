package com.ibsu.ibsu.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import java.util.Locale

object LocaleHelper {
    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

    // the method is used to set the language at runtime
    fun setLocale(context: Context, language: String): Context {
        persist(context, language)

        // updating the language for devices above android nougat
        return updateResources(context, language)
    }

    private fun persist(context: Context, language: String) {
        val preferences = context.getSharedPreferences("LocaleHelperPrefs", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    // the method is used update the language of application by creating
    // object of inbuilt Locale class and passing language argument to it
    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }

}
