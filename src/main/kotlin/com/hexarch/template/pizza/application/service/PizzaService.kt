package com.hexarch.template.pizza.application.service

import com.hexarch.template.pizza.domain.model.entity.Pizza
import com.hexarch.template.pizza.port.inbound.RetrievePizzaUseCase

class PizzaService : RetrievePizzaUseCase {
    override fun get(name: String): Pizza {
        TODO("Not yet implemented")
    }
}