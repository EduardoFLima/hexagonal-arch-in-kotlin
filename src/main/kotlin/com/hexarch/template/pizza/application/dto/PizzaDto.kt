package com.hexarch.template.pizza.application.dto

import com.hexarch.template.pizza.application.error.Error
import com.hexarch.template.pizza.domain.model.entity.Pizza
import com.hexarch.template.pizza.domain.model.value.PizzaType

data class PizzaDto(
    val name: String? = null,
    val type: PizzaType? = null,
    val errors: List<Error<Pizza, *>> = emptyList(),
) {
    companion object {
        fun fromPizza(pizza: Pizza, errors: List<Error<Pizza, *>> = emptyList()): PizzaDto {
            return PizzaDto(
                name = pizza.name,
                type = pizza.type,
                errors = errors
            )
        }

        fun withErrors(errors: List<Error<Pizza, *>>): PizzaDto {
            return PizzaDto(errors = errors)
        }
    }

    fun toPizza(): Pizza {
        return Pizza(
            name = name!!,
            type = type!!
        )
    }
}
