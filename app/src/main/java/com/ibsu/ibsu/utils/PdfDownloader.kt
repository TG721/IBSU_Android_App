package com.ibsu.ibsu.utils


import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.ibsu.ibsu.R

object PdfDownloader {

    private var fileName = "pdf_file"
    fun setFileName(name: String) {
        fileName = name
    }

    private val DOWNLOAD_DIRECTORY = Environment.DIRECTORY_DOWNLOADS

    fun downloadPdfWithReceiver(context: Context, pdfUrl: String, view: View) {
        val request = DownloadManager.Request(Uri.parse(pdfUrl))
        request.setDescription(context.getString(R.string.downloading_pdf))
        request.setTitle(fileName)

        // Set the network types allowed for downloading
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)



        // Specify the destination directory and filename
        val fileName = "$fileName.pdf"
        request.setDestinationInExternalPublicDir(DOWNLOAD_DIRECTORY, fileName)

        // Get the DownloadManager
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        Toast.makeText(context, context.getString(R.string.downloading_pdf), Toast.LENGTH_SHORT)
            .show()
        // Enqueue the download request
        val downloadId = downloadManager.enqueue(request)

        // Register a BroadcastReceiver to listen for download completion
        val downloadReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == intent?.action) {
                    val query = DownloadManager.Query().setFilterById(downloadId)
                    val cursor: Cursor = downloadManager.query(query)

                    if (cursor.moveToFirst()) {
                        val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                        if (columnIndex >= 0) {
                            val status = cursor.getInt(columnIndex)

                            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                val columnIndexFileName =
                                    cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)
                                if (columnIndexFileName >= 0) {
                                    val downloadUri = cursor.getString(columnIndexFileName)
                                    val downloadedFile = Uri.parse(downloadUri).path
                                    // Handle the downloaded file here (e.g., open it, share it, etc.)
                                    val snackbar = Snackbar.make(
                                        view,
                                        "${context?.getString(R.string.downloaded)}: $downloadedFile",
                                        Snackbar.LENGTH_LONG
                                    )
                                    snackbar.setAction(context?.getString(R.string.go_to_downloads)) {
                                        // Handle button click here
                                        context?.startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS))
                                    }
                                    snackbar.show() // Display the Snackbar
                                } else {
                                    // Handle the case where the file name column is not found
                                }
                            } else if (status == DownloadManager.STATUS_FAILED) {
                                Toast.makeText(
                                    context,
                                    context?.getString(R.string.download_failed),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            // Handle the case where the status column is not found
                        }
                    }
                    cursor.close()

                    // Unregister the BroadcastReceiver
                    context?.unregisterReceiver(this)
                }
            }
        }

        // Register the BroadcastReceiver
        val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        context.registerReceiver(downloadReceiver, intentFilter)
    }
}
