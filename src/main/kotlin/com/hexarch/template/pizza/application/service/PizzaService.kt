package com.hexarch.template.pizza.application.service

import com.hexarch.template.pizza.application.dto.PizzaDto
import com.hexarch.template.pizza.application.error.BusinessError
import com.hexarch.template.pizza.application.error.ErrorCode.PIZZA_NAME_NOT_VALID
import com.hexarch.template.pizza.application.error.ErrorCode.PIZZA_NOT_FOUND
import com.hexarch.template.pizza.domain.model.entity.Pizza
import com.hexarch.template.pizza.port.inbound.CreatePizzaUseCase
import com.hexarch.template.pizza.port.inbound.RetrievePizzaUseCase
import com.hexarch.template.pizza.port.outbound.PizzaPersistencePort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PizzaService(
    private val pizzaPersistencePort: PizzaPersistencePort,
) : RetrievePizzaUseCase, CreatePizzaUseCase {
    override fun get(id: UUID): PizzaDto {
        pizzaPersistencePort.getPizza(id)?.let { return PizzaDto.fromPizza(it) }

        return pizzaDtoWithNotFoundError()
    }

    override fun create(pizzaDto: PizzaDto): PizzaDto {
        val pizza = pizzaDto.toPizza()

        pizza.errorInName()?.let { return pizzaDtoWithNameNotValidError(it) }

        return pizzaPersistencePort.persistPizza(pizza).let { PizzaDto.fromPizza(it) }
    }

    private fun pizzaDtoWithNotFoundError() =
        PizzaDto.withErrors(listOf(BusinessError(Pizza::name, PIZZA_NOT_FOUND, "Pizza not found")))

    private fun pizzaDtoWithNameNotValidError(
        errorMessage: String,
    ) = PizzaDto.withErrors(listOf(BusinessError(Pizza::name, PIZZA_NAME_NOT_VALID, errorMessage)))
}