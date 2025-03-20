package com.hexarch.template.pizza.application.error

import kotlin.reflect.KProperty1

data class BusinessError<T : Any, R>(
    override val property: KProperty1<T, R>,
    override val errorCode: ErrorCode,
    override val errorMessage: String,
) : Error<T, R>