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
import com.example.domain.CreateTodoUseCase
import com.example.domain.GetTodosUseCase
import com.example.domain.UpdateTodoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

sealed class AccountCreationState {
    object Initial : AccountCreationState()
    object Success : AccountCreationState()
    data class Error(val message: String) : AccountCreationState()
}

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

    private val _accountCreationState = MutableStateFlow<AccountCreationState>(AccountCreationState.Initial)
    val accountCreationState: StateFlow<AccountCreationState> = _accountCreationState.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.M)
    fun initializeChromia(context: Context) {
        viewModelScope.launch {
            try {
                Timber.d("Initializing Chromia...")
                chromiaRepository.initialize(context)
                _error.value = null
                Timber.d("Chromia initialized successfully")
            } catch (e: Exception) {
                val errorMsg = "Failed to initialize Chromia: ${e.message}"
                Timber.e(e, errorMsg)
                _error.value = errorMsg
                _accountCreationState.value = AccountCreationState.Error(errorMsg)
            }
        }
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    fun generateKeyPair() {
        viewModelScope.launch {
            try {
                Timber.d("Generating key pair...")
                chromiaRepository.generateAndStoreKeyPair()
                _error.value = null
                _accountCreationState.value = AccountCreationState.Success
                Timber.d("Key pair generated successfully")
                
                // Only try to load accounts after successful account creation
                loadAccounts()
            } catch (e: Exception) {
                val errorMsg = "Failed to generate key pair: ${e.message}"
                Timber.e(e, errorMsg)
                _error.value = errorMsg
                _accountCreationState.value = AccountCreationState.Error(errorMsg)
            }
        }
    }
    
    private fun loadAccounts() {
        viewModelScope.launch {
            try {
                Timber.d("Loading accounts...")
                _accounts.value = chromiaRepository.getAccounts()
                if (_accounts.value.isNotEmpty()) {
                    Timber.d("Found ${_accounts.value.size} accounts")
                    // Only create session if we actually have accounts
                    createSession(_accounts.value.first())
                } else {
                    Timber.d("No accounts found")
                }
                _error.value = null
            } catch (e: Exception) {
                val errorMsg = "Failed to load accounts: ${e.message}"
                Timber.e(e, errorMsg)
                _error.value = errorMsg
            }
        }
    }
    
    private fun createSession(account: String) {
        viewModelScope.launch {
            try {
                Timber.d("Creating session for account: $account")
                val sessionId = chromiaRepository.createSession(account)
                _currentSession.value = sessionId
                Timber.d("Session created successfully: $sessionId")
                _error.value = null
            } catch (e: Exception) {
                val errorMsg = "Failed to create session: ${e.message}"
                Timber.e(e, errorMsg)
                _error.value = errorMsg
            }
        }
    }

    fun loadTodos() {
        viewModelScope.launch {
            _loading.value = true
            try {
                Timber.d("Loading todos...")
                _todos.value = getTodosUseCase()
                Timber.d("Loaded ${_todos.value?.size ?: 0} todos")
                _error.value = null
            } catch (e: Exception) {
                val errorMsg = "Failed to load todos: ${e.message}"
                Timber.e(e, errorMsg)
                _error.value = errorMsg
            } finally {
                _loading.value = false
            }
        }
    }

    fun createTodo(todo: TodoItem) {
        viewModelScope.launch {
            try {
                Timber.d("Creating todo: ${todo.title}")
                createTodoUseCase(todo)
                loadTodos()
                Timber.d("Todo created successfully")
                _error.value = null
            } catch (e: Exception) {
                val errorMsg = "Failed to create todo: ${e.message}"
                Timber.e(e, errorMsg)
                _error.value = errorMsg
            }
        }
    }

    fun updateTodo(todo: TodoItem) {
        viewModelScope.launch {
            try {
                Timber.d("Updating todo: ${todo.id}")
                updateTodoUseCase(todo)
                loadTodos()
                Timber.d("Todo updated successfully")
                _error.value = null
            } catch (e: Exception) {
                val errorMsg = "Failed to update todo: ${e.message}"
                Timber.e(e, errorMsg)
                _error.value = errorMsg
            }
        }
    }
} 