package com.viiam.room.activity

import android.app.Activity
import android.app.Application
import android.content.Context

interface BaseView{
    fun getContext() : Context

    fun getActivity(): Activity
}