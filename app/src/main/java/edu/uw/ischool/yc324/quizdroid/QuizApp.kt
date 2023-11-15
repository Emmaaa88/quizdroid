package edu.uw.ischool.yc324.quizdroid

import android.app.Application
import android.util.Log
import com.google.gson.Gson

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
}
