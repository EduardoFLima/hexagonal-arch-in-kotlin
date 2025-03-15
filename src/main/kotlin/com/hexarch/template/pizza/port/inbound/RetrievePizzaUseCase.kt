package com.hexarch.template.pizza.port.inbound

import com.hexarch.template.pizza.application.dto.PizzaDto

interface RetrievePizzaUseCase {
    fun get(name: String): PizzaDto
}