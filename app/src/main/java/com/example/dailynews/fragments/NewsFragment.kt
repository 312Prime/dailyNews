package com.example.dailynews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailynews.R
import com.example.dailynews.adapter.NewsAdapter
import com.example.dailynews.base.BaseFragment
import com.example.dailynews.databinding.FragmentNewsBinding
import com.example.dailynews.tools.logger.Logger
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : BaseFragment(R.layout.fragment_news) {

    private var _binding: FragmentNewsBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModel<NewsViewModel>()

    private val newsAdapter by lazy { NewsAdapter(requireContext(), this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBinding()
        setObserver()
        viewModel.getNews("all")
    }

    override fun onResume() {
        super.onResume()
        closeWebView()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setBinding() {
        with(binding) {
            with(newsWebViewLayout) {
                visibility = View.GONE
            }

            with(newsRecyclerView) {
                adapter = newsAdapter.also { it.initList(mutableListOf()) }
                layoutManager = LinearLayoutManager(requireContext())
            }

            with(newsWebViewCloseButton) {
                setOnClickListener {
                    closeWebView()
                }
            }
        }
    }

    private fun setObserver() {
        viewModel.responseNews.observe(
            viewLifecycleOwner, Observer { news ->
                newsAdapter.initList(news.items)
            }
        )
    }

    fun openWebView(url: String) {
        binding.newsWebViewLayout.visibility = View.VISIBLE
        binding.newsWebView.webViewClient
        binding.newsWebView.loadUrl(url)
    }
    private fun closeWebView(){
        binding.newsWebViewLayout.visibility = View.GONE
    }
}