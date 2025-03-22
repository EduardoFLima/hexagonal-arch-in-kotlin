package com.hexarch.template.pizza.application.service

import com.hexarch.template.pizza.application.error.BusinessError
import com.hexarch.template.pizza.application.error.ErrorCode.PIZZA_NAME_NOT_VALID
import com.hexarch.template.pizza.application.error.ErrorCode.PIZZA_NOT_FOUND
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

        `when`(pizzaPersistencePort.getPizzaByName(eq(name))).thenReturn(Pizza(name = name, type = NEAPOLITAN))

        val pizzaDto = pizzaService.get(name)

        assertThat(pizzaDto).isNotNull
        assertThat(pizzaDto.errors).isEmpty()
        assertThat(pizzaDto.name).isEqualTo(name)
        assertThat(pizzaDto.type).isEqualTo(NEAPOLITAN)
    }

    @Test
    fun `SHOULD try to get a pizza and return error message WHEN it does not exist`() {
        val name = "Margherita"

        val pizzaDto = pizzaService.get(name)

        assertThat(pizzaDto).isNotNull
        assertThat(pizzaDto.errors).isNotEmpty
        assertThat(pizzaDto.errors).contains(BusinessError(Pizza::name, PIZZA_NOT_FOUND, "Pizza not found"))
        assertThat(pizzaDto.name).isNull()
        assertThat(pizzaDto.type).isNull()
    }

    @Test
    fun `SHOULD create a pizza WHEN given parameters are valid`() {
        val newPizza = Pizza(name = "Some New Pizza", type = NEAPOLITAN)

        `when`(pizzaPersistencePort.persistPizza(eq(newPizza))).thenReturn(newPizza.copy())

        val createdPizzaDto = pizzaService.create(newPizza)
        assertThat(createdPizzaDto.errors).isEmpty()

        val createdPizza = createdPizzaDto.toPizza()
        assertThat(createdPizza).isNotNull
        assertThat(createdPizza).isNotSameAs(newPizza)
        assertThat(createdPizza).isEqualTo(newPizza)
    }

    @Test
    fun `SHOULD try to create a pizza and return error message WHEN pizza name length is less than 3`() {
        val newPizza = Pizza(name = "AB", type = NEAPOLITAN)

        val createdPizzaDto = pizzaService.create(newPizza)

        assertThat(createdPizzaDto).isNotNull
        assertThat(createdPizzaDto.errors).isNotEmpty
        assertThat(createdPizzaDto.errors)
            .contains(BusinessError(Pizza::name, PIZZA_NAME_NOT_VALID, "Pizza name is too short"))

        verify(pizzaPersistencePort, never()).persistPizza(any())
    }

    @Test
    fun `SHOULD try to create a pizza and return error message WHEN pizza name length is greater than 100`() {
        val longPizzaName =
            "That's a very very very very very very very long pizza name and it is not accepted by the domain model"
        val newPizza = Pizza(name = longPizzaName, type = NEAPOLITAN)

        val createdPizzaDto = pizzaService.create(newPizza)

        assertThat(createdPizzaDto).isNotNull
        assertThat(createdPizzaDto.errors).isNotEmpty
        assertThat(createdPizzaDto.errors)
            .contains(BusinessError(Pizza::name, PIZZA_NAME_NOT_VALID, "Pizza name is too long"))

        verify(pizzaPersistencePort, never()).persistPizza(any())
    }
}