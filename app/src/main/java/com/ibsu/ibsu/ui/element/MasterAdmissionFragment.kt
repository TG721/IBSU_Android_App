package com.ibsu.ibsu.ui.element

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.ibsu.ibsu.databinding.FragmentMasterAdmissionBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MasterAdmissionFragment : Fragment() {
    private var isFirstLoad = true
    private var binding: FragmentMasterAdmissionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMasterAdmissionBinding.inflate(inflater)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWebView()
    }

    private fun setupWebView() {
        var webURL: String
        if (requireContext().getCurrentLocale(requireContext()).language == georgianLocale)
            webURL = "https://ibsu.edu.ge/ge/masters/#1676283131963-b3849189-54ad"
        else webURL = "https://ibsu.edu.ge/en/masters/#1676283131963-b3849189-54ad"
        val webView = binding?.webView
        val myWebSettings = webView?.settings
        myWebSettings?.javaScriptEnabled = true
        webView?.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        // Enable Cookies
        CookieManager.getInstance().setAcceptCookie(true)

        webView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest,
            ): Boolean {
                val url = request.url.toString()
                return if (!url.startsWith(webURL)) {
                    // Open the URL in an external browser
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    view.context.startActivity(intent)
                    true
                } else {
                    false
                }
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (isFirstLoad) {
//                    binding?.progressBar?.visibility = View.GONE
                    isFirstLoad = false
                }
                binding?.progressBar?.visibility = View.GONE

                // Enable Cookies
                CookieManager.getInstance().flush()


                val jsCode = """
            var elementsToRemove = document.querySelectorAll(".edgt-title-inner, .edgt-mobile-header, .edgt-title-holder, .vc_tta-panel-heading, .pulse, .edgt-title-holder.edgt-centered-type.edgt-has-bg-image, .edgt-page-footer, .fb_reset");
            for (var i = 0; i < elementsToRemove.length; i++) {
                var element = elementsToRemove[i];
                element.parentNode.removeChild(element);
            }
        """

                view?.evaluateJavascript(jsCode) { result ->
                    binding?.webView?.visibility = View.VISIBLE
                }


            }
        }

        webView?.loadUrl(webURL)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.webView?.destroy()
        binding = null
    }
}