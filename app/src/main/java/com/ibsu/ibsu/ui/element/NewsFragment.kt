package com.ibsu.ibsu.ui.element

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ibsu.ibsu.databinding.FragmentNewsBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment :
    BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate) {
    override fun setup() {
    }

}