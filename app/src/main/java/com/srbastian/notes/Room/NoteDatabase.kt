package com.srbastian.notes.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.srbastian.notes.Model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDAO

    //Single object for database class == Singleton
    companion object {

        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDataBase(context: Context, scope: CoroutineScope): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                )
                    .addCallback(NoteDatabaseCallback(scope))
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }

    private class NoteDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val noteDao = database.getNoteDao()
                    noteDao.insert(Note("Title 1", "Description 1"))
                    noteDao.insert(Note("Title 2", "Description 2"))
                    noteDao.insert(Note("Title 3", "Description 3"))

                }
            }
        }
    }

}