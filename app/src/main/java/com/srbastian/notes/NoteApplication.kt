package com.srbastian.notes

import android.app.Application
import com.srbastian.notes.Repository.NoteRepository
import com.srbastian.notes.Room.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    // create an instance for DB only first app needed. Not every time will run it
    val database by lazy { NoteDatabase.getDataBase(this, applicationScope) }
    val repository by lazy {NoteRepository(database.getNoteDao())}
}