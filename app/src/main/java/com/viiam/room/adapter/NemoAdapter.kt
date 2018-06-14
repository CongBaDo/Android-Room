package com.viiam.room.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.viiam.room.R
import com.viiam.room.model.Post
import kotlinx.android.synthetic.main.item_post.view.*

class NemoAdapter(private var subjects: List<Post>,
                     var onItemClick: (Int) -> Unit) :
        RecyclerView.Adapter<NemoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(subjects[position], position)
    }

    override fun getItemCount() = subjects.size

    inner class ViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        fun bindData(subjectItem: Post, position: Int) {
            with(subjectItem) {
                itemView.setOnClickListener { onItemClick(position) }

                itemView.post_title.text = subjectItem.title
                itemView.post_body.text = subjectItem.body
            }
        }
    }

    fun updatePost(posts: List<Post>){
        subjects = posts
        notifyDataSetChanged()
    }
}