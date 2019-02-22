package com.sourceedge.ToDoApplication.TodoActivity.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sourceedge.ToDoApplication.DataRepository.room.TodoEntity
import com.sourceedge.ToDoApplication.R
import com.sourceedge.ToDoApplication.TodoActivity.TodoPresenterImpl
import kotlinx.android.synthetic.main.todo_list_content.view.*

/**
 * Created by Manikandan Baskaran on 21-02-2019.
 */

class TodoListAdapter(val context: Context, val todoPresenterImpl: TodoPresenterImpl) :
    RecyclerView.Adapter<ViewHolder>() {
    private var todoEntities = emptyList<TodoEntity>()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.todo_list_content, p0, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val todoEntity: TodoEntity = todoEntities.get(position)
        viewHolder.tvTodoText.text = todoEntity.toDoText
        viewHolder.tvTodoCreatedOn.text = "Created on " + todoEntity.todoDate
        viewHolder.ivDeleteTodo.setOnClickListener { todoPresenterImpl.onItemDelete(todoEntity.id) }
        viewHolder.cvTodo.setOnClickListener { todoPresenterImpl.onTodoSelected(todoEntity) }
    }

    override fun getItemCount(): Int {
        return todoEntities.size
    }

    fun setTodoList(todoEntities: List<TodoEntity>) {
        this.todoEntities = todoEntities
        notifyDataSetChanged()
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val tvTodoText = view.tv_todo_text!!
    val tvTodoCreatedOn = view.tv_created_date!!
    val ivDeleteTodo = view.iv_delete_todo!!
    val cvTodo = view.cv_todo!!
}