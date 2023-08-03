package com.example.dailynews.fragments

import com.example.dailynews.base.BaseViewModel
import com.example.dailynews.data.repository.TodoRepository
import com.example.dailynews.model.TodoModel

class TodoViewModel(
    private val todoRepository: TodoRepository
) : BaseViewModel() {

    fun saveTodoList(todoModel: TodoModel): List<TodoModel> {
        return todoRepository.storeTodoList(todoModel)
    }

    fun initTodoList(): List<TodoModel> {
        return todoRepository.initTodoList()
    }

    fun deleteTodoList(todoListString: String): List<TodoModel> {
        return todoRepository.deleteTodoList(todoListString)
    }
}