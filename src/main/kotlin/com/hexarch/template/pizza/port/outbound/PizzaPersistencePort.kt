package com.hexarch.template.pizza.port.outbound

import com.hexarch.template.pizza.domain.model.entity.Pizza

interface PizzaPersistencePort {
    fun getPizzaByName(name: String): Pizza?

    fun persistPizza(pizza: Pizza): Pizza
}