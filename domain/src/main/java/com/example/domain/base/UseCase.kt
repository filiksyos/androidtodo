package com.example.domain.base

interface UseCase<in P, R> {
    suspend operator fun invoke(params: P): Result<R>
}

interface NoParamsUseCase<R> {
    suspend operator fun invoke(): Result<R>
} 