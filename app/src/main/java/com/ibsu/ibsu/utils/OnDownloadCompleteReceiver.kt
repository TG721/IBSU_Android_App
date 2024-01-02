package com.ibsu.ibsu.utils

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.ibsu.ibsu.R
import java.io.File

class OnDownloadCompleteReceiver(
    private val downloadId: Long,
    private val downloadManager: DownloadManager,
    private val layout: View?,
) : BroadcastReceiver() {
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
                        layout!!,
                        context!!.getString(R.string.downloaded),
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.setAction(context!!.getString(R.string.go_to_downloads)) {
                        // Handle button click here
                        context.startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
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