package edu.uw.ischool.yc324.quizdroid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView


class TopicAdapter(context: Context, topics: List<Topic>) : ArrayAdapter<Topic>(context, 0, topics) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_topic, parent, false)

        val topic = getItem(position)
        val titleTextView: TextView = view.findViewById(R.id.topic_title)
        val iconImageView: ImageView = view.findViewById(R.id.topic_icon)

        titleTextView.text = topic?.title

        topic?.iconResId?.let { iconImageView.setImageResource(it) }

        return view
    }
}
