package com.hexarch.template.pizza.application.error

import kotlin.reflect.KProperty1

interface Error<T : Any, R> {
    val property: KProperty1<T, R>
    val errorCode: ErrorCode
    val errorMessage: String
}