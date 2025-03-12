package com.hexarch.template.pizza.application.service

import com.hexarch.template.pizza.domain.model.entity.Pizza
import com.hexarch.template.pizza.domain.model.value.PizzaType.NEAPOLITAN
import com.hexarch.template.pizza.port.outbound.PizzaPersistencePort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.eq
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class PizzaServiceTest {

    @InjectMocks
    private lateinit var pizzaService: PizzaService

    @Mock
    private lateinit var pizzaPersistencePort: PizzaPersistencePort

    @Test
    fun `SHOULD get a pizza WHEN it exists in persistence layer`() {
        val name = "Margherita"

        `when`(pizzaPersistencePort.getPizzaByName(eq(name))).thenReturn(Pizza(name, NEAPOLITAN))

        val pizza = pizzaService.get(name)

        assertThat(pizza).isNotNull
        assertThat(pizza?.name).isEqualTo(name)
        assertThat(pizza?.type).isEqualTo(NEAPOLITAN)
    }
}