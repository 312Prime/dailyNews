package com.da312.dailynews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.da312.dailynews.R
import com.da312.dailynews.adapter.NewsAdapter
import com.da312.dailynews.base.BaseFragment
import com.da312.dailynews.databinding.FragmentNewsBinding
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
        // 외부 인터넷 앱을 통하는 경우 웹뷰가 열려있는 현상 때문에 설정
        closeWebView()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setBinding() {
        with(binding) {
            with(newsLoadingLottie) {
                playAnimation()
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
        lifecycleScope.launchWhenStarted {
            viewModel.loading.observe(viewLifecycleOwner) {
                binding.newsLoadingFrameLayout.isVisible = it
            }

            viewModel.responseNews.observe(viewLifecycleOwner) { news ->
                if (news != null) newsAdapter.initList(news.items)
            }
        }
    }

    // 웹뷰 열기
    fun openWebView(url: String) {
        binding.newsWebViewLayout.visibility = View.VISIBLE
        binding.newsWebView.webViewClient
        binding.newsWebView.loadUrl(url)
    }

    // 웹뷰 닫기
    private fun closeWebView() {
        binding.newsWebViewLayout.visibility = View.GONE
    }
}