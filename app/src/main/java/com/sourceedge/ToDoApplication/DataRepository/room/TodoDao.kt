package com.sourceedge.ToDoApplication.DataRepository.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/**
 * Created by Manikandan Baskaran on 21-02-2019.
 */

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(todoEntity: TodoEntity)

    @Update
    fun updateTodo(todoEntity: TodoEntity)

    @Delete
    fun deleteTodo(todoEntity: TodoEntity)

    @Query("SELECT * FROM todo_entities ORDER BY todoDate DESC")
    fun getAllTodoAsLiveData(): LiveData<List<TodoEntity>>

    @Query("SELECT * FROM todo_entities ORDER BY todoDate DESC")
    fun getAllTodoAsList(): List<TodoEntity>

    @Query("DELETE FROM todo_entities WHERE id = :id")
    fun deleteTodoById(id: String)

    @Query("DELETE FROM todo_entities")
    fun deleteTable()

}