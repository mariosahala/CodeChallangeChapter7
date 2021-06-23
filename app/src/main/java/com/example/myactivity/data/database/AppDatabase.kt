package com.example.myactivity.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Friends::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun friendsDao(): FriendsDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "AppDatabase_db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}