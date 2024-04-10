package com.srbastian.notes.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.srbastian.notes.Model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDAO

    //Single object for database class == Singleton
    companion object {

        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDataBase(context: Context): NoteDatabase
        {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }

}