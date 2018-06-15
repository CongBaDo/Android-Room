package com.viiam.room.database

import android.arch.persistence.room.*
import com.viiam.room.model.Post
import io.reactivex.Flowable
import io.reactivex.Observable


@Dao
interface PostDao{

    @Query("SELECT * from post_table")
    fun getAllPost(): Flowable<List<Post>>

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    fun insert(post: Post)

    @Update
    fun update(post: Post)

    @Delete
    fun delete(post: Post)

    @Query("SELECT * FROM post_table WHERE id=:id")
    fun getPost(id: Int): Post

    @Query("DELETE FROM post_table")
    fun deleteAll()

}