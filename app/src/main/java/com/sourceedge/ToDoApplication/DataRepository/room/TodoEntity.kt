package com.sourceedge.ToDoApplication.DataRepository.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by Manikandan Baskaran on 21-02-2019.
 */

@Entity(tableName = "todo_entities")
data class TodoEntity(
    var toDoText: String,
    var todoDate: Date,
    @PrimaryKey()
    var id: String
)