package com.example.domain

import com.example.data.TodoItem
import com.example.data.TodoRepository

class UpdateTodoUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(todo: TodoItem) = repository.updateTodo(todo)
} 