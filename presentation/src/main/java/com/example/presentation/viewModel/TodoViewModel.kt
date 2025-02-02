package com.example.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ChromiaRepository
import com.example.data.TodoItem
import com.example.domain.CreateTodoUseCase
import com.example.domain.GetTodosUseCase
import com.example.domain.UpdateTodoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.postchain.crypto.KeyPair
import java.util.UUID

class TodoViewModel(
    private val getTodosUseCase: GetTodosUseCase,
    private val createTodoUseCase: CreateTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase
) : ViewModel() {

    private val chromiaRepository = ChromiaRepository.getInstance()
    
    private val _accounts = MutableStateFlow<List<String>>(emptyList())
    val accounts: StateFlow<List<String>> = _accounts
    
    private val _currentSession = MutableStateFlow<String?>(null)
    val currentSession: StateFlow<String?> = _currentSession
    
    private val _todos = MutableLiveData<List<TodoItem>?>()
    val todos: LiveData<List<TodoItem>> get() = _todos as LiveData<List<TodoItem>>

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun initializeChromia(nodeUrl: String, brid: String, keyPair: KeyPair) {
        viewModelScope.launch {
            try {
                chromiaRepository.initialize(nodeUrl, brid, keyPair)
                loadAccounts()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to initialize Chromia"
            }
        }
    }
    
    private fun loadAccounts() {
        viewModelScope.launch {
            try {
                _accounts.value = chromiaRepository.getAccounts()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load accounts"
            }
        }
    }
    
    fun createSession(account: String) {
        viewModelScope.launch {
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
        viewModelScope.launch {
            _loading.value = true
            try {
                _todos.value = getTodosUseCase()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load todos"
            } finally {
                _loading.value = false
            }
        }
    }

    fun createTodo(todo: TodoItem) {
        viewModelScope.launch {
            try {
                createTodoUseCase(todo)
                loadTodos()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to create todo"
            }
        }
    }

    fun updateTodo(todo: TodoItem) {
        viewModelScope.launch {
            try {
                updateTodoUseCase(todo)
                loadTodos()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to update todo"
            }
        }
    }
} 