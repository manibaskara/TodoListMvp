package com.sourceedge.ToDoApplication.TodoActivity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.sourceedge.ToDoApplication.DataRepository.room.TodoEntity
import com.sourceedge.ToDoApplication.R
import com.sourceedge.ToDoApplication.TodoActivity.adapter.TodoListAdapter
import kotlinx.android.synthetic.main.activity_to_do.*
import kotlinx.android.synthetic.main.show_todo_dialog.view.*
import java.util.*


class TodoViewImpl : AppCompatActivity(), TodoContract.View {

    internal var ivAdd: ImageView? = null
    internal var etSearch: EditText? = null
    internal var ivDelete: ImageView? = null
    internal var presenter: TodoPresenterImpl? = null
    private lateinit var todoModelImpl: TodoModelImpl
    var todoListAdapter: TodoListAdapter? = null
    private lateinit var dialogView: View
    private lateinit var dateTemp: Date
    private var idTemp: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
        initView()
    }

    override fun initView() {
        ivAdd = findViewById(R.id.iv_add)
        etSearch = findViewById(R.id.et_search)
        ivDelete = findViewById(R.id.iv_delete)
        todoModelImpl = ViewModelProviders.of(this).get(TodoModelImpl::class.java)
        presenter = TodoPresenterImpl(this, todoModelImpl)
        todoListAdapter = TodoListAdapter(this, presenter!!)
        rv_todo_list.adapter = todoListAdapter
        rv_todo_list.layoutManager = LinearLayoutManager(this)
        todoModelImpl.getAllTodoLiveData().observe(this, object : Observer<List<TodoEntity>> {
            override fun onChanged(todoEntities: List<TodoEntity>?) {
                if (todoEntities != null) todoListAdapter!!.setTodoList(todoEntities)
            }
        })
        onClick()
    }

    override fun setTodoTextEmptyError() {
        et_text!!.error = "Enter text."
        et_text.requestFocus()
    }

    override fun showDatePicker(message: String, isFromDialog: Boolean) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog =
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, _year, monthOfYear, dayOfMonth ->
                run {
                    val cal = Calendar.getInstance()
                    cal.set(Calendar.YEAR, _year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    presenter?.onGotDate(message, cal.time, isFromDialog)
                }
            }, year, month, day)
        datePickerDialog.show()
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showTimePicker(message: String, date: Date, isFromDialog: Boolean) {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.time = date
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            if (isFromDialog) {
                dialogView.tv_created_date.text = cal.time.toString()
                dateTemp = cal.time
            } else {
                presenter?.addTodo(
                    presenter?.constructTodoEntity(message, cal.time, presenter!!.generateUuid())!!,
                    isFromDialog
                )
            }
        }
        TimePickerDialog(
            this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY)
            , cal.get(Calendar.MINUTE), true
        ).show()
    }

    override fun onClick() {
        ivAdd!!.setOnClickListener { presenter?.onAddTodoClick(et_text?.text?.trim().toString(), false) }

        ivDelete!!.setOnClickListener { presenter?.onDeleteAll() }

        etSearch?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter?.onSearchTextChange(et_search?.text?.trim().toString())
            }
        })
    }

    override fun updateTodoList(todoEntities: List<TodoEntity>) {
        todoListAdapter!!.setTodoList(todoEntities)

    }

    override fun showTodoDialog(todoEntity: TodoEntity) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        dialogView = inflater.inflate(R.layout.show_todo_dialog, null)
        dialogBuilder.setView(dialogView)
        dateTemp = todoEntity.todoDate
        idTemp = todoEntity.id

        val etEditText = dialogView.et_text
        val ivEditTodo = dialogView.iv_edit_todo
        val tvCreated = dialogView.tv_created_date
        val ivEditDate = dialogView.iv_edit_date
        etEditText.setText(todoEntity.toDoText)
        tvCreated.text = todoEntity.todoDate.toString()

        ivEditDate.setOnClickListener { presenter?.onAddTodoClick(etEditText.text.trim().toString(), true) }

        ivEditTodo.setOnClickListener {
            etEditText.isEnabled = true
            etEditText.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etEditText, InputMethodManager.SHOW_IMPLICIT)
        }

        dialogBuilder.setPositiveButton("Submit") { dialog, id ->

            if (etEditText.text.trim().isEmpty()) {
                etEditText.error = "Enter your Todo"
            } else {
                presenter!!.addTodo(
                    presenter!!.constructTodoEntity(
                        etEditText.text.trim().toString(),
                        dateTemp,
                        idTemp
                    ), false
                )
                dialog.dismiss()
            }
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, which ->
            if (dialog != null) {
                dialog.dismiss()
            }
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    override fun setDialogTodoEmptyError(message: String) {
        dialogView.et_text.error = message
        dialogView.et_text.requestFocus()
    }

    override fun setDialogDateEmptyError(message: String) {
        dialogView.tv_created_date.error = message
    }

    override fun setTempDate(id: String, date: Date) {
        dateTemp = date

        idTemp = id
    }
}