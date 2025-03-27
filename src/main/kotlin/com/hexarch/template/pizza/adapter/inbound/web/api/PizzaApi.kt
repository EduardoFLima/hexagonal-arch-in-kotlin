package com.hexarch.template.pizza.adapter.inbound.web.api

import com.hexarch.template.pizza.application.dto.PizzaDto
import java.util.UUID

interface PizzaApi {

    fun createPizza(pizzaDto: PizzaDto): PizzaDto

    fun getPizza(pizzaId: UUID): PizzaDto
}