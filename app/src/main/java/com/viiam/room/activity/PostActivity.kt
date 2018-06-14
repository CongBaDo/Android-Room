package com.viiam.room.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.viiam.room.R
import com.viiam.room.adapter.NemoAdapter
import com.viiam.room.model.Post
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : BaseActivity<PostPresenter>(), PostView {

    private val TAG: String = "PostActivity"
    private lateinit var adapter: NemoAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        progressBar.visibility = View.GONE

        adapter = NemoAdapter(emptyList()){

        }

        rcPost.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rcPost.adapter = adapter

        butRequest.setOnClickListener(View.OnClickListener { presenter.onViewCreated() })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun updatePosts(posts: List<Post>) {
        adapter.updatePost(posts)
        progressBar.visibility = View.GONE
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun instantiatePresenter(): PostPresenter {
        return PostPresenter(this)
    }

    override fun getActivity(): Activity {
        return this
    }
}