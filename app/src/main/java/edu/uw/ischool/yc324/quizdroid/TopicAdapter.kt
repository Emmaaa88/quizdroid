package edu.uw.ischool.yc324.quizdroid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class TopicAdapter(context: Context, topics: List<Topic>) : ArrayAdapter<Topic>(context, 0, topics) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_topic, parent, false)

        val topic = getItem(position)
        view.findViewById<TextView>(R.id.topic_title).text = topic?.title
        view.findViewById<TextView>(R.id.topic_description).text = topic?.desc // Assuming you have a TextView with id topic_description in your layout
        return view
    }

}
