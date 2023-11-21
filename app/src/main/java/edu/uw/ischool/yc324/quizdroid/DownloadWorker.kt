package edu.uw.ischool.yc324.quizdroid

import android.content.Context
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException

class DownloadWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        if (!isOnline()) {
            showToast("No internet connection, will retry later.")
            return Result.retry()
        }

        val sharedPref = applicationContext.getSharedPreferences("YourPreferenceName", Context.MODE_PRIVATE)
        val url = sharedPref.getString("questionsUrlKey", "defaultUrl")

        showToast("Downloading from $url")

        makeBackup()

        return try {
            if (downloadFile(url)) {
                Result.success()
            } else {
                restoreBackup()
                Result.retry()
            }
        } catch (e: Exception) {
            restoreBackup()
            Result.failure()
        }
    }

    private fun showToast(text: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
        }
    }

    private fun isOnline(): Boolean {
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnected ?: false
    }

    private fun downloadFile(urlString: String?): Boolean {
        if (urlString.isNullOrEmpty()) {
            showToast("URL is null or empty")
            return false
        }

        val client = OkHttpClient()
        val request = Request.Builder().url(urlString).build()

        return try {
            val response = client.newCall(request).execute()

            if (!response.isSuccessful) {
                showToast("Failed to download file: ${response.message}")
                return false
            }

            val downloadedData = response.body?.string()
            saveToFile(downloadedData)
        } catch (e: IOException) {
            showToast("IOException during file download: ${e.message}")
            false
        }
    }

    private fun saveToFile(data: String?): Boolean {
        if (data.isNullOrEmpty()) {
            showToast("Downloaded data is empty or null")
            return false
        }

        return try {
            val file = File(applicationContext.filesDir, "questions.json")
            file.writeText(data)
            true
        } catch (e: Exception) {
            showToast("Failed to save file: ${e.message}")
            false
        }
    }

    private fun makeBackup() {
        val file = File(applicationContext.filesDir, "questions.json")
        if (file.exists()) {
            file.copyTo(File(file.parentFile, "questions_backup.json"), overwrite = true)
        }
    }

    private fun restoreBackup() {
        val backupFile = File(applicationContext.filesDir, "questions_backup.json")
        if (backupFile.exists()) {
            backupFile.copyTo(File(applicationContext.filesDir, "questions.json"), overwrite = true)
        }
    }
}
