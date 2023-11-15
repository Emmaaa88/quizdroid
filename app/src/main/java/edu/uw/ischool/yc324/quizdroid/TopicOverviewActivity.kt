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

        val topicTitle = intent.getStringExtra("TOPIC_TITLE") ?: return finish()

        val topic = QuizApp.instance.repository.getTopics().find { it.title == topicTitle }
            ?: return finish()

        findViewById<TextView>(R.id.topicDescriptionTextView).text = topic.desc

        val beginButton: Button = findViewById(R.id.beginButton)
        beginButton.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("TOPIC_TITLE", topic.title)
            startActivity(intent)
        }
    }
}
