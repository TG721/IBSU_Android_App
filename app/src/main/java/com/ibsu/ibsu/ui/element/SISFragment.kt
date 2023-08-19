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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentSISBinding
import com.ibsu.ibsu.utils.JavaScriptInterfaceForSIS
import com.ibsu.ibsu.utils.WeekValue
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SISFragment : Fragment() {
    private var isFirstLoad = true
    private var binding: FragmentSISBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSISBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWebView()
        showBottomMenu()
    }

    private fun setupWebView() {
        val webURL = "https://sis.ibsu.edu.ge/"
        val webView = binding?.webView
        val myWebSettings = webView?.settings
        myWebSettings?.javaScriptEnabled = true

        // Add JavaScript interface
        webView?.addJavascriptInterface(JavaScriptInterfaceForSIS(this), "AndroidFunction")

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
                view?.evaluateJavascript(
                    "(function() {" +
                            "var dropdownMenus = document.getElementsByClassName('dropdown-menu');" +
                            "if (dropdownMenus.length > 0) {" +
                            "  var lastDropdownMenu = dropdownMenus[0];" +
                            "  var lastChild = lastDropdownMenu.lastElementChild;" +
                            "  if (lastChild && lastChild.tagName.toLowerCase() === 'li') {" +
                            "    lastChild.remove();" +
                            "  }" +
                            "}" +
                            "})();"
                    +
                            //script for school
                            "var emElements = document.getElementsByTagName('em');" +
                            "for (var i = 0; i < emElements.length; i++) {" +
                            "  if (emElements[i].textContent === 'School') {" +
                            "    var nextNode = emElements[i].nextSibling;" +
                            "    while (nextNode && nextNode.nodeType !== 1) {" +
                            "      nextNode = nextNode.nextSibling;" +
                            "    }" +
                            "    if (nextNode && nextNode.tagName === 'STRONG') {" +
                            "      var siblingText = nextNode.textContent.toUpperCase();" +
                            "      if (siblingText.includes('COMPUTER SCIENCE AND ARCHITECTURE')) {" +
                            "        nextNode.style.transition = 'color 1s';" +
                            "        nextNode.style.color = '#1E73BE';" +
                            "      } else if (siblingText.includes('BUSINESS')) {" +
                            "        nextNode.style.transition = 'color 1s';" +
                            "        nextNode.style.color = '#1D5822';" +
                            "      } else if (siblingText.includes('EDUCATION') || siblingText.includes('HUMANITIES AND SOCIAL SCIENCES')) {" +
                            "        nextNode.style.transition = 'color 1s';" +
                            "        nextNode.style.color = '#F4A83B';" +
                            "      } else if (siblingText.includes('LAW AND STATE GOVERNANCE')) {" +
                            "        nextNode.style.transition = 'color 1s';" +
                            "        nextNode.style.color = '#6E0000';" +
                            "      }" +
                            "      nextNode.addEventListener('click', function() {" +
                            "        AndroidFunction.passValueToKotlin(siblingText);" +
                            "      });" +
                            "      var nextSibling = nextNode.nextSibling;" +
                            "      while (nextSibling && nextSibling.nodeType !== 1) {" +
                            "        nextSibling = nextSibling.nextSibling;" +
                            "      }" +
                            "      if (nextSibling && nextSibling.tagName === 'A') {" +
                            "        nextSibling.style.pointerEvents = 'none';" +
                            "        nextSibling.style.opacity = '0.7';" +
                            "      }" +
                            "      break;" +
                            "    }" +
                            "  }" +
                            "}"
                    ,
                    null
                )

                //

                // Change background color to blue and text color to white for all child elements of the element with ID "navbar-sis"
                val ibsuColorInt = ContextCompat.getColor(requireContext(), R.color.ibsu)
                val ibsuColorString = String.format("#%06X", 0xFFFFFF and ibsuColorInt)
                view?.loadUrl(
                    "javascript:(function() { " +
                            "var parentElement = document.getElementById('navbar-sis'); " +
                            "if (parentElement) {" +
                            "    parentElement.style.backgroundColor = '${ibsuColorString}';" +
                            "    var childElements = parentElement.getElementsByTagName('*'); " +
                            "    for (var i = 0; i < childElements.length; i++) {" +
                            "        childElements[i].style.color = 'white';" +
                            "    }" +
                            "}" +
                            "})()"
                )
            }
        }

        webView?.loadUrl(webURL)
    }

    companion object {

        @JvmStatic
        fun newInstance() = SISFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.webView?.destroy()
        binding = null
    }
    private fun showBottomMenu() {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun setupActionBar() {
        if (WeekValue.weekValue != null) {
            (activity as AppCompatActivity).supportActionBar?.title = HtmlCompat.fromHtml(
                "<font color='#FFFFFF'>IBSU - ${WeekValue.weekValue}</font>",
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
        val activity = requireActivity()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }

    override fun onResume() {
        super.onResume()

        setupActionBar()
    }



}