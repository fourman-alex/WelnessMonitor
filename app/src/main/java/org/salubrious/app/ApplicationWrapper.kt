package org.salubrious.app

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

lateinit var appContext: Application
val uiScope = CoroutineScope(Dispatchers.Main)

class ApplicationWrapper : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}