package edu.uw.ischool.yc324.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class TopicOverviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

        // Retrieve the topic title from the intent
        val topicTitle = intent.getStringExtra("TOPIC_TITLE") ?: return finish()

        // Find the topic by title from the repository
        val topic = QuizApp.instance.repository.getTopics().find { it.title == topicTitle }
            ?: return finish() // Finish the activity if the topic is not found

        // Set the topic description in the TextView
        findViewById<TextView>(R.id.topicDescriptionTextView).text = topic.desc

        // Set up the 'Begin' button to start the QuestionActivity
        val beginButton: Button = findViewById(R.id.beginButton)
        beginButton.setOnClickListener {
            // Create an intent to start QuestionActivity, passing the topic title
            val intent = Intent(this, QuestionActivity::class.java).apply {
                putExtra("TOPIC_TITLE", topic.title)
            }
            startActivity(intent)
        }
    }
}
