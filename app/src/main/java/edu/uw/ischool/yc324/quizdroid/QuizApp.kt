package edu.uw.ischool.yc324.quizdroid

import android.app.Application
import android.util.Log


data class Quiz(val questionText: String, val answers: List<String>, val correctAnswerIndex: Int)

class QuizApp : Application() {
    lateinit var repository: TopicRepository
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        repository = MemoryTopicRepository()
        Log.d("QuizApp", "Application is starting")
    }

    companion object {
        lateinit var instance: QuizApp
            private set
    }
}