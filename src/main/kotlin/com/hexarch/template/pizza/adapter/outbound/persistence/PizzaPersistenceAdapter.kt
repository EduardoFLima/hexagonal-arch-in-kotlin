package com.hexarch.template.pizza.adapter.outbound.persistence

import com.hexarch.template.pizza.domain.model.entity.Pizza
import com.hexarch.template.pizza.port.outbound.PizzaPersistencePort
import org.springframework.stereotype.Component

@Component
class PizzaPersistenceAdapter(val pizzaRepository: PizzaRepository) : PizzaPersistencePort {
    override fun getPizzaByName(name: String): Pizza? {
        TODO("Not yet implemented")
    }

    override fun persistPizza(pizza: Pizza): Pizza {
        return pizzaRepository.save(pizza)
    }
}