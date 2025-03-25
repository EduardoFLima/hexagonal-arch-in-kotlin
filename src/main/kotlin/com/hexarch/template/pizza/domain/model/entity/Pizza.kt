package com.hexarch.template.pizza.domain.model.entity

import com.hexarch.template.pizza.domain.model.value.PizzaType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.util.*

@Entity
data class Pizza(
    @Id @GeneratedValue val id: UUID? = null,
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
