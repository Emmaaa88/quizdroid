package edu.uw.ischool.yc324.quizdroid


interface TopicRepository {
    fun getTopics(): List<Topic>
    fun getQuizzesForTopic(topicId: String): List<Quiz>
}
