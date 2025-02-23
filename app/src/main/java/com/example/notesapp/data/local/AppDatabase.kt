package com.example.notesapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.data.models.Note

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao() : NoteDao

    object DatabaseBuilder{
        @Volatile
        var instance: AppDatabase? = null
        fun buildDatabse(context: Context): AppDatabase {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "notes_db"
            ).build()
            return db
        }

        fun getInstance(context: Context):AppDatabase{

            return instance ?:buildDatabse(context = context).also { instance=it }
        }
    }

}