package com.hexarch.template.pizza.domain.model.entity

import com.hexarch.template.pizza.domain.model.value.PizzaType

data class Pizza(
    val name: String,
    val type: PizzaType,
)
