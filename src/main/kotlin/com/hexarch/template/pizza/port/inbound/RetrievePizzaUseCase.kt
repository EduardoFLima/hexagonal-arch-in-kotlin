package com.hexarch.template.pizza.port.inbound

import com.hexarch.template.pizza.application.dto.PizzaDto
import java.util.UUID

interface RetrievePizzaUseCase {
    fun get(id: UUID): PizzaDto
}