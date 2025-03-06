package com.hexarch.template.pizza.application.service

import com.hexarch.template.pizza.domain.model.value.PizzaType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class PizzaServiceTest {

    @InjectMocks
    private lateinit var pizzaService: PizzaService

    @Test
    fun `SHOULD get a pizza WHEN it exists`() {
        val name = "Margherita"

        val pizza = pizzaService.get(name)

        assertThat(pizza).isNotNull
        assertThat(pizza.name).isEqualTo(name)
        assertThat(pizza.type).isEqualTo(PizzaType.NEAPOLITAN)

    }
}