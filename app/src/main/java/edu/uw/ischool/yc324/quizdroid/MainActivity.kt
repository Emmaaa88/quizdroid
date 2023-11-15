package edu.uw.ischool.yc324.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var topics: List<Topic>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topics = QuizApp.instance.repository.getTopics()

        val listView: ListView = findViewById(R.id.listView)
        val adapter = TopicAdapter(this, topics)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, TopicOverviewActivity::class.java).apply {
                putExtra("TOPIC_TITLE", topics[position].title)
            }
            startActivity(intent)
        }
    }
}
