package uz.yasindev.noteapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_database")
data class NoteModel(
    @ColumnInfo("id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("priority")
    val priority:Int,
    @ColumnInfo("date")
    val date: String
)
