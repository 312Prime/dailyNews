package com.example.dailynews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dailynews.R
import com.example.dailynews.base.BaseFragment
import com.example.dailynews.databinding.FragmentNewsBinding

class NewsFragment : BaseFragment(R.layout.fragment_news) {

    private var _binding: FragmentNewsBinding? = null

    private val binding get() = _binding!!

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
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setBinding() {
        with(binding) {}
    }
}