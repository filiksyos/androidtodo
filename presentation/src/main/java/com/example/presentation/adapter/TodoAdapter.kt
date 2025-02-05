package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.TodoItem
import com.example.presentation.R
import com.example.presentation.databinding.ItemTodoBinding

class TodoAdapter(
    private val onTodoClick: (TodoItem) -> Unit,
    private val onTodoToggle: (TodoItem) -> Unit,
    private val onTodoEdit: (TodoItem) -> Unit,
    private val onTodoDelete: (TodoItem) -> Unit
) : ListAdapter<TodoItem, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TodoViewHolder(
        private val binding: ItemTodoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onTodoClick(getItem(position))
                }
            }

            binding.todoCheckbox.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onTodoToggle(getItem(position))
                }
            }

            binding.menuButton.setOnClickListener { view ->
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    showPopupMenu(view, getItem(position))
                }
            }
        }

        private fun showPopupMenu(view: android.view.View, todo: TodoItem) {
            PopupMenu(view.context, view).apply {
                menuInflater.inflate(R.menu.todo_item_menu, menu)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.action_edit -> {
                            onTodoEdit(todo)
                            true
                        }
                        R.id.action_delete -> {
                            onTodoDelete(todo)
                            true
                        }
                        else -> false
                    }
                }
                show()
            }
        }

        fun bind(todo: TodoItem) {
            binding.apply {
                todoTitle.text = todo.title
                todoDescription.text = todo.description
                todoDueDate.text = todo.dueDate
                todoCheckbox.isChecked = todo.completed
            }
        }
    }

    private class TodoDiffCallback : DiffUtil.ItemCallback<TodoItem>() {
        override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
            return oldItem == newItem
        }
    }
} 