package edu.uw.ischool.yc324.quizdroid

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import android.content.Context


data class Question(
    val text: String,
    val answer: String,
    val answers: List<String>
)

data class Topic(
    val title: String,
    val desc: String,
    val questions: List<Question>
)

class JsonTopicRepository(context: Context) : TopicRepository {
    private var topics: List<Topic>? = null

    init {
        val jsonFile = File(context.filesDir, "questions.json")
        if (jsonFile.exists()) {
            val jsonText = jsonFile.readText()
            val gson = Gson()
            topics = gson.fromJson(jsonText, object : TypeToken<List<Topic>>() {}.type)
        }
    }

    override fun getTopics(): List<Topic> {
        return topics ?: emptyList()
    }

    override fun getQuizzesForTopic(topicId: Int): List<Quiz> {
        return emptyList()
    }
}
