package edu.uw.ischool.yc324.quizdroid

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class QuizApp : Application() {
    lateinit var repository: TopicRepository

    override fun onCreate() {
        super.onCreate()
        instance = this
        val gson = Gson()
        repository = JsonTopicRepository(this, gson)
        Log.d("QuizApp", "Application is starting")
    }

    companion object {
        lateinit var instance: QuizApp
            private set
    }

    fun scheduleDownloadWorker() {
        val downloadRequest = PeriodicWorkRequestBuilder<DownloadWorker>(15, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this).enqueue(downloadRequest)
    }
}
