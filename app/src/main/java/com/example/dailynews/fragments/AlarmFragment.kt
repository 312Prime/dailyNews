package com.example.dailynews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dailynews.R
import com.example.dailynews.base.BaseFragment
import com.example.dailynews.databinding.FragmentAlarmBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlarmFragment : BaseFragment(R.layout.fragment_alarm) {

    private var _binding: FragmentAlarmBinding? = null

    private val viewModel by viewModel<AlarmViewModel>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlarmBinding.inflate(
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