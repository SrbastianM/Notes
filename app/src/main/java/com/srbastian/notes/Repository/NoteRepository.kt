package com.srbastian.notes.Repository

import androidx.annotation.WorkerThread
import com.srbastian.notes.Model.Note
import com.srbastian.notes.Room.NoteDAO
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDAO: NoteDAO) {

    val myAllNotes : Flow<List<Note>> = noteDAO.getAll()

    @WorkerThread
    suspend fun insert(note: Note) {
        noteDAO.insert(note)
    }

    @WorkerThread
    suspend fun update(note: Note) {
        noteDAO.updated(note)
    }

    @WorkerThread
    suspend fun delete(note: Note) {
        noteDAO.delete(note)
    }

    @WorkerThread
    suspend fun deleteAllNotes() {
        noteDAO.deleteAllNotes()
    }

}