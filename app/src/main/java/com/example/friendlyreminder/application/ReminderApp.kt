package com.example.friendlyreminder.application

import android.app.Application

class ReminderApp : Application() {

    companion object{
        lateinit var mApp : Application
    }

    init {
        mApp = this
    }

    override fun onCreate() {
        super.onCreate()

    }
}