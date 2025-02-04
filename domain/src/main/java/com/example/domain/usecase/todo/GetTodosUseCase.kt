package com.example.domain.usecase.todo

import com.example.data.ChromiaRepository
import com.example.data.TodoItem
import com.example.domain.base.NoParamsUseCase

class GetTodosUseCase(
    private val repository: ChromiaRepository
) : NoParamsUseCase<List<TodoItem>> {
    
    override suspend fun invoke(): Result<List<TodoItem>> = runCatching {
        repository.getTodos()
    }
} 