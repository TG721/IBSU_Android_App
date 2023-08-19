package com.ibsu.ibsu.extensions

import android.content.Context
import java.util.Locale

fun Context.getCurrentLocale(context: Context): Locale {
    return context.resources.configuration.locales.get(0)

}