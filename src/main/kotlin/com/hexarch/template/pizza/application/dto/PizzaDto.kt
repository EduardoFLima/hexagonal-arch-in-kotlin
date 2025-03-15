package com.hexarch.template.pizza.application.dto

import com.hexarch.template.pizza.domain.model.entity.Pizza
import com.hexarch.template.pizza.domain.model.value.PizzaType

data class PizzaDto(
    val name: String? = null,
    val type: PizzaType? = null,
    val errors: List<String> = emptyList(),
) {
    companion object {
        fun fromPizza(pizza: Pizza, errors: List<String> = emptyList()): PizzaDto {
            return PizzaDto(
                name = pizza.name,
                type = pizza.type,
                errors = errors
            )
        }

        fun withErrors(errors: List<String>): PizzaDto {
            return PizzaDto(errors = errors)
        }
    }
}
