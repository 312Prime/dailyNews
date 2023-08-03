package com.example.dailynews.data.repository

import com.example.dailynews.base.BaseRepository
import com.example.dailynews.data.local.SharedPreferenceManager
import com.example.dailynews.model.TodoModel
import com.google.gson.JsonArray
import org.json.JSONArray

class TodoRepository(
    private val sharedPreferenceManager: SharedPreferenceManager
) : BaseRepository() {

    fun storeTodoList(todoModel: TodoModel): List<TodoModel> {
        var oldList = if (sharedPreferenceManager.todoList == "") JSONArray()
        else JSONArray(sharedPreferenceManager.todoList)

        val newList = JsonArray()
        val newModel = mutableListOf<TodoModel>()
        for (i in 0 until oldList.length()) {
            newList.add(oldList.optString(i))
        }

        val newDate = ""
        val newTitle =
            todoModel.title.also { for (i in 0 until 10 - todoModel.title.length) it.plus("") }
        val newMessage = todoModel.message
        val newIsComplete = if (todoModel.isComplete) 1 else 0

        newList.add(newDate + newTitle + newMessage + newIsComplete)

        sharedPreferenceManager.todoList = newList.toString()

        oldList = JSONArray(sharedPreferenceManager.todoList)
        for (i in 0 until oldList.length()) {
            val cData = oldList.optString(i)
            newModel.add(
                TodoModel(
                    date = cData.substring(0, 4),
                    title = cData.substring(4, 13),
                    message = cData.substring(13, cData.length - 2),
                    isComplete = cData.last() == '1'
                )
            )
        }
        return newModel
    }

    fun initTodoList(): List<TodoModel> {
        val todoList = if (sharedPreferenceManager.todoList == "") JSONArray()
        else JSONArray(sharedPreferenceManager.todoList)
        val todoModel = mutableListOf<TodoModel>()
        for (i in 0 until todoList.length()) {
            todoModel.add(
                TodoModel(
                    date = todoList.optString(i).substring(0, 4),
                    title = todoList.optString(i).substring(4, 13),
                    message = todoList.optString(i).substring(13, todoList.optString(i).length - 2),
                    isComplete = todoList.optString(i).last() == '1'
                )
            )
        }
        return todoModel
    }

    fun deleteTodoList(todoListString: String): List<TodoModel> {
        val oldList = JSONArray(sharedPreferenceManager.todoList)
        val newList = JsonArray()
        val newModel = mutableListOf<TodoModel>()
        for (i in 0 until oldList.length()) {
            if (todoListString != oldList.optString(i)) {
                newList.add(oldList.optString(i))
                newModel.add(
                    TodoModel(
                        date = oldList.optString(i).substring(0, 4),
                        title = oldList.optString(i).substring(4, 13),
                        message = oldList.optString(i)
                            .substring(13, oldList.optString(i).length - 2),
                        isComplete = oldList.optString(i).last() == '1'
                    )
                )
            }
        }
        sharedPreferenceManager.todoList = newList.toString()
        return newModel
    }

    fun deleteAllTodoList() {
        sharedPreferenceManager.clearTodoList()
    }
}