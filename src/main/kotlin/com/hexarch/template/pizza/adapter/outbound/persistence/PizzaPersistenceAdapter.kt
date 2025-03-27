package com.hexarch.template.pizza.adapter.outbound.persistence

import com.hexarch.template.pizza.domain.model.entity.Pizza
import com.hexarch.template.pizza.port.outbound.PizzaPersistencePort
import org.springframework.stereotype.Component
import java.util.*

@Component
class PizzaPersistenceAdapter(val pizzaRepository: PizzaRepository) : PizzaPersistencePort {
    override fun getPizza(id: UUID): Pizza? {
        TODO("Not yet implemented")
    }

    override fun persistPizza(pizza: Pizza): Pizza {
        return pizzaRepository.save(pizza)
    }
}