package com.hexarch.template.pizza.adapter.inbound.web.api

import com.hexarch.template.pizza.application.dto.PizzaDto
import org.springframework.http.ResponseEntity

interface PizzaApi {

    fun getPizza() : ResponseEntity<PizzaDto>
}