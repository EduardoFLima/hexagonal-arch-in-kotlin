package com.hexarch.template.pizza.port.inbound

import com.hexarch.template.pizza.domain.model.entity.Pizza

interface RetrievePizzaUseCase {
    fun get(name: String): Pizza?
}