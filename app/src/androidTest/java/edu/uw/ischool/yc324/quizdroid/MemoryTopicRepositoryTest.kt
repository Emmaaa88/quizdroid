package edu.uw.ischool.yc324.quizdroid

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MemoryTopicRepositoryTest {

    private lateinit var repository: MemoryTopicRepository

    @Before
    fun setUp() {
        repository = MemoryTopicRepository()
    }

    @Test
    fun getTopics_ReturnsCorrectTopicList() {
        val topics = repository.getTopics()
        assertNotNull("Topic list is null", topics)
        assertTrue("Topic list is empty", topics.isNotEmpty())

        val expectedTitle = "Math"
        val topic = topics.find { it.title == expectedTitle }
        assertNotNull("Topic with title $expectedTitle not found", topic)
    }

    @Test
    fun getQuizzesForTopic_ReturnsCorrectQuizzes() {
        val topicId = 1
        val quizzes = repository.getQuizzesForTopic(topicId)
        assertNotNull("Quizzes list is null", quizzes)
        assertTrue("Quizzes list for topic ID $topicId is empty", quizzes.isNotEmpty())

        val expectedQuestionText = "What is 2 + 2?"
        val quiz = quizzes.find { it.questionText == expectedQuestionText }
        assertNotNull("Quiz with question text $expectedQuestionText not found", quiz)
    }
}
