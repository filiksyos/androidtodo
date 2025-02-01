package com.example.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.TodoItem
import com.example.domain.CreateTodoUseCase
import com.example.domain.GetTodosUseCase
import com.example.domain.UpdateTodoUseCase
import kotlinx.coroutines.launch

class TodoViewModel(
    private val getTodosUseCase: GetTodosUseCase,
    private val createTodoUseCase: CreateTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase
) : ViewModel() {

    private val _todos = MutableLiveData<List<TodoItem>>()
    val todos: LiveData<List<TodoItem>> get() = _todos

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun loadTodos() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _todos.value = getTodosUseCase()
            } finally {
                _loading.value = false
            }
        }
    }

    fun createTodo(todo: TodoItem) {
        viewModelScope.launch {
            createTodoUseCase(todo)
            loadTodos()
        }
    }

    fun updateTodo(todo: TodoItem) {
        viewModelScope.launch {
            updateTodoUseCase(todo)
            loadTodos()
        }
    }
} 