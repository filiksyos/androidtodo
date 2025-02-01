package com.example.data

interface TodoRepository {
    suspend fun getTodos(): List<TodoItem>
    suspend fun createTodo(todo: TodoItem)
    suspend fun updateTodo(todo: TodoItem)
    suspend fun deleteTodo(id: String)
    suspend fun toggleTodo(id: String)
} 