package com.example.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.R
import com.example.presentation.adapter.TodoAdapter
import com.example.presentation.viewModel.TodoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class TodoListFragment : Fragment() {

    private val viewModel: TodoViewModel by viewModel()
    private lateinit var adapter: TodoAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.todoRecyclerView)
        addButton = view.findViewById(R.id.addTodoButton)

        setupRecyclerView()
        setupClickListeners()

        viewModel.todos.observe(viewLifecycleOwner) { todos ->
            adapter.submitList(todos)
        }
        viewModel.loadTodos()
    }

    private fun setupRecyclerView() {
        adapter = TodoAdapter(
            onToggleClick = { todo ->
                viewModel.updateTodo(todo.copy(completed = !todo.completed))
            },
            onItemClick = { todo ->
                // TODO: Show edit dialog
            }
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@TodoListFragment.adapter
        }
    }

    private fun setupClickListeners() {
        addButton.setOnClickListener {
            // TODO: Show add todo dialog
        }
    }
} 