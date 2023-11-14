package edu.uw.ischool.yc324.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.view.View


class QuestionActivity : AppCompatActivity() {
    private var currentQuestionIndex = 0
    private lateinit var questions: List<Quiz>
    private var topicId: Int = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        // Assuming that the topic ID is passed as an intent extra
        topicId = intent.getIntExtra("TOPIC_ID", -1)
        if (topicId == -1) finish() // If there's no valid topic ID, end the activity

        // Use the repository to fetch questions for the topic
        questions = QuizApp.instance.repository.getQuizzesForTopic(topicId)
        currentQuestionIndex = intent.getIntExtra("QUESTION_INDEX", 0)

        displayQuestion(currentQuestionIndex)

        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)
        val submitButton: Button = findViewById(R.id.submitButton)

        radioGroup.setOnCheckedChangeListener { _, _ ->
            submitButton.visibility = View.VISIBLE
        }

        submitButton.setOnClickListener {
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                val radioButton: RadioButton = findViewById(selectedRadioButtonId)
                val selectedOptionIndex = radioGroup.indexOfChild(radioButton)
                submitAnswer(selectedOptionIndex)
            } else {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
            }
        }

        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            if (currentQuestionIndex > 0) {
                // Navigate to the previous question
                navigateToQuestion(currentQuestionIndex - 1)
            } else {
                // Return to the topic list
                finish()
            }
        }
    }

    private fun displayQuestion(questionIndex: Int) {
        val question = questions[questionIndex].questionText
        findViewById<TextView>(R.id.questionTextView).text = question

        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)
        radioGroup.clearCheck()
        val answers = questions[questionIndex].answers
        radioGroup.findViewById<RadioButton>(R.id.option1).text = answers[0]
        radioGroup.findViewById<RadioButton>(R.id.option2).text = answers[1]
        radioGroup.findViewById<RadioButton>(R.id.option3).text = answers[2]
        radioGroup.findViewById<RadioButton>(R.id.option4).text = answers[3]
    }

    private fun submitAnswer(selectedOptionIndex: Int) {
        val correctAnswerIndex = questions[currentQuestionIndex].correctAnswerIndex
        if (selectedOptionIndex == correctAnswerIndex) {
            score++
        }

        navigateToAnswer(selectedOptionIndex)
    }

    private fun navigateToQuestion(questionIndex: Int) {
        val intent = Intent(this, QuestionActivity::class.java).apply {
            putExtra("TOPIC_ID", topicId)
            putExtra("QUESTION_INDEX", questionIndex)
            putExtra("SCORE", score) // Pass along the current score
        }
        startActivity(intent)
        finish()
    }

    private fun navigateToAnswer(selectedOptionIndex: Int) {
        val intent = Intent(this, AnswerActivity::class.java).apply {
            putExtra("SELECTED_ANSWER_INDEX", selectedOptionIndex)
            putExtra("QUESTION_INDEX", currentQuestionIndex)
            putExtra("TOPIC_ID", topicId)
            putExtra("SCORE", score)
        }
        startActivity(intent)
        finish()
    }
}
