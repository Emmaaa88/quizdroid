package edu.uw.ischool.yc324.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AnswerActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questions: List<Quiz>
    private var topicTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        topicTitle = intent.getStringExtra("TOPIC_TITLE")
        currentQuestionIndex = intent.getIntExtra("QUESTION_INDEX", 0)
        score = intent.getIntExtra("SCORE", 0) // The score is received from the previous activity

        if (topicTitle == null) {
            finish()
            return
        }

        questions = QuizApp.instance.repository.getQuizzesForTopic(topicTitle!!)

        val selectedAnswerIndex = intent.getIntExtra("SELECTED_ANSWER_INDEX", -1)
        val correctAnswerIndex = questions[currentQuestionIndex].correctAnswerIndex

        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
        }

        findViewById<TextView>(R.id.yourAnswerTextView).text = questions[currentQuestionIndex].answers[selectedAnswerIndex]
        findViewById<TextView>(R.id.correctAnswerTextView).text = questions[currentQuestionIndex].answers[correctAnswerIndex]

        val totalQuestions = questions.size
        val cumulativeScoreText = "You have $score out of $totalQuestions correct"
        findViewById<TextView>(R.id.scoreTextView).text = cumulativeScoreText

        val nextFinishButton: Button = findViewById(R.id.nextFinishButton)
        setupButtonBehavior(nextFinishButton)
    }

    private fun setupButtonBehavior(button: Button) {
        if (currentQuestionIndex + 1 < questions.size) {
            button.text = getString(R.string.next)
            button.setOnClickListener {
                // Navigate to the next question
                val intent = Intent(this, QuestionActivity::class.java).apply {
                    putExtra("TOPIC_TITLE", topicTitle)
                    putExtra("QUESTION_INDEX", currentQuestionIndex + 1)
                    putExtra("SCORE", score)
                }
                startActivity(intent)
                finish()
            }
        } else {
            button.text = getString(R.string.finish)
            button.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
