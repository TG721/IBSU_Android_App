package com.ibsu.ibsu.ui.element

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentIniBinding
import java.io.File


class IniFragment : Fragment() {
    private var binding: FragmentIniBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentIniBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWebView()
    }


    private fun setupWebView() {
        val webURL = "https://students.ibsu.edu.ge/"
        val webView = binding?.webView
        val myWebSettings = webView?.settings
        myWebSettings?.javaScriptEnabled = true
        myWebSettings?.userAgentString = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36"
        myWebSettings?.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        myWebSettings?.setDomStorageEnabled(true)
        webView?.setWebChromeClient(WebChromeClient())


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
                // Inject JavaScript code to add padding to the <body> element
                val script = "javascript:document.querySelector('.loginPage-wrapper').style.margin = '30px';"
                webView?.evaluateJavascript(script, null)
                // Delay hiding ProgressBar and overlay
                Handler(Looper.getMainLooper()).postDelayed({
                    binding?.progressBar?.visibility = View.GONE
                    binding?.overlay?.visibility = View.GONE
                }, 500) // Delay for 500 milliseconds (0.5 seconds)

                // Enable Cookies
                CookieManager.getInstance().flush()

            }
        }
                webView?.setDownloadListener { url, userAgent, contentDisposition, mimeType, contentLength ->
                    val request = DownloadManager.Request(Uri.parse(url))
                    request.setMimeType(mimeType)
                    request.setDescription("Downloading file...")
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.downloading),
                        Toast.LENGTH_SHORT
                    )
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
                                val columnIndex =
                                    cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
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
        fun newInstance() = IniFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.webView?.destroy()
        binding = null
    }
}