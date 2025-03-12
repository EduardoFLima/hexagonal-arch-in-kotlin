package com.hexarch.template.pizza.application.service

import com.hexarch.template.pizza.domain.model.entity.Pizza
import com.hexarch.template.pizza.port.inbound.RetrievePizzaUseCase
import com.hexarch.template.pizza.port.outbound.PizzaPersistencePort

class PizzaService(
    val pizzaPersistencePort: PizzaPersistencePort
) : RetrievePizzaUseCase {
    override fun get(name: String): Pizza? {
        return pizzaPersistencePort.getPizzaByName(name)
    }
}