package edu.uw.ischool.yc324.quizdroid

import androidx.annotation.DrawableRes

data class Topic(
    val id: Int,
    val title: String,
    val shortDescription: String,
    val longDescription: String,
    val quizzes: List<Quiz>,
    @DrawableRes val iconResId: Int
)

interface TopicRepository {
    fun getTopics(): List<Topic>
    fun getQuizzesForTopic(topicId: Int): List<Quiz>
}
