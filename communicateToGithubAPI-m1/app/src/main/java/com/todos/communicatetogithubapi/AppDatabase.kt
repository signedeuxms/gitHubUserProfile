package com.todos.communicatetogithubapi

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    //
    abstract fun interfaceToDatabase(): IUserDao

    companion object {

        private var uniqueInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (uniqueInstance == null) {
                synchronized(AppDatabase::class) {
                    uniqueInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase::class.java, "users_github.db")
                            .build()
                }
            }
            return uniqueInstance
        }

        fun destroyInstance() {
            uniqueInstance = null
        }
    }
}