package com.example.dailynews.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailynews.R
import com.example.dailynews.adapter.TodoAdapter
import com.example.dailynews.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.dailynews.databinding.FragmentTodoBinding
import com.example.dailynews.model.TodoModel
import java.time.LocalDate

class TodoFragment : BaseFragment(R.layout.fragment_todo) {

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<TodoViewModel>()

    private val todoAdapter by lazy { TodoAdapter(requireContext(), this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBinding()
        setObserver()
        todoAdapter.initList(viewModel.initTodoList())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setBinding() {
        with(binding) {
            with(todoRecyclerView) {
                adapter = todoAdapter.also { it.initList(mutableListOf()) }
                layoutManager = LinearLayoutManager(requireContext())
            }

            with(todoAddButton) {
                setOnClickListener {
                    resetTodoLayout()
                }
            }

            with(todoCancelButton) {
                setOnClickListener {
                    resetDatePicker()
                    resetTodoLayout(false)
                }
            }
            with(todoConfirmButton) {
                setOnClickListener {
                    if (binding.todoTitleEditText.text.toString().isEmpty()) {
                        setTitleTodoDialog()
                    } else {
                        val date = binding.todoDatePicker.year.toString() +
                                (if (binding.todoDatePicker.month + 1 < 10) "0" else "") +
                                (binding.todoDatePicker.month + 1).toString() +
                                (if (binding.todoDatePicker.dayOfMonth < 10) "0" else "") +
                                binding.todoDatePicker.dayOfMonth
                        val newModel = TodoModel(
                            title = binding.todoTitleEditText.text.toString(),
                            message = binding.todoMessageEditText.text.toString(),
                            date = date,
                            isComplete = false
                        )
                        // null 을 반환시 동일 내용 입력 메시지 출력 list 반환시 저장 완료
                        viewModel.saveTodoList(newModel).also {
                            if (it == null) sameTitleTodoDialog() else todoAdapter.initList(it)
                        }
                        resetDatePicker()
                        resetTodoLayout(false)
                    }
                }
            }
        }
    }

    private fun setObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.isListEmpty.observe(viewLifecycleOwner) {
                binding.todoEmptyTextView.visibility = if (it == true) View.VISIBLE else View.GONE
            }
        }
    }

    fun changeIsCompleteTodo(todoCode: String) {
        todoAdapter.initList(viewModel.switchTodo(todoCode, todoCode.last() != '1'))
    }

    // 제목 미입력 팝업
    private fun setTitleTodoDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("제목을 입력해주세요").setPositiveButton("확인") { _, _ ->
        }
        builder.show()
    }

    // 제목 미입력 팝업
    private fun sameTitleTodoDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("동일한 항목이 존재합니다").setPositiveButton("확인") { _, _ ->
        }
        builder.show()
    }

    // 알람 삭제 팝업
    fun showCancelTodoDialog(date: String, title: String, message: String, todoCode: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("할 일을 삭제하시겠습니까?").setMessage("$date \n$title \n$message")
            .setPositiveButton("확인") { _, _ ->
                todoAdapter.initList(viewModel.deleteTodoList(todoCode))
            }
            .setNegativeButton("취소") { _, _ ->
            }
        builder.show()
    }

    private fun resetTodoLayout(visibility: Boolean? = true) {
        binding.todoAddLayout.visibility = if (visibility == true) View.VISIBLE else View.GONE
        binding.todoTitleEditText.setText("")
        binding.todoMessageEditText.setText("")
    }

    private fun resetDatePicker() {
        binding.todoDatePicker.init(
            LocalDate.now().year,
            LocalDate.now().monthValue - 1,
            LocalDate.now().dayOfMonth,
            null
        )
    }
}