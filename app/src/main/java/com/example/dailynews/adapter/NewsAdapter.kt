package com.example.dailynews.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailynews.databinding.ItemNewsListBinding
import com.example.dailynews.fragments.NewsFragment
import com.example.dailynews.model.NewsItemsModel

class NewsAdapter(val context: Context,val newsFragment: NewsFragment) : RecyclerView.Adapter<NewsAdapter.ListViewHolder>() {

    private var newsItems = mutableListOf<NewsItemsModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsAdapter.ListViewHolder {
        return ListViewHolder(
            binding = ItemNewsListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        newsItems.getOrNull(position)?.let {
            holder.bindViewHolder(it)

            holder.itemView.setOnClickListener {
                newsFragment.openWebView(newsItems[position].link)
            }
        }
    }

    override fun getItemCount(): Int {
        return newsItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initList(newList: List<NewsItemsModel>) {
        with(newsItems) {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: ItemNewsListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViewHolder(data: NewsItemsModel) {
            with(binding) {
                newsDetailTitle.text = Html.fromHtml(data.title).toString()
                newsDetailBody.text = Html.fromHtml(data.description).toString()
                newsDetailDate.text = data.pubDate.substring(0, 25)
            }
        }
    }

}