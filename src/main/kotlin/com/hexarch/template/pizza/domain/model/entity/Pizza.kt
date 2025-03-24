package com.hexarch.template.pizza.domain.model.entity

import com.hexarch.template.pizza.domain.model.value.PizzaType
import java.util.*

data class Pizza(
    val id: UUID? = null,
    val name: String,
    val type: PizzaType,
) {
    fun errorInName(): String? {
        if (name.length > 100) {
            return "Pizza name is too long"
        }

        if (name.length < 3) {
            return "Pizza name is too short"
        }

        return null
    }
}
