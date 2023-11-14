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

        val topicId = intent.getIntExtra("TOPIC_ID", -1)

        if (topicId == -1) {
            finish()
            return
        }

        val topicRepository = QuizApp.instance.repository
        val topic = topicRepository.getTopics().find { it.id == topicId }

        if (topic == null) {
            finish()
            return
        }

        findViewById<TextView>(R.id.topicDescriptionTextView).text = topic.longDescription

        val beginButton: Button = findViewById(R.id.beginButton)
        beginButton.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("TOPIC_ID", topic.id)
            startActivity(intent)
        }
    }
}
