package com.sourceedge.ToDoApplication.DataRepository.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

/**
 * Created by Manikandan Baskaran on 21-02-2019.
 */

@Database(entities = arrayOf(TodoEntity::class), version = 1)
@TypeConverters(TypeConverter::class)
abstract class TodoDataBase : RoomDatabase() {

    abstract fun getTodoDao(): TodoDao

    companion object {
        @Volatile
        var INSTANCE: TodoDataBase? = null

        fun getAppDataBase(context: Context): TodoDataBase? {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                            Room.databaseBuilder(
                                context.applicationContext
                                , TodoDataBase::class.java
                                , "todo_database"
                            ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}