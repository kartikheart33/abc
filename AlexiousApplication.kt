package com.alexios.android.activities

import android.app.Application
import com.google.firebase.FirebaseApp

class AlexiousApplication: Application() {
     override fun onCreate() {
        FirebaseApp.initializeApp(this)
        super.onCreate()
    }
}