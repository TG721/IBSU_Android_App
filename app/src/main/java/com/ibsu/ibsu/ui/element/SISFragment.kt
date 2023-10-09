package com.ibsu.ibsu.ui.element

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentSISBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.ui.viewmodel.SchoolViewModel
import com.ibsu.ibsu.utils.JavaScriptInterfaceForSIS
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale
import com.ibsu.ibsu.utils.WeekValue
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


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

        val programText = getString(R.string.program)

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
                binding?.progressBar?.visibility = View.VISIBLE
                binding?.overlay?.visibility = View.VISIBLE

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                val appSettingPrefs: SharedPreferences =
                    requireActivity().getSharedPreferences("appSettingPrefs", Context.MODE_PRIVATE)
//                d("urlld", url.toString())
                if(isFirstLoad){
                    if(requireContext().getCurrentLocale(requireContext()).language==georgianLocale){
                        view?.evaluateJavascript(
                            """
    function changeToKA() {

     
        var elementToClick = document.getElementById('languageGeorgian');
        
        // Create a click event
        var clickEvent = new MouseEvent('click', {
            bubbles: true,
            cancelable: true,
            view: window
        });
        
        // Dispatch the click event on the element
        elementToClick.dispatchEvent(clickEvent);
    }

    // Call the function to change the colors when needed
    changeToKA();
                                """, null
                        )
                    } else {
                        view?.evaluateJavascript(
                            """
    function changeToEn() {

     
        var elementToClickEn = document.getElementById('languageEnglish');
        
        // Create a click event
        var clickEventEn = new MouseEvent('click', {
            bubbles: true,
            cancelable: true,
            view: window
        });
        
        // Dispatch the click event on the element
        elementToClickEn.dispatchEvent(clickEventEn);
    }

    // Call the function to change the colors when needed
    changeToEn();
                                """, null
                        )
                    }
                }
                    isFirstLoad = false

                //setup dark mode

//                d("snnnnnnn", appSettingPrefs.getInt("darkModeVal", 2).toString())
                if (appSettingPrefs.getInt("darkModeVal", 2) == 0) {
//                    if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
//
//                    } else {
                    view?.evaluateJavascript(
                        """
    // Function to change the background color and text color of the body and div elements
    function changeColors() {
      // Change the body background color to black and text color to white
      document.body.style.background = 'black';
   


      // Get all div elements on the page
      const divElements = document.querySelectorAll('div');

      // Loop through each div element
      divElements.forEach((div) => {
        // Check if the div has the class "navbar-sis" or is a child of a div with that class
        if (!div.classList.contains('navbar-sis') && !div.closest('.navbar-sis')) {
          // If it doesn't have the class or is not a child, change its background color to black and text color to white
          div.style.background = 'black';
          div.style.color = 'white';
        }
      });
      
    }

    // Call the function to change the colors when needed
    changeColors();
                        """, null
                    )

                    if (!url!!.contains("requests") && url.length > 40 ) {

                        view?.evaluateJavascript(
                            """
            function changeColors2() {
        
        
            // Get all td elements on the page
            var tdElements = document.querySelectorAll('td');
            // Loop through each td element and change its background color
            tdElements.forEach(function (td) {
                td.style.background = '#203f01';
            });
        
            // Get all th elements on the page
            var thElements = document.querySelectorAll('th');
        
            // Loop through each th element and change its background color
            thElements.forEach(function (th) {
                th.style.backgroundColor = '#20672d';
            });
        
            }
        
            // Call the function to change the colors when needed
            changeColors2();
                """, null
                        )
                    } else {
                        view?.evaluateJavascript(
                            """
    function changeColors2() {


    // Get all td elements on the page
    var tdElements = document.querySelectorAll('td');
    // Loop through each td element and change its background color
    tdElements.forEach(function (td) {
        td.style.color = 'black';
    });


    }

    // Call the function to change the colors when needed
    changeColors2();
""", null
                        )
                    }
                }


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
                            // code to remove the 'div' element with class name 'overlay'
                            "var overlay = document.querySelector('.overlay');" +
                            "if (overlay) {" +
                            "  overlay.remove();" +
                            "}" +
                            //script for school
                            "var emElements = document.getElementsByTagName('em');" +
                            "for (var i = 0; i < emElements.length; i++) {" +
                            "  if (emElements[i].textContent === 'School' || emElements[i].textContent === 'სკოლა') {" +
                            "    emElements[i].style.display='none';" +
                            "    var nextNode = emElements[i].nextSibling;" +
                            "    while (nextNode && nextNode.nodeType !== 1) {" +
                            "      nextNode = nextNode.nextSibling;" +
                            "    }" +
                            "    if (nextNode && nextNode.tagName === 'STRONG') {" +
                            "      nextNode.style.display='inline-block';" +
                            "      nextNode.style.width='100%';" +
                            "      var siblingText = nextNode.textContent.toUpperCase();" +
                            "      var schoolValue = ''; " +
                            "      if (siblingText.includes('COMPUTER SCIENCE AND ARCHITECTURE') || siblingText.includes('კომპიუტერული მეცნიერებისა და არქიტექტურის'.toUpperCase())) {" +
                            "        nextNode.style.background = '#1E73BE';" +
                            "        schoolValue = 'COMPUTER SCIENCE AND ARCHITECTURE'" +
                            "      } else if (siblingText.includes('BUSINESS') || siblingText.includes('ბიზნესის'.toUpperCase())){" +
                            "        nextNode.style.background = '#1D5822';" +
                            "        schoolValue = 'BUSINESS'" +
                            "      } else if (siblingText.includes('EDUCATION') || siblingText.includes('HUMANITIES AND SOCIAL SCIENCES') || siblingText.includes('განათლების, ჰუმანიტარული და სოციალური მეცნიერებების'.toUpperCase())) {" +
                            "        nextNode.style.background = '#F4A83B';" +
                            "        schoolValue = 'EDUCATION, HUMANITIES AND SOCIAL SCIENCES'" +
                            "      } else if (siblingText.includes('LAW AND STATE GOVERNANCE') || siblingText.includes('სამართლისა და სახელმწიფო მმართველობის'.toUpperCase())) {" +
                            "        nextNode.style.background = '#6E0000';" +
                            "        schoolValue = 'LAW AND STATE GOVERNANCE'" +
                            "      }" +
                            "        nextNode.style.margin = '0px 0px 4px 0px';" +
                            "        nextNode.style.color = 'white';" +
                            "        nextNode.style.borderRadius = '5px';" +
                            "        nextNode.style.padding = '2px';" +
                            // Apply click effect to nextNode
                            "nextNode.style.transition = 'background-color 0.3s';" +
                            "nextNode.addEventListener('click', function() {" +
                            "  nextNode.style.backgroundColor = 'lightgray';" + // Apply the click effect
                            "  setTimeout(function() {" +
                            "    nextNode.style.backgroundColor = 'transparent';" + // Remove the click effect after a delay
                            "    AndroidFunction.passValueToKotlin('School of ' + schoolValue);" +
                            "  }, 300);" + // 300 milliseconds (adjust as needed)
                            "});" +

                            "      var nextSibling = nextNode.nextSibling;" +
                            "      while (nextSibling && nextSibling.nodeType !== 1) {" +
                            "        nextSibling = nextSibling.nextSibling;" +
                            "      }" +
                            "      if (nextSibling && nextSibling.tagName === 'A') {" +
                            "        nextSibling.style.pointerEvents = 'none';" +
                            "        nextSibling.parentNode.removeChild(nextSibling);" +
                            "      }" +
                            "      break;" +
                            "    }" +
                            "  }" +
                            " }"
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
                // Delay hiding ProgressBar and overlay
                Handler(Looper.getMainLooper()).postDelayed({
                    binding?.progressBar?.visibility = View.GONE
                    binding?.overlay?.visibility = View.GONE
                }, 500) // Delay for 500 milliseconds (0.5 seconds)
            }
        }

        webView?.setDownloadListener { url, userAgent, contentDisposition, mimeType, contentLength ->
            val request = DownloadManager.Request(Uri.parse(url))
            request.setMimeType(mimeType)
            request.setDescription("Downloading file...")
            Toast.makeText(requireContext(), getString(R.string.downloading), Toast.LENGTH_SHORT)
                .show()
            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType))

            val downloadManager =
                requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadId = downloadManager.enqueue(request)

            // Listen for download completion using a BroadcastReceiver
            val onComplete = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    val query = DownloadManager.Query()
                    query.setFilterById(downloadId)
                    val cursor = downloadManager.query(query)
                    if (cursor.moveToFirst()) {
                        val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                        val status = cursor.getInt(columnIndex)
                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            val columnIndexFileName =
                                cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)
                            if (columnIndexFileName >= 0) {
                                val localFilePath = cursor.getString(columnIndexFileName)
                                val file = File(localFilePath)
                                // Handle the downloaded file (e.g., open it, share it, etc.)
                                val snackbar = Snackbar.make(
                                    binding?.root!!,
                                    getString(R.string.downloaded),
                                    Snackbar.LENGTH_LONG
                                )
                                snackbar.setAction(getString(R.string.go_to_downloads)) {
                                    // Handle button click here
                                    startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
                                }
                                snackbar.show()
                            } else {

                            }
                        }
                    }
                    cursor.close()

                    // Unregister the BroadcastReceiver
                    context?.unregisterReceiver(this)
                }
            }

            val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            requireContext().registerReceiver(onComplete, filter)
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