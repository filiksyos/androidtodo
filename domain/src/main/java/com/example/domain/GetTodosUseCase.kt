package com.example.domain

import com.example.data.TodoItem
import com.example.data.TodoRepository

class GetTodosUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(): List<TodoItem> = repository.getTodos()
} 