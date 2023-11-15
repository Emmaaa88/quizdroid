package edu.uw.ischool.yc324.quizdroid

import android.app.Application
import android.util.Log
import com.google.gson.Gson


class QuizApp : Application() {
    lateinit var repository: TopicRepository

    override fun onCreate() {
        super.onCreate()
        instance = this
        // 初始化 Gson 对象
        val gson = Gson()
        // 使用 Gson 对象创建 JsonTopicRepository 实例
        repository = JsonTopicRepository(this, gson)
        Log.d("QuizApp", "Application is starting")
    }

    companion object {
        lateinit var instance: QuizApp
            private set
    }
}
