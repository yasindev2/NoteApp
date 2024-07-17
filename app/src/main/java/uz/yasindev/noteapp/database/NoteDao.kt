package uz.yasindev.noteapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {


    @Query("SELECT * FROM note_database")
    fun getAllData(): List<NoteModel>

    @Query("SELECT * FROM note_database WHERE id=:id")
    fun getNote(id: Int): NoteModel

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNote(noteModel: NoteModel)

    @Delete
    fun deleteNote(noteModel: NoteModel)

    @Update
    fun updateNote(noteModel: NoteModel)

    @Query("DELETE FROM note_database")
    fun deleteAllData()

    @Query("SELECT * FROM note_database WHERE title LIKE '%' || :text || '%'")
    fun searchTitle(text: String): List<NoteModel>



}