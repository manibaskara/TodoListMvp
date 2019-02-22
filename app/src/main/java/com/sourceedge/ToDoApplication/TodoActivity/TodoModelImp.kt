package com.sourceedge.ToDoApplication.TodoActivity

import android.arch.lifecycle.LiveData
import com.sourceedge.ToDoApplication.DataRepository.DataRepository
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.sourceedge.ToDoApplication.DataRepository.room.TodoEntity


/**
 * Created by Manikandan Baskaran on 21-02-2019.
 */

class TodoModelImpl(application: Application) : AndroidViewModel(application), TodoContract.Model {


    private var dataRepository: DataRepository = DataRepository(application)

    override fun addTodo(todoEntity: TodoEntity) {
        dataRepository.insertTodo(todoEntity)
    }

    override fun deleteTodo(todoEntity: TodoEntity) {
        dataRepository.deleteTodo(todoEntity)
    }

    override fun deleteTodoById(id: String) {
        dataRepository.deleteTodoById(id)
    }

    override fun getAllTodoLiveData():LiveData<List<TodoEntity>> {
        return dataRepository.getAllTodoLiveData()
    }

    override fun deleteAll() {
            dataRepository.deleteTable()
    }

    override fun getAllTodoList(): List<TodoEntity> {
        return dataRepository.getAllTodoAsList()
    }




}