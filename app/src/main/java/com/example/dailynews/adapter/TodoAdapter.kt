package com.example.dailynews.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dailynews.R
import com.example.dailynews.databinding.ItemTodoListBinding
import com.example.dailynews.fragments.TodoFragment
import com.example.dailynews.model.TodoModel
import com.example.dailynews.tools.logger.Logger

class TodoAdapter(val context: Context, val todoFragment: TodoFragment) :
    RecyclerView.Adapter<TodoAdapter.ListViewHolder>() {

    private var todoItems = mutableListOf<TodoModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoAdapter.ListViewHolder {
        return ListViewHolder(
            binding = ItemTodoListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        todoItems.getOrNull(position)?.let {
            holder.bindViewHolder(it)
        }
    }

    override fun getItemCount(): Int {
        return todoItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initList(newList: List<TodoModel>) {
        with(todoItems) {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: ItemTodoListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // header set need

        @SuppressLint("NotifyDataSetChanged")
        fun bindViewHolder(data: TodoModel) {
            with(binding) {
                todoListTitle.text = data.title
                todoListMessage.text = data.message
                todoListTitle.paintFlags = if (data.isComplete) Paint.STRIKE_THRU_TEXT_FLAG else 0
                todoListTitle.setTextColor(
                    if (data.isComplete) ContextCompat.getColor(context, R.color.grey_600)
                    else ContextCompat.getColor(context, R.color.blue_grey_800)
                )
                todoListDeleteButton.setOnClickListener {
                    todoFragment.showCancelTodoDialog(
                        date =
                        "${data.date.substring(0, 4)}년 " +
                                "${data.date.substring(4, 6)}월 " +
                                "${data.date.substring(6, 8)}일",
                        title = data.title,
                        message = data.message,
                        todoCode = data.date + data.title + data.message + if (data.isComplete) 1 else 0
                    )
                }
                todoLayout.setOnClickListener {
                    todoFragment.changeIsCompleteTodo(
                        data.date + data.title + data.message + if (data.isComplete) 1 else 0
                    )
                }
            }
        }
    }
}