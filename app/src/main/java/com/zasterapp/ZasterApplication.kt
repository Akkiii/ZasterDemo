package com.zasterapp

import android.app.Application


class ZasterApplication : Application() {
    companion object {
        lateinit var instance: ZasterApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}