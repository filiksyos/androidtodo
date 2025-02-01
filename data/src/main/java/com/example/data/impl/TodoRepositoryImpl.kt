package com.example.data.impl

import com.example.data.TodoItem
import com.example.data.TodoRepository

class TodoRepositoryImpl : TodoRepository {
    // Temporary in-memory storage until we implement Chromia integration
    private val todos = mutableListOf<TodoItem>()

    override suspend fun getTodos(): List<TodoItem> = todos.toList()

    override suspend fun createTodo(todo: TodoItem) {
        todos.add(todo)
    }

    override suspend fun updateTodo(todo: TodoItem) {
        val index = todos.indexOfFirst { it.id == todo.id }
        if (index != -1) {
            todos[index] = todo
        }
    }

    override suspend fun deleteTodo(id: String) {
        todos.removeIf { it.id == id }
    }

    override suspend fun toggleTodo(id: String) {
        val index = todos.indexOfFirst { it.id == id }
        if (index != -1) {
            todos[index] = todos[index].copy(completed = !todos[index].completed)
        }
    }
} 