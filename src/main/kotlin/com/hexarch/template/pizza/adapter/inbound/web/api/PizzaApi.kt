package com.hexarch.template.pizza.adapter.inbound.web.api

import com.hexarch.template.pizza.application.dto.PizzaDto
import org.springframework.web.bind.annotation.PostMapping

interface PizzaApi {

    @PostMapping("/v1/pizzas")
    fun getPizza(pizzaDto: PizzaDto): PizzaDto
}