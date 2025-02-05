package com.example.presentation.viewModel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ChromiaRepository
import com.example.data.TodoItem
import com.example.domain.usecase.todo.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class TodoViewModel(
    private val getTodosUseCase: GetTodosUseCase,
    private val createTodoUseCase: CreateTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val toggleTodoUseCase: ToggleTodoUseCase
) : ViewModel() {

    private val chromiaRepository = ChromiaRepository.getInstance()
    
    private val _accounts = MutableStateFlow<List<String>>(emptyList())
    val accounts: StateFlow<List<String>> = _accounts
    
    private val _currentSession = MutableStateFlow<String?>(null)
    val currentSession: StateFlow<String?> = _currentSession
    
    private val _todos = MutableLiveData<List<TodoItem>>()
    val todos: LiveData<List<TodoItem>> = _todos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var lastOperation: (() -> Unit)? = null

    @RequiresApi(Build.VERSION_CODES.M)
    fun initializeChromia(context: Context) {
        executeWithLoading {
            try {
                chromiaRepository.initialize(context)
                loadAccounts()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to initialize Chromia"
            }
        }
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    fun generateKeyPair() {
        executeWithLoading {
            try {
                chromiaRepository.generateAndStoreKeyPair()
                loadAccounts()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to generate keypair"
            }
        }
    }
    
    private fun loadAccounts() {
        executeWithLoading {
            try {
                _accounts.value = chromiaRepository.getAccounts()
                if (_accounts.value.isNotEmpty()) {
                    // Automatically create session for the first account
                    createSession(_accounts.value.first())
                }
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load accounts"
            }
        }
    }
    
    fun createSession(account: String) {
        executeWithLoading {
            try {
                val sessionId = chromiaRepository.createSession(account)
                _currentSession.value = sessionId
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to create session"
            }
        }
    }

    fun loadTodos() {
        executeWithLoading {
            getTodosUseCase()
                .onSuccess { todos ->
                    _todos.value = todos
                    _error.value = null
                }
                .onFailure { e ->
                    _error.value = e.message ?: "Failed to load todos"
                }
        }
    }

    fun createTodo(todo: TodoItem) {
        executeWithLoading {
            createTodoUseCase(todo)
                .onSuccess {
                    loadTodos()
                    _error.value = null
                }
                .onFailure { e ->
                    _error.value = e.message ?: "Failed to create todo"
                }
        }
    }

    fun updateTodo(todo: TodoItem) {
        executeWithLoading {
            updateTodoUseCase(todo)
                .onSuccess {
                    loadTodos()
                    _error.value = null
                }
                .onFailure { e ->
                    _error.value = e.message ?: "Failed to update todo"
                }
        }
    }

    fun deleteTodo(id: String) {
        executeWithLoading {
            deleteTodoUseCase(id)
                .onSuccess {
                    loadTodos()
                    _error.value = null
                }
                .onFailure { e ->
                    _error.value = e.message ?: "Failed to delete todo"
                }
        }
    }

    fun toggleTodo(id: String) {
        executeWithLoading {
            toggleTodoUseCase(id)
                .onSuccess {
                    loadTodos()
                    _error.value = null
                }
                .onFailure { e ->
                    _error.value = e.message ?: "Failed to toggle todo"
                }
        }
    }

    fun retry() {
        lastOperation?.invoke()
    }

    private fun executeWithLoading(operation: suspend () -> Unit) {
        lastOperation = { executeWithLoading(operation) }
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null
                operation()
            } finally {
                _loading.value = false
            }
        }
    }
} 