package com.ibsu.ibsu.extensions

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ibsu.ibsu.R

fun Fragment.hideBottomNavigation() {
    val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
    bottomNavigationView.visibility = View.GONE
}

fun Fragment.showBackButton(){
    val activity = requireActivity()
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
}

fun Fragment.hideBackButton(){
    val activity = requireActivity()
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
}

fun Fragment.setActionBarName(name: String) {
    (activity as AppCompatActivity).supportActionBar?.title = HtmlCompat.fromHtml(
        "<font color='#FFFFFF'>$name</font>",
        HtmlCompat.FROM_HTML_MODE_LEGACY
    )
}