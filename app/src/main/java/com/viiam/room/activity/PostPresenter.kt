package com.viiam.room.activity

import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.util.Log
import com.viiam.room.database.NemoDatabase
import com.viiam.room.database.PostDao
import com.viiam.room.model.Post
import com.viiam.room.network.PostApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import org.reactivestreams.Subscriber
import io.reactivex.disposables.CompositeDisposable
import android.databinding.adapters.TextViewBindingAdapter.setText
import io.reactivex.Observable


class PostPresenter(postView: PostView) : BasePresenter<PostView>(postView) {

    private var TAG : String = "PostPresenter"

    @Inject
    lateinit var postApi: PostApi

    private var subscription: Disposable? = null
    private var postDao: PostDao

    init {
        postDao = NemoDatabase.getInstance(view.getContext())!!.postDao()
    }

    override fun onViewCreated() {
        loadPosts()
    }

    /**
     * Loads the posts from the API and presents them in the view when retrieved, or showss error if
     * any.
     */
    fun loadPosts() {
        view.showLoading()
        subscription = postApi
                .getPosts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { postList ->
                            view.updatePosts(postList)
                            PopulateDbAsync().execute(postList)
                        },
                        { error -> error.message
                        Log.e(TAG, "Message Err "+error.message)}
                )
    }

    inner class PopulateDbAsync() : AsyncTask<List<Post>, Void, Void>() {
        override fun doInBackground(vararg params: List<Post>): Void? {
            for(i in 0..params[0].size-1){
                NemoDatabase.getInstance(view.getContext())!!.postDao().insert(params[0].get(i))
            }

            return null
        }
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    fun deleteAll(){
        postDao.deleteAll()
    }

    fun insert(post: Post){
        InsertAsyncTask(postDao).execute(post)
    }

    private class InsertAsyncTask internal constructor(private val postDao: PostDao) : AsyncTask<Post, Void, Void>() {
        override fun doInBackground(vararg params: Post): Void? {
            postDao.insert(params[0])
            return null
        }
    }
}