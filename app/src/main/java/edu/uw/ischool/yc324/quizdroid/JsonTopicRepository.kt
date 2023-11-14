package edu.uw.ischool.yc324.quizdroid

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL

class JsonTopicRepository : TopicRepository {
    private var topics: List<Topic>? = null

    init {
        // Placeholder for actual JSON loading logic
        val jsonUrl = "http://tednewardsandbox.site44.com/questions.json" // This URL is hardcoded for now
        val jsonText = URL(jsonUrl).readText()
        val gson = Gson()
        topics = gson.fromJson(jsonText, object : TypeToken<List<Topic>>() {}.type)
    }

    override fun getTopics(): List<Topic> {
        return topics ?: emptyList()
    }

    override fun getQuizzesForTopic(topicId: Int): List<Quiz> {
        return topics?.find { it.id == topicId }?.quizzes ?: emptyList()
    }
}
