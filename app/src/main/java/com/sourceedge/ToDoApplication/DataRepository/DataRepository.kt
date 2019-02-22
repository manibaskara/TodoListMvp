package com.sourceedge.ToDoApplication.DataRepository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.sourceedge.ToDoApplication.DataRepository.room.TodoDataBase
import com.sourceedge.ToDoApplication.DataRepository.room.TodoEntity


/**
 * Created by Manikandan Baskaran on 21-02-2019.
 */

class DataRepository(application: Application) {

    private val todoDataBase = TodoDataBase.getAppDataBase(application.applicationContext)
    val todoDao = todoDataBase!!.getTodoDao()

    @WorkerThread
    fun insertTodo(todoEntity: TodoEntity) {
        todoDao.insertTodo(todoEntity)
    }

    @WorkerThread
    fun deleteTodo(todoEntity: TodoEntity) {
        todoDao.deleteTodo(todoEntity)
    }

    @WorkerThread
    fun getAllTodoLiveData(): LiveData<List<TodoEntity>> {
        return todoDao.getAllTodoAsLiveData()
    }

    @WorkerThread
    fun getAllTodoAsList(): List<TodoEntity> {
        return todoDao.getAllTodoAsList()
    }

    @WorkerThread
    fun deleteTodoById(id: String) {
        todoDao.deleteTodoById(id)
    }

    @WorkerThread
    fun deleteTable() {
        todoDao.deleteTable()
    }

}