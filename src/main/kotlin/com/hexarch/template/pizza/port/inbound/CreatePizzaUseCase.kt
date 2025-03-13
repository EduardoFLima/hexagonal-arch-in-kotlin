package com.hexarch.template.pizza.port.inbound

import com.hexarch.template.pizza.domain.model.entity.Pizza

interface CreatePizzaUseCase {
    fun create(pizza: Pizza): Pizza?
}