package com.hexarch.template.pizza.adapter.inbound.web.controller

import com.hexarch.template.pizza.adapter.inbound.web.api.PizzaApi
import com.hexarch.template.pizza.application.dto.PizzaDto
import com.hexarch.template.pizza.application.service.PizzaService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class PizzaController(val pizzaService: PizzaService) : PizzaApi {

    @PostMapping("/v1/pizzas")
    @ResponseStatus(HttpStatus.CREATED)
    override fun createPizza(
        @Valid @RequestBody pizzaDto: PizzaDto,
    ): PizzaDto {
        return pizzaService.create(pizzaDto)
    }

    @GetMapping("/v1/pizzas/{pizzaId}")
    override fun getPizza(
        @PathVariable pizzaId: UUID,
    ): ResponseEntity<PizzaDto> {
        return pizzaService.get(pizzaId).let {
            if (it.id != null) {
                return ResponseEntity.ok(it)
            }
            ResponseEntity.status(NOT_FOUND).body(it)
        }
    }
}