package com.example.todo

import android.app.Application
import android.provider.Settings
import com.google.firebase.FirebaseApp

class TodoApplication : Application() {
    val installationId: String by lazy {
        Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
} 