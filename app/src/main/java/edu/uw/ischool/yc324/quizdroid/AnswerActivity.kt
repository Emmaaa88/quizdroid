package edu.uw.ischool.yc324.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AnswerActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var score = 0
    private var totalCorrectAnswers = 0 // 答对的总数
    private lateinit var currentTopic: String
    private lateinit var questions: List<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        // Retrieve current question index, score, and topic from the intent
        currentQuestionIndex = intent.getIntExtra("QUESTION_INDEX", 0)
        score = intent.getIntExtra("SCORE", 0) // The score is received from the previous activity
        currentTopic = intent.getStringExtra("TOPIC_NAME") ?: "Math"

        // Get the questions for the current topic
        questions = QuizData.quizzes[currentTopic] ?: listOf()

        // Get the selected answer index from the intent
        val selectedAnswerIndex = intent.getIntExtra("SELECTED_ANSWER_INDEX", -1)
        val correctAnswerIndex = questions[currentQuestionIndex].correctAnswerIndex

        // Check if the answer is correct and update the score and total correct answers
        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
            totalCorrectAnswers++
        }

        // Update UI with the selected and correct answers
        findViewById<TextView>(R.id.yourAnswerTextView).text =
            questions[currentQuestionIndex].options[selectedAnswerIndex]
        findViewById<TextView>(R.id.correctAnswerTextView).text =
            questions[currentQuestionIndex].options[correctAnswerIndex]

        // Calculate and display the cumulative score and total questions
        val totalQuestions = questions.size
        val cumulativeScoreText = "You have $score out of $totalQuestions correct"
        findViewById<TextView>(R.id.scoreTextView).text = cumulativeScoreText

        // Setup the Next/Finish button
        val nextFinishButton = findViewById<Button>(R.id.nextFinishButton)
        setupButtonBehavior(nextFinishButton)
    }

    private fun setupButtonBehavior(button: Button) {
        if (currentQuestionIndex + 1 < questions.size) {
            button.text = getString(R.string.next)
            button.setOnClickListener {
                // Move to the next question
                val intent = Intent(this, QuestionActivity::class.java).apply {
                    putExtra("TOPIC_NAME", currentTopic)
                    putExtra("QUESTION_INDEX", currentQuestionIndex + 1)
                    putExtra("SCORE", score) // Pass the current score to the next question
                }
                startActivity(intent)
                finish()
            }
        } else {
            button.text = getString(R.string.finish)
            button.setOnClickListener {
                // Finish the activity and return to the main topic list
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
