package com.hexarch.template.pizza.application.service

import com.hexarch.template.pizza.application.dto.PizzaDto
import com.hexarch.template.pizza.domain.model.entity.Pizza
import com.hexarch.template.pizza.port.inbound.CreatePizzaUseCase
import com.hexarch.template.pizza.port.inbound.RetrievePizzaUseCase
import com.hexarch.template.pizza.port.outbound.PizzaPersistencePort

class PizzaService(
    private val pizzaPersistencePort: PizzaPersistencePort,
) : RetrievePizzaUseCase, CreatePizzaUseCase {
    override fun get(name: String): PizzaDto {
        pizzaPersistencePort.getPizzaByName(name)?.let { return PizzaDto.fromPizza(it) }

        return PizzaDto.withErrors(listOf("Pizza not found"))
    }

    override fun create(pizza: Pizza): PizzaDto {

        pizza.validate()?.let { errorMessage -> return PizzaDto.fromPizza(pizza, listOf(errorMessage)) }

        return pizzaPersistencePort.persistPizza(pizza).let { PizzaDto.fromPizza(it) }
    }

}