package com.hexarch.template.pizza.port.outbound

import com.hexarch.template.pizza.domain.model.entity.Pizza
import java.util.*

interface PizzaPersistencePort {
    fun getPizza(id: UUID): Pizza?

    fun persistPizza(pizza: Pizza): Pizza
}