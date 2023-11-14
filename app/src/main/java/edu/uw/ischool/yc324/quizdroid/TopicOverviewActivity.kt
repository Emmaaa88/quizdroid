package edu.uw.ischool.yc324.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView


class TopicOverviewActivity : AppCompatActivity() {

    val topicDescriptions = mapOf(
        "Math" to "Dive into the world of Mathematics where numbers and formulas reveal the secrets of the universe. Challenge yourself with questions ranging from basic arithmetic to complex calculus.",
        "Physics" to "Explore the fundamental principles that govern the physical world around us. From Newton's laws of motion to the theory of relativity, test your knowledge of physics.",
        "Marvel Super Heroes" to "Enter the Marvel Universe, where superheroes and villains clash in epic battles. Test your knowledge of their origins, powers, and adventures."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

        val topicName = intent.getStringExtra("TOPIC_NAME") ?: "Math"
        val topicDescription = topicDescriptions[topicName] ?: "No description available."

        findViewById<TextView>(R.id.topicDescriptionTextView).text = topicDescription

        val beginButton: Button = findViewById(R.id.beginButton)
        beginButton.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("TOPIC_NAME", topicName)
            startActivity(intent)
        }
    }
}
