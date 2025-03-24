package com.hexarch.template.pizza.adapter.inbound.web.controller

import com.hexarch.template.pizza.adapter.inbound.web.api.PizzaApi
import com.hexarch.template.pizza.application.dto.PizzaDto
import com.hexarch.template.pizza.application.service.PizzaService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class PizzaController(val pizzaService: PizzaService) : PizzaApi {

    @PostMapping("/v1/pizzas")
    @ResponseStatus(HttpStatus.CREATED)
    override fun getPizza(
        @RequestBody pizzaDto: PizzaDto,
    ): PizzaDto {
        return pizzaService.create(pizzaDto)
    }
}