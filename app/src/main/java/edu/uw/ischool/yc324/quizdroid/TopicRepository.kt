package edu.uw.ischool.yc324.quizdroid

import androidx.annotation.DrawableRes


interface TopicRepository {
    fun getTopics(): List<Topic>
    fun getQuizzesForTopic(topicId: Int): List<Quiz>
}
