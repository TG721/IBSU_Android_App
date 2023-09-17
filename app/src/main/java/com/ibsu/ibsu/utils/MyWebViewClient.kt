package com.ibsu.ibsu.utils

import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.webkit.CookieManager
import android.webkit.DownloadListener
import android.webkit.MimeTypeMap
import android.webkit.URLUtil
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import okhttp3.internal.userAgent

open class MyWebViewClient(
    private val context: Context,
    private val webURL: String
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url.toString()
        return if (!url.startsWith(webURL)) {
            // Open the URL in an external browser
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            view?.context?.startActivity(intent)
            true
        } else {
            // Check if the URL points to a downloadable file
            if (isDownloadable(request?.url)) {
                // Handle the download link
                startDownload(view, request?.url)
                true // The WebView has handled the URL
            } else {
                // Load regular URLs in the WebView
                view?.loadUrl(url)
                true // The WebView has handled the URL
            }
        }
    }

    private fun isDownloadable(url: Uri?): Boolean {
        // Check if the URL's file extension suggests it's a downloadable file
        val extension = MimeTypeMap.getFileExtensionFromUrl(url.toString())
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension?.lowercase())

        return mimeType != null && (mimeType.startsWith("application/") || mimeType == "application/pdf")
    }

    private fun startDownload(view: WebView?, url: Uri?) {
        try {
            // Check if it's a PDF file
            val extension = MimeTypeMap.getFileExtensionFromUrl(url.toString())
            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension?.lowercase())

            if (mimeType == "application/pdf") {
                // If it's a PDF, initiate the download
                val request = DownloadManager.Request(url)
                request.setMimeType(mimeType)
                val cookies = CookieManager.getInstance().getCookie(url.toString())
                request.addRequestHeader("cookie", cookies)
                request.addRequestHeader("User-Agent", userAgent)
                request.setDescription("Downloading PDF")
                request.setTitle(URLUtil.guessFileName(url.toString(), null, mimeType))
                request.allowScanningByMediaScanner()
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    URLUtil.guessFileName(url.toString(), null, mimeType)
                )

                val downloadManager =
                    context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                downloadManager.enqueue(request)

                showToast("Starting PDF Download")
            } else {
                // If it's not a PDF, open in the default browser
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = url
                view?.context?.startActivity(intent)
                showToast("Opening Link in Browser")
            }
        } catch (e: ActivityNotFoundException) {
            showToast("Error: Download cannot be started")
        }
    }

    // Utility function to display a Toast message
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    // DownloadListener to handle download completion
    private val downloadListener = DownloadListener { url, _, _, _, _ ->
        // Show a "Downloaded" toast message when the download is complete
        showToast("Downloaded")
    }

    fun getDownloadListener(): DownloadListener {
        return downloadListener
    }
}
