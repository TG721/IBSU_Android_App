package com.ibsu.ibsu.extensions

import android.content.Context
import java.util.Locale

fun Context.getCurrentLocale(): Locale {
    return this.resources.configuration.locales.get(0)

}