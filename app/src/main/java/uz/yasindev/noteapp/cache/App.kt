package uz.yasindev.noteapp.cache

import android.app.Application

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        AppCache.init(this)
    }

}