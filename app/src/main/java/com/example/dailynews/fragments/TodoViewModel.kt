package com.example.dailynews.fragments

import androidx.lifecycle.asLiveData
import com.example.dailynews.base.BaseViewModel
import com.example.dailynews.data.repository.TodoRepository
import com.example.dailynews.model.TodoModel
import kotlinx.coroutines.flow.MutableStateFlow

class TodoViewModel(
    private val todoRepository: TodoRepository
) : BaseViewModel() {

    private val _isListEmpty = MutableStateFlow(false)
    val isListEmpty = _isListEmpty.asLiveData()

    fun saveTodoList(todoModel: TodoModel): List<TodoModel> {
        return todoRepository.storeTodoList(todoModel)
    }

    fun initTodoList(): List<TodoModel> {
        return todoRepository.initTodoList()
    }

    fun deleteTodoList(todoListString: String): List<TodoModel> {
        return todoRepository.deleteTodoList(todoListString)
    }

    fun switchTodo(todoCode: String, isCompleteTo: Boolean): List<TodoModel> {
        return todoRepository.switchTodoList(todoCode, isCompleteTo)
    }

    fun deleteAllList() {
        return todoRepository.deleteAllTodoList()
    }
}