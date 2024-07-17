package uz.yasindev.noteapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteModel::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getDao(): NoteDao
}