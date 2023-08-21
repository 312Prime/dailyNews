package com.da312.dailynews.fragments

import androidx.lifecycle.asLiveData
import com.da312.dailynews.base.BaseViewModel
import com.da312.dailynews.data.repository.TodoRepository
import com.da312.dailynews.model.TodoModel
import kotlinx.coroutines.flow.MutableStateFlow

class TodoViewModel(
    private val todoRepository: TodoRepository
) : BaseViewModel() {

    private val _isListEmpty = MutableStateFlow(false)
    val isListEmpty = _isListEmpty.asLiveData()

    fun saveTodoList(todoModel: TodoModel): List<TodoModel>? {
        return todoRepository.storeTodoList(todoModel)
            .also {
                if (it != null) { _isListEmpty.value = it.isEmpty() }
            }
    }

    fun initTodoList(): List<TodoModel> {
        return todoRepository.initTodoList()
            .also { _isListEmpty.value = it.isEmpty() }
    }

    fun deleteTodoList(todoListString: String): List<TodoModel> {
        return todoRepository.deleteTodoList(todoListString)
            .also { _isListEmpty.value = it.isEmpty() }
    }

    fun switchTodo(todoCode: String, isCompleteTo: Boolean): List<TodoModel> {
        return todoRepository.switchTodoList(todoCode, isCompleteTo)
            .also { _isListEmpty.value = it.isEmpty() }
    }

    fun deleteAllList() {
        return todoRepository.deleteAllTodoList()
    }
}