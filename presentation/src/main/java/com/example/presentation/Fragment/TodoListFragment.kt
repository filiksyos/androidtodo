package com.example.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.data.TodoItem
import com.example.presentation.adapter.TodoAdapter
import com.example.presentation.databinding.FragmentTodoListBinding
import com.example.presentation.dialog.TodoDialogFragment
import com.example.presentation.viewModel.TodoViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.UUID

class TodoListFragment : Fragment() {
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodoViewModel by viewModel()
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
        viewModel.loadTodos()
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoAdapter(
            onTodoClick = { /* Show details if needed */ },
            onTodoToggle = { todo ->
                viewModel.toggleTodo(todo.id)
            },
            onTodoEdit = { todo ->
                showEditTodoDialog(todo)
            },
            onTodoDelete = { todo ->
                showDeleteConfirmationDialog(todo)
            }
        )
        
        binding.todoList.adapter = todoAdapter
    }

    private fun setupClickListeners() {
        binding.addTodoButton.setOnClickListener {
            showCreateTodoDialog()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.todos.observe(viewLifecycleOwner) { todos ->
                todos?.let {
                    todoAdapter.submitList(it)
                    updateEmptyState(it.isEmpty())
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collectLatest { error ->
                error?.let {
                    showError(it)
                }
            }
        }
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        binding.emptyStateText.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.todoList.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun showCreateTodoDialog() {
        TodoDialogFragment.newInstance()
            .show(childFragmentManager, "create_todo")
    }

    private fun showEditTodoDialog(todo: TodoItem) {
        TodoDialogFragment.newInstance(todo)
            .show(childFragmentManager, "edit_todo")
    }

    private fun showDeleteConfirmationDialog(todo: TodoItem) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Todo")
            .setMessage("Are you sure you want to delete this todo?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteTodo(todo.id)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showError(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 