package com.ibsu.ibsu.utils

import android.util.Log.d
import android.webkit.JavascriptInterface
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.ibsu.ibsu.R
import com.ibsu.ibsu.ui.element.SISFragmentDirections
import com.ibsu.ibsu.ui.viewmodel.SchoolViewModel

class JavaScriptInterfaceForSIS(private val fragment: Fragment,) {

    @JavascriptInterface
    fun passValueToKotlin(school: String) {
        val schoolName = school.substring(10)

        fragment.activity?.runOnUiThread {
            val action = SISFragmentDirections.actionSISFragmentToSchoolMenuFragment(schoolName, true)
            NavHostFragment.findNavController(fragment).navigate(action)
        }
    }
    }


