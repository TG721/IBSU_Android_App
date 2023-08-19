package com.ibsu.ibsu.utils

import android.util.Log.d
import android.webkit.JavascriptInterface
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.ibsu.ibsu.R
import com.ibsu.ibsu.ui.element.SISFragmentDirections

class JavaScriptInterfaceForSIS(private val fragment: Fragment) {
    @JavascriptInterface
    fun passValueToKotlin(school: String) {
        val schoolName = school.substring(10)

        fragment.activity?.runOnUiThread {
            val action = SISFragmentDirections.actionSISFragmentToSchoolMenuFragment(schoolName)
            NavHostFragment.findNavController(fragment).navigate(action)
        }
    }
    }


