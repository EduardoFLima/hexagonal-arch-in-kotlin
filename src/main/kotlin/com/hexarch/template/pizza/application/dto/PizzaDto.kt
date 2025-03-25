package com.hexarch.template.pizza.application.dto

import com.hexarch.template.pizza.application.error.Error
import com.hexarch.template.pizza.domain.model.entity.Pizza
import com.hexarch.template.pizza.domain.model.value.PizzaType
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.*

data class PizzaDto(
    val id: UUID? = null,
    @field:NotEmpty
    val name: String? = null,
    @field:NotNull
    val type: PizzaType? = null,
    val errors: List<Error<Pizza, *>> = emptyList(),
) {
    companion object {
        fun fromPizza(pizza: Pizza, errors: List<Error<Pizza, *>> = emptyList()): PizzaDto {
            return PizzaDto(
                id = pizza.id,
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
            id = id,
            name = name!!,
            type = type!!
        )
    }
}
