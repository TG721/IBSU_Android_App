package com.ibsu.ibsu.utils

import android.webkit.JavascriptInterface
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.ibsu.ibsu.ui.element.SISFragmentDirections

class JavaScriptInterfaceForSIS(private val fragment: Fragment) {

    @JavascriptInterface
    fun passValueToKotlin(school: String) {
        val schoolName = school.substring(10)

        fragment.activity?.runOnUiThread {
            val action = SISFragmentDirections.actionSISFragmentToSchoolMenuFragment(schoolName, true)
            NavHostFragment.findNavController(fragment).navigate(action)
        }
    }
    }


