package com.example.dailynews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dailynews.R
import com.example.dailynews.adapter.TodoAdapter
import com.example.dailynews.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.dailynews.databinding.FragmentTodoBinding

class TodoFragment : BaseFragment(R.layout.fragment_todo) {

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<TodoViewModel>()

    private val todoAdapter by lazy { TodoAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(
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
        with(binding) {

        }
    }
}