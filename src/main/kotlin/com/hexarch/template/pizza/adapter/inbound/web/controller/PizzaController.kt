package com.hexarch.template.pizza.adapter.inbound.web.controller

import com.hexarch.template.pizza.adapter.inbound.web.api.PizzaApi
import com.hexarch.template.pizza.application.dto.PizzaDto
import org.springframework.http.ResponseEntity

class PizzaController : PizzaApi {
    override fun getPizza(): ResponseEntity<PizzaDto> {
        TODO("Not yet implemented")
    }
}