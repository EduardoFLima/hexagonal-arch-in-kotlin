package com.hexarch.template.pizza.application.service

import com.hexarch.template.pizza.domain.model.entity.Pizza
import com.hexarch.template.pizza.domain.model.value.PizzaType.NEAPOLITAN
import com.hexarch.template.pizza.port.outbound.PizzaPersistencePort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
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

    @Test
    fun `SHOULD create a pizza WHEN given parameters are valid`() {
        val newPizza = Pizza(name = "Some New Pizza", type = NEAPOLITAN)

        `when`(pizzaPersistencePort.persistPizza(eq(newPizza))).thenReturn(newPizza.copy())

        val createdPizza = pizzaService.create(newPizza)

        assertThat(createdPizza).isNotNull
        assertThat(createdPizza).isNotSameAs(newPizza)
        assertThat(createdPizza).isEqualTo(newPizza)
    }

    @Test
    fun `SHOULD try to create a pizza and return error message WHEN pizza name length is less than 3`() {
        val newPizza = Pizza(name = "AB", type = NEAPOLITAN)

        val createdPizza = pizzaService.create(newPizza)

        assertThat(createdPizza).isNotNull
        assertThat(createdPizza.errors).isNotEmpty
        assertThat(createdPizza.errors).contains("Pizza name is too short")

        verify(pizzaPersistencePort, never()).persistPizza(any())
    }

    @Test
    fun `SHOULD try to create a pizza and return error message WHEN pizza name length is greater than 100`() {
        val longPizzaName =
            "That's a very very very very very very very long pizza name and it is not accepted by the domain model"
        val newPizza = Pizza(name = longPizzaName, type = NEAPOLITAN)

        val createdPizza = pizzaService.create(newPizza)

        assertThat(createdPizza).isNotNull
        assertThat(createdPizza.errors).isNotEmpty
        assertThat(createdPizza.errors).contains("Pizza name is too long")

        verify(pizzaPersistencePort, never()).persistPizza(any())
    }
}