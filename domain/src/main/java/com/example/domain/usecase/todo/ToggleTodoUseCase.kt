package com.example.domain.usecase.todo

import com.example.data.ChromiaRepository
import com.example.domain.base.UseCase

class ToggleTodoUseCase(
    private val repository: ChromiaRepository
) : UseCase<String, Unit> {
    
    override suspend fun invoke(params: String): Result<Unit> = runCatching {
        repository.toggleTodo(params)
    }
} 