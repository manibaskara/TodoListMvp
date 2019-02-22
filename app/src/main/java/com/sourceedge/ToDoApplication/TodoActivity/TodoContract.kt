package com.sourceedge.ToDoApplication.TodoActivity

import android.arch.lifecycle.LiveData
import com.sourceedge.ToDoApplication.DataRepository.room.TodoEntity
import java.util.*

/**
 * Created by Manikandan Baskaran on 21-02-2019.
 */

interface TodoContract {

    interface View {

        fun initView()
        fun onClick()
        fun setTodoTextEmptyError()
        fun showDatePicker(message: String, isFromDialog: Boolean)
        fun showTimePicker(message: String, date: Date, isFromDialog: Boolean)
        fun showToast(message: String)
        fun updateTodoList(todoEntities: List<TodoEntity>)
        fun showTodoDialog(todoEntity: TodoEntity)
        fun setDialogTodoEmptyError(message: String)
        fun setDialogDateEmptyError(message: String)
        fun setTempDate(id: String, date: Date)
    }

    interface Model {

        fun addTodo(todoEntity: TodoEntity)
        fun deleteTodo(todoEntity: TodoEntity)
        fun deleteTodoById(id: String)
        fun getAllTodoLiveData(): LiveData<List<TodoEntity>>
        fun getAllTodoList(): List<TodoEntity>
        fun deleteAll()
    }

    interface Presenter {

        fun initView()
        fun onAddTodoClick(text: String, isFromDialog: Boolean)
        fun addTodo(todoEntity: TodoEntity, isFromDialog: Boolean)
        fun constructTodoEntity(message: String, date: Date, id: String): TodoEntity
        fun onGotDate(message: String, date: Date, isFromDialog: Boolean)
        fun onItemDelete(id: String)
        fun onDeleteAll()
        fun onSearchTextChange(text: String)
        fun onTodoSelected(todoEntity: TodoEntity)
        fun onTodoUpdate(todoEntity: TodoEntity, todoText: String, todoDate: Date)
        fun generateUuid(): String

    }
}