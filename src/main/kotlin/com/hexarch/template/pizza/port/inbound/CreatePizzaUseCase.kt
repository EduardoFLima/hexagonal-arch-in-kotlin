package com.hexarch.template.pizza.port.inbound

import com.hexarch.template.pizza.application.dto.PizzaDto

interface CreatePizzaUseCase {
    fun create(pizzaDto: PizzaDto): PizzaDto
}