package com.hexarch.template.pizza.domain.model.entity

import com.hexarch.template.pizza.domain.model.value.PizzaType

data class Pizza(
    val name: String,
    val type: PizzaType,
) {
    fun validate(): String? {
        if (name.length > 100) {
            return "Pizza name is too long"
        }

        if (name.length < 3) {
            return "Pizza name is too short"
        }

        return null
    }
}
