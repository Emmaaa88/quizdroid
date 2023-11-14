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
    private lateinit var questions: List<Question>
    private lateinit var topicName: String
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        topicName = intent.getStringExtra("TOPIC_NAME") ?: return
        questions = QuizData.quizzes[topicName] ?: return
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

        // Handle the "back" button to navigate to the previous question or topic list
        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            if (currentQuestionIndex > 0) {
                // If not the first question, go to the previous question
                val intent = Intent(this, QuestionActivity::class.java).apply {
                    putExtra("TOPIC_NAME", topicName)
                    putExtra("QUESTION_INDEX", currentQuestionIndex - 1)
                }
                startActivity(intent)
                finish()
            } else {
                // If the first question, return to the topic list
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun displayQuestion(questionIndex: Int) {
        val question = questions[questionIndex]
        findViewById<TextView>(R.id.questionTextView).text = question.text

        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)
        radioGroup.clearCheck()
        radioGroup.findViewById<RadioButton>(R.id.option1).text = question.options[0]
        radioGroup.findViewById<RadioButton>(R.id.option2).text = question.options[1]
        radioGroup.findViewById<RadioButton>(R.id.option3).text = question.options[2]
        radioGroup.findViewById<RadioButton>(R.id.option4).text = question.options[3]
    }

    // When submitting an answer, check if it's correct and increment the score
    private fun submitAnswer(selectedOptionIndex: Int) {
        val correctAnswerIndex = questions[currentQuestionIndex].correctAnswerIndex
        if (selectedOptionIndex == correctAnswerIndex) {
            score++
        }

        val intent = Intent(this, AnswerActivity::class.java).apply {
            putExtra("SELECTED_ANSWER_INDEX", selectedOptionIndex)
            putExtra("QUESTION_INDEX", currentQuestionIndex)
            putExtra("TOPIC_NAME", topicName)
            putExtra("SCORE", score) // Pass the updated cumulative score
        }
        startActivity(intent)
    }
}
