package edu.uw.ischool.yc324.quizdroid

import java.io.Serializable

data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int
) : Serializable

object QuizData {
    val quizzes = mapOf(
        "Math" to listOf(
            Question(
                text = "What is 2 + 2?",
                options = listOf("1", "2", "3", "4"),
                correctAnswerIndex = 3
            ),
            Question(
                text = "What is 7 * 6?",
                options = listOf("42", "52", "62", "72"),
                correctAnswerIndex = 0
            )
        ),
        "Physics" to listOf(
            Question(
                text = "What is the force of gravity on Earth?",
                options = listOf("9.8 m/s^2", "8.9 m/s^2", "10 m/s^2", "5 m/s^2"),
                correctAnswerIndex = 0
            ),
            Question(
                text = "What is the speed of light?",
                options = listOf("3x10^8 m/s", "2x10^8 m/s", "1x10^8 m/s", "4x10^8 m/s"),
                correctAnswerIndex = 0
            )
        ),
        "Marvel Super Heroes" to listOf(
            Question(
                text = "What is the version?",
                options = listOf("1", "2", "3", "4"),
                correctAnswerIndex = 1
            )
        )
    )
}
