package com.example.dailynews.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailynews.databinding.ItemTodoListBinding
import com.example.dailynews.model.TodoModel

class TodoAdapter(val context: Context) : RecyclerView.Adapter<TodoAdapter.ListViewHolder>() {

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

    inner class ListViewHolder(private val binding: ItemTodoListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViewHolder(data: TodoModel) {
            with(binding) {

            }
        }
    }
}