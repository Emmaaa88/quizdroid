package edu.uw.ischool.yc324.quizdroid

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL
import android.util.Log
import com.google.gson.JsonSyntaxException
import java.io.File


data class Topic(
    val title: String,
    val desc: String,
    val questions: List<Question>
)

data class Question(
    val text: String,
    val answer: String,
    val answers: List<String>
)

data class Quiz(
    val questionText: String,
    val answers: List<String>,
    val correctAnswerIndex: Int
)

class JsonTopicRepository(context: Context, private val gson: Gson) : TopicRepository {
    private val _topics: List<Topic> by lazy {
        loadTopics(context)
    }

    private fun loadTopics(context: Context): List<Topic> {
        val jsonFile = File(context.filesDir, "questions.json")
        if (!jsonFile.exists()) {
            Log.e("JsonTopicRepository", "JSON file not found")
            return emptyList()
        }

        return try {
            val jsonText = jsonFile.readText()
            gson.fromJson(jsonText, object : TypeToken<List<Topic>>() {}.type)
        } catch (e: JsonSyntaxException) {
            Log.e("JsonTopicRepository", "Error parsing JSON", e)
            emptyList()
        }
    }

    override fun getTopics(): List<Topic> {
        return _topics
    }

    override fun getQuizzesForTopic(topicTitle: String): List<Quiz> {
        val topic = _topics.find { it.title == topicTitle }
        return topic?.questions?.map { question ->
            Quiz(question.text, question.answers, question.answer.toInt() - 1)
        } ?: emptyList()
    }
}
