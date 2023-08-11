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

        val newDate = todoModel.date
        var newTitle = todoModel.title
        for (i in 0 until 10 - todoModel.title.length) newTitle = "$newTitle "
        val newMessage = todoModel.message
        val newIsComplete = if (todoModel.isComplete) "1" else "0"

        newList.add(newDate + newTitle + newMessage + newIsComplete)

        sharedPreferenceManager.todoList = newList.toString()

        oldList = JSONArray(sharedPreferenceManager.todoList)
        for (i in 0 until oldList.length()) {
            val cData = oldList.optString(i)
            if (cData.length > 18) newModel.add(
                TodoModel(
                    date = cData.substring(0, 8),
                    title = cData.substring(8, 18),
                    message = if (cData.length == 19) ""
                    else cData.substring(18, cData.length - 1),
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
            if (todoList.optString(i).length > 14) todoModel.add(
                TodoModel(
                    date = todoList.optString(i).substring(0, 8),
                    title = todoList.optString(i).substring(8, 18),
                    message = if (todoList.optString(i).length == 19) ""
                    else todoList.optString(i).substring(18, todoList.optString(i).length - 1),
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
                        date = oldList.optString(i).substring(0, 8),
                        title = oldList.optString(i).substring(8, 18),
                        message = if (oldList.optString(i).length == 19) ""
                        else oldList.optString(i).substring(18, oldList.optString(i).length - 1),
                        isComplete = oldList.optString(i).last() == '1'
                    )
                )
            }
        }
        sharedPreferenceManager.todoList = newList.toString()
        return newModel
    }

    // 할 일 완료 상태 변경
    fun switchTodoList(todoCode: String, isCompleteTo: Boolean): List<TodoModel> {
        val oldList = JSONArray(sharedPreferenceManager.todoList)
        val newList = JsonArray()
        val newModel = mutableListOf<TodoModel>()
        for (i in 0 until oldList.length()) {
            if (todoCode == oldList.optString(i)) {
                newList.add(todoCode.substring(0, todoCode.length - 1) + if (isCompleteTo) 1 else 0)
                newModel.add(convertToModel(oldList.optString(i), isCompleteTo))
            } else {
                newList.add(oldList.optString(i))
                newModel.add(
                    convertToModel(oldList.optString(i), oldList.optString(i).last() == '1')
                )
            }
        }
        sharedPreferenceManager.todoList = newList.toString()
        return newModel
    }

    fun deleteAllTodoList() {
        sharedPreferenceManager.clearAll()
    }

    // string -> TodoModel
    private fun convertToModel(optString: String, isComplete: Boolean): TodoModel {
        return TodoModel(
            date = optString.substring(0, 8),
            title = optString.substring(8, 18),
            message = if (optString.length == 19) ""
            else optString.substring(18, optString.length - 1),
            isComplete = isComplete
        )
    }
}