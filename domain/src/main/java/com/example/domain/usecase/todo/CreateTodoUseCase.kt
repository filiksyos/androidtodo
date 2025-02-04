package com.example.domain.usecase.todo

import com.example.data.ChromiaRepository
import com.example.data.TodoItem
import com.example.domain.base.UseCase

class CreateTodoUseCase(
    private val repository: ChromiaRepository
) : UseCase<TodoItem, Unit> {
    
    override suspend fun invoke(params: TodoItem): Result<Unit> = runCatching {
        repository.createTodo(params)
    }
} 