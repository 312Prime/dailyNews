package com.example.dailynews.data.repository

import com.example.dailynews.base.BaseRepository
import com.example.dailynews.data.local.SharedPreferenceManager
import com.example.dailynews.model.TodoModel
import com.google.gson.JsonArray
import org.json.JSONArray

class TodoRepository(
    private val sharedPreferenceManager: SharedPreferenceManager
) : BaseRepository() {

    fun storeTodoList(todoModel: TodoModel): List<TodoModel>? {
        var oldList = if (sharedPreferenceManager.todoList == "") JSONArray()
        else JSONArray(sharedPreferenceManager.todoList)

        // new todoModel 을 JSONArray 에 추가할 String 으로 변환
        var newTitle = todoModel.title
        // title 의 10자 보다 작은 만큼 space 추가
        for (i in 0 until 10 - todoModel.title.length) newTitle = "$newTitle "
        val currentTodoString =
            todoModel.date + newTitle + todoModel.message + if (todoModel.isComplete) "1" else "0"

        val newList = JsonArray()
        val newModel = mutableListOf<TodoModel>()

        // 같은 제목이 있으면 return null 반환 -> fragment 에서 경고 메시지
        for (i in 0 until oldList.length()) {
            if (oldList.optString(i) == currentTodoString)
                return null
            else if (oldList.optString(i) ==
                currentTodoString.substring(0, currentTodoString.length - 1)
                + if (todoModel.isComplete) "0" else "1"
            ) return null

            newList.add(oldList.optString(i))
        }

        newList.add(currentTodoString)

        // sharedPreferenceManager Update
        sharedPreferenceManager.todoList = newList.toString()

        oldList = JSONArray(sharedPreferenceManager.todoList)
        for (i in 0 until oldList.length()) {
            val newData = oldList.optString(i)
            if (newData.length > 18) newModel.add(convertToModel(newData, newData.last() == '1'))
        }
        // 날짜 순으로 정렬 반환
        return newModel.apply { sortBy { it.date } }
    }

    fun initTodoList(): List<TodoModel> {
        val todoList = if (sharedPreferenceManager.todoList == "") JSONArray()
        else JSONArray(sharedPreferenceManager.todoList)
        val todoModel = mutableListOf<TodoModel>()
        for (i in 0 until todoList.length()) {
            if (todoList.optString(i).length > 14)
                todoModel.add(
                    convertToModel(
                        optString = todoList.optString(i),
                        isComplete = todoList.optString(i).last() == '1'
                    )
                )
        }
        return todoModel.apply { sortBy { it.date } }
    }

    fun deleteTodoList(todoListString: String): List<TodoModel> {
        val oldList = JSONArray(sharedPreferenceManager.todoList)
        val newList = JsonArray()
        val newModel = mutableListOf<TodoModel>()
        for (i in 0 until oldList.length()) {
            if (todoListString != oldList.optString(i)) {
                newList.add(oldList.optString(i))
                newModel.add(
                    convertToModel(oldList.optString(i), oldList.optString(i).last() == '1')
                )
            }
        }

        // sharedPreferenceManager Update
        sharedPreferenceManager.todoList = newList.toString()
        return newModel.apply { sortBy { it.date } }
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

        // sharedPreferenceManager Update
        sharedPreferenceManager.todoList = newList.toString()
        return newModel.apply { sortBy { it.date } }
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