package uz.yasindev.noteapp.cache

import android.content.Context
import android.content.SharedPreferences

class AppCache private constructor(context: Context) {


    companion object {
        private lateinit var appCache: AppCache
        lateinit var sharedPreferences: SharedPreferences

        fun init(context: Context) {
            appCache = AppCache(context)
        }

        fun getObject(): AppCache {
            return appCache
        }
    }


    init {
        sharedPreferences = context.getSharedPreferences("app_cache", Context.MODE_PRIVATE)
    }


    fun saveNoteCount(count: Int) {
        sharedPreferences.edit().putInt("note_count", count).apply()
    }

    fun getNoteCount(): Int {
        return sharedPreferences.getInt("note_count", 0)
    }

    fun deleteCount(){
        sharedPreferences.edit().clear().apply()
    }


}