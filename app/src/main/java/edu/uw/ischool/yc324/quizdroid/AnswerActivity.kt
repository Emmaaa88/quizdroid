package edu.uw.ischool.yc324.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AnswerActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var score = 0
    private var totalCorrectAnswers = 0 // Total number of correct answers
    private lateinit var questions: List<Quiz>
    private var topicId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        // Retrieve the topic ID, current question index, and score from the intent
        currentQuestionIndex = intent.getIntExtra("QUESTION_INDEX", 0)
        score = intent.getIntExtra("SCORE", 0) // The score is received from the previous activity
        topicId = intent.getIntExtra("TOPIC_ID", -1)

        // If there is no valid topic ID, we return to MainActivity
        if (topicId == -1) {
            finish()
            return
        }

        // Fetch the questions using the repository
        questions = QuizApp.instance.repository.getQuizzesForTopic(topicId)

        // Get the selected answer index from the intent
        val selectedAnswerIndex = intent.getIntExtra("SELECTED_ANSWER_INDEX", -1)
        val correctAnswerIndex = questions[currentQuestionIndex].correctAnswerIndex

        // Check if the answer is correct and update the score and total correct answers
        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
            totalCorrectAnswers++
        }

        // Update UI with the selected and correct answers
        findViewById<TextView>(R.id.yourAnswerTextView).text = questions[currentQuestionIndex].answers[selectedAnswerIndex]
        findViewById<TextView>(R.id.correctAnswerTextView).text = questions[currentQuestionIndex].answers[correctAnswerIndex]

        // Calculate and display the cumulative score and total questions
        val totalQuestions = questions.size
        val cumulativeScoreText = "You have $score out of $totalQuestions correct"
        findViewById<TextView>(R.id.scoreTextView).text = cumulativeScoreText

        // Setup the Next/Finish button
        val nextFinishButton: Button = findViewById(R.id.nextFinishButton)
        setupButtonBehavior(nextFinishButton)
    }

    private fun setupButtonBehavior(button: Button) {
        if (currentQuestionIndex + 1 < questions.size) {
            button.text = getString(R.string.next)
            button.setOnClickListener {
                // Move to the next question
                val intent = Intent(this, QuestionActivity::class.java).apply {
                    putExtra("TOPIC_ID", topicId)
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
