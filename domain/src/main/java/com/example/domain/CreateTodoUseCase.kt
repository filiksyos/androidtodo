package com.example.domain

import com.example.data.TodoItem
import com.example.data.TodoRepository

class CreateTodoUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(todo: TodoItem) = repository.createTodo(todo)
} 