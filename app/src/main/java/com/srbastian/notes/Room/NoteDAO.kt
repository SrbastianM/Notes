package com.srbastian.notes.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.srbastian.notes.Model.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDAO {

    @Insert
    suspend fun insert(note : Note)

    @Update
    suspend fun updated(note : Note)

    @Delete
    suspend fun delete(note : Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM NOTE_TABLE ORDER BY id ASC")
    fun getAll() : Flow<List<Note>>

}