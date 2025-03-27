package com.hexarch.template.pizza.adapter.inbound.web.api

import com.hexarch.template.pizza.application.dto.PizzaDto

interface PizzaApi {

    fun getPizza(pizzaDto: PizzaDto): PizzaDto
}