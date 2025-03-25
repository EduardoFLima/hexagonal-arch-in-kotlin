package com.hexarch.template.pizza.adapter.outbound.persistence

import com.hexarch.template.pizza.domain.model.entity.Pizza
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PizzaRepository : JpaRepository<Pizza, UUID>