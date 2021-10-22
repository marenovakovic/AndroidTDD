package com.example.androidtdd.usecases

fun interface UseCase<Parameters, Result> : suspend (Parameters) -> Result

suspend operator fun <Result> UseCase<Unit, Result>.invoke(): Result = this(Unit)