package com.sourceedge.ToDoApplication.DataRepository.room

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Created by Manikandan Baskaran on 22-02-2019.
 */
class TypeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}