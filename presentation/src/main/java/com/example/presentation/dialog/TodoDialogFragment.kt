package com.example.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.data.TodoItem
import com.example.presentation.databinding.DialogTodoBinding
import com.example.presentation.viewModel.TodoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TodoDialogFragment : DialogFragment() {
    private var _binding: DialogTodoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodoViewModel by viewModel()
    private var todoItem: TodoItem? = null

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
            binding.dialogTitle.text = "Edit Task"
            binding.saveButton.text = "Save Changes"
        }
    }

    private fun setupClickListeners() {
        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.saveButton.setOnClickListener {
            saveTodo()
        }
    }

    private fun saveTodo() {
        val title = binding.titleInput.text.toString().trim()
        if (title.isEmpty()) {
            binding.titleInputLayout.error = "Title is required"
            return
        }

        val description = binding.descriptionInput.text.toString().trim()
        
        val todo = TodoItem(
            id = todoItem?.id ?: UUID.randomUUID().toString(),
            owner = "current_user", // This should come from your session/auth system
            title = title,
            description = description,
            completed = todoItem?.completed ?: false,
            createdAt = todoItem?.createdAt ?: System.currentTimeMillis().toString()
        )

        try {
            if (todoItem == null) {
                viewModel.createTodo(todo)
            } else {
                viewModel.updateTodo(todo)
            }
            dismiss()
        } catch (e: Exception) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Error")
                .setMessage("Oops! We encountered a compatibility issue with the Chromia Postchain client.\n\n" +
                          "The current version of the Kotlin Postchain client doesn't fully support some Android API levels. " +
                          "We're working with the Chromia team to update the client.")
                .setPositiveButton("OK", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 