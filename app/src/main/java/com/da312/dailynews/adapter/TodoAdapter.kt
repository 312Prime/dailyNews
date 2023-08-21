package com.da312.dailynews.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.da312.dailynews.R
import com.da312.dailynews.databinding.ItemTodoHeaderBinding
import com.da312.dailynews.databinding.ItemTodoListBinding
import com.da312.dailynews.fragments.TodoFragment
import com.da312.dailynews.model.TodoModel

class TodoAdapter(val context: Context, val todoFragment: TodoFragment) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var todoItems = mutableListOf<HeaderItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                HeaderViewHolder(
                    binding = ItemTodoHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            VIEW_TYPE_ITEM -> {
                ListViewHolder(
                    binding = ItemTodoListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> throw IllegalArgumentException("miss view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        todoItems.getOrNull(position)?.let {
            when (holder) {
                is HeaderViewHolder -> {
                    val headerItem = it as HeaderItem.Header
                    holder.bindViewHolder(headerItem.text)
                }

                is ListViewHolder -> {
                    val listItem = it as HeaderItem.Item
                    holder.bindViewHolder(listItem.todoModel)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return todoItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (todoItems[position]) {
            is HeaderItem.Header -> VIEW_TYPE_HEADER
            is HeaderItem.Item -> VIEW_TYPE_ITEM
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initList(newList: List<TodoModel>) {
        with(todoItems) {
            clear()
            var date = ""
            newList.forEach {
                if (it.date != date) {
                    date = it.date
                    this.add(HeaderItem.Header(date))
                }
                this.add(HeaderItem.Item(it))
            }
        }
        notifyDataSetChanged()
    }

    inner class HeaderViewHolder(private val binding: ItemTodoHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindViewHolder(date: String) {
            binding.todoHeaderDateTextView.text =
                "${date.substring(0, 4)}년 ${date.substring(4, 6)}월 ${date.substring(6, 8)}일"
        }
    }

    inner class ListViewHolder(private val binding: ItemTodoListBinding) :
        RecyclerView.ViewHolder(binding.root) {

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
                todoListDeleteButton.visibility = if (data.isComplete) View.VISIBLE else View.GONE

                // 삭제 버튼 클릭시
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

                // 완료 상태 변경
                todoLayout.setOnClickListener {
                    todoFragment.changeIsCompleteTodo(
                        data.date + data.title + data.message + if (data.isComplete) 1 else 0
                    )
                }
            }
        }
    }

    sealed class HeaderItem {
        data class Header(val text: String) : HeaderItem()
        data class Item(val todoModel: TodoModel) : HeaderItem()
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }
}