package com.example.presentation.dialog

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.data.TodoItem
import com.example.presentation.databinding.DialogTodoBinding
import com.example.presentation.viewModel.TodoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class TodoDialogFragment : DialogFragment() {
    private var _binding: DialogTodoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodoViewModel by viewModel()
    private var todoItem: TodoItem? = null
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    companion object {
        private const val ARG_TODO = "todo"

        fun newInstance(todo: TodoItem? = null): TodoDialogFragment {
            return TodoDialogFragment().apply {
                arguments = Bundle().apply {
                    todo?.let { putParcelable(ARG_TODO, it) }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoItem = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_TODO, TodoItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(ARG_TODO)
        }
        setStyle(STYLE_NORMAL, com.google.android.material.R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupClickListeners()
    }

    private fun setupViews() {
        todoItem?.let { todo ->
            binding.titleInput.setText(todo.title)
            binding.descriptionInput.setText(todo.description)
            binding.dueDateInput.setText(todo.dueDate)
        }
    }

    private fun setupClickListeners() {
        binding.dueDateInput.setOnClickListener {
            showDatePicker()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.saveButton.setOnClickListener {
            saveTodo()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        todoItem?.dueDate?.let {
            try {
                calendar.time = dateFormat.parse(it) ?: Date()
            } catch (e: Exception) {
                // Use current date if parsing fails
            }
        }

        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                calendar.set(year, month, day)
                binding.dueDateInput.setText(dateFormat.format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun saveTodo() {
        val title = binding.titleInput.text.toString().trim()
        if (title.isEmpty()) {
            binding.titleInputLayout.error = "Title is required"
            return
        }

        val todo = TodoItem(
            id = todoItem?.id ?: UUID.randomUUID().toString(),
            owner = "current_user", // This should come from session
            title = title,
            description = binding.descriptionInput.text.toString().trim(),
            dueDate = binding.dueDateInput.text.toString(),
            completed = todoItem?.completed ?: false,
            createdAt = todoItem?.createdAt ?: System.currentTimeMillis().toString()
        )

        if (todoItem == null) {
            viewModel.createTodo(todo)
        } else {
            viewModel.updateTodo(todo)
        }

        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 