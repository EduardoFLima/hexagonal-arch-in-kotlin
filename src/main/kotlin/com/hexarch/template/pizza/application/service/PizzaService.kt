package com.hexarch.template.pizza.application.service

import com.hexarch.template.pizza.domain.model.entity.Pizza
import com.hexarch.template.pizza.port.inbound.CreatePizzaUseCase
import com.hexarch.template.pizza.port.inbound.RetrievePizzaUseCase
import com.hexarch.template.pizza.port.outbound.PizzaPersistencePort

class PizzaService(
    private val pizzaPersistencePort: PizzaPersistencePort
) : RetrievePizzaUseCase, CreatePizzaUseCase {
    override fun get(name: String): Pizza? {
        return pizzaPersistencePort.getPizzaByName(name)
    }

    override fun create(pizza: Pizza): Pizza? {
        return pizzaPersistencePort.persistPizza(pizza)
    }
}