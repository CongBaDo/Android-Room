package com.viiam.room.database

import android.arch.persistence.room.*
import android.content.Context
import com.viiam.room.model.Post
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(Post::class), version = 1)
abstract class NemoDatabase : RoomDatabase(){

    abstract fun postDao(): PostDao

    companion object {

        @Volatile
        private var instance: NemoDatabase? = null

        @Synchronized
        fun getInstance(context: Context): NemoDatabase? {
            if (instance == null) {
                instance = create(context)
            }
            return instance
        }

        private fun create(context: Context): NemoDatabase {
            return Room.databaseBuilder(
                    context,
                    NemoDatabase::class.java,
                    "nemo_sample.db")
//                    .addCallback(sRoomDatabaseCallback)
                    .build()
        }

        fun destroyInstance() {
            instance = null
        }

//        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {
//
//            override fun onOpen(@NonNull db: SupportSQLiteDatabase) {
//                super.onOpen(db)
//                PopulateDbAsync(instance).execute()
//            }
//        }
//
//        private class PopulateDbAsync internal constructor(db: NemoDatabase?) : AsyncTask<Void, Void, Void>() {
//
//            private val mDao: PostDao
//
//            init {
//                mDao = db!!.postDao()
//            }
//
//            override fun doInBackground(vararg params: Void): Void? {
//                mDao.deleteAll()
////                var word = Post()//Word("Hello")
////                mDao.insert(word)
////                word = Word("World")
////                mDao.insert(word)
//                return null
//            }
//        }
    }


}