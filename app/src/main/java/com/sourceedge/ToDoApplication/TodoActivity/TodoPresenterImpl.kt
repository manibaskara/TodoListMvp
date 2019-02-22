package com.sourceedge.ToDoApplication.TodoActivity

import com.sourceedge.ToDoApplication.DataRepository.room.TodoEntity
import java.util.*

/**
 * Created by Manikandan Baskaran on 21-02-2019.
 */

class TodoPresenterImpl(_view: TodoContract.View, todoModel: TodoContract.Model) : TodoContract.Presenter {

    private var view: TodoContract.View = _view
    private var model: TodoContract.Model = todoModel


    override fun initView() {
        view.onClick()
    }

    override fun onAddTodoClick(text: String, isFromDialog: Boolean) {
        if (text.isEmpty())
            view.setTodoTextEmptyError()
        else
            view.showDatePicker(text, isFromDialog)
    }

    override fun constructTodoEntity(message: String, date: Date, id: String): TodoEntity {
        return TodoEntity(message, date, id)
    }

    override fun addTodo(todoEntity: TodoEntity, isFromDialog: Boolean) {
        if (isFromDialog) {
            view.setTempDate(todoEntity.id, todoEntity.todoDate)
        } else model.addTodo(todoEntity)
    }

    override fun onGotDate(message: String, date: Date, isFromDialog: Boolean) {
        view.showTimePicker(message, date, isFromDialog)
    }

    override fun onItemDelete(id: String) {
        model.deleteTodoById(id)
    }

    override fun onDeleteAll() {
        model.deleteAll()
    }

    override fun onSearchTextChange(text: String) {
        val todoEntities: MutableList<TodoEntity> = mutableListOf()
        val allTodoList = model.getAllTodoList()
        for (item in allTodoList) {
            if (item.toDoText.contains(text)) {
                todoEntities.add(item)
            }
        }
        if (todoEntities.isEmpty())
            view.showToast("No item exist for this query")
        view.updateTodoList(todoEntities)
    }

    override fun onTodoSelected(todoEntity: TodoEntity) {
        view.showTodoDialog(todoEntity)
    }

    override fun onTodoUpdate(todoEntity: TodoEntity, todoText: String, todoDate: Date) {
        if (todoText.isEmpty()) view.setDialogTodoEmptyError("Todo Text Empty")
        else {
            todoEntity.toDoText = todoText
            todoEntity.todoDate = todoDate
            model.addTodo(todoEntity)
        }
    }

    override fun generateUuid(): String {
        return UUID.randomUUID().toString()
    }

}