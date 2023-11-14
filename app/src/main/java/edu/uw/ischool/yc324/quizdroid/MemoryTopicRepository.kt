package edu.uw.ischool.yc324.quizdroid

class MemoryTopicRepository : TopicRepository {
    private val topics = listOf(
        Topic(
            id = 1,
            title = "Math",
            shortDescription = "Basic Math Questions",
            longDescription = "A set of questions covering basic mathematics.",
            quizzes = QuizData.quizzes["Math"]?.map { q ->
                Quiz(questionText = q.text, answers = q.options, correctAnswerIndex = q.correctAnswerIndex)
            } ?: emptyList(),
            iconResId = R.drawable.ic_math
        ),
        Topic(
            id = 2,
            title = "Physics",
            shortDescription = "Physics Questions",
            longDescription = "A set of questions covering fundamental physics.",
            quizzes = QuizData.quizzes["Physics"]?.map { q ->
                Quiz(questionText = q.text, answers = q.options, correctAnswerIndex = q.correctAnswerIndex)
            } ?: emptyList(),
            iconResId = R.drawable.ic_physics
        ),
        Topic(
            id = 3,
            title = "Marvel Super Heroes",
            shortDescription = "Super Hero Trivia",
            longDescription = "Test your knowledge about Marvel Super Heroes.",
            quizzes = QuizData.quizzes["Marvel Super Heroes"]?.map { q ->
                Quiz(questionText = q.text, answers = q.options, correctAnswerIndex = q.correctAnswerIndex)
            } ?: emptyList(),
            iconResId = R.drawable.ic_super_heroes
        )
    )

    override fun getTopics(): List<Topic> = topics

    override fun getQuizzesForTopic(topicId: Int): List<Quiz> {
        return topics.find { it.id == topicId }?.quizzes ?: emptyList()
    }
}
