package com.hexarch.template.pizza.adapter.inbound.web.controller

import com.hexarch.template.pizza.application.dto.PizzaDto
import com.hexarch.template.pizza.domain.model.entity.Pizza
import com.hexarch.template.pizza.domain.model.value.PizzaType.*
import com.hexarch.template.pizza.port.outbound.PizzaPersistencePort
import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.eq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper
import kotlin.test.Test

@SpringBootTest
// TODO remove it once database is set
@EnableAutoConfiguration(exclude = [ DataSourceAutoConfiguration::class ])
@AutoConfigureMockMvc
class PizzaControllerIntegrationTest (
    @Autowired val mockMvc: MockMvc
) {

    @Mock
    private lateinit var pizzaPersistencePort: PizzaPersistencePort

    @Test
    fun `SHOULD create a pizza WHEN parameters are valid`() {
        val pizzaName = "Mama mia"
        val pizzaType = NEAPOLITAN

        `when`(pizzaPersistencePort.getPizzaByName(eq(pizzaName))).thenReturn(Pizza(name = pizzaName, type = NEAPOLITAN))

        val mockMvcResult = mockMvc.perform(
            post("/v1/pizzas")
                .content(
                    """
                    {
                        "name": $pizzaName,
                        "type": ${pizzaType.name}
                    }
                """.trimIndent()
                )
        )
            .andExpect(status().isCreated)
            .andReturn()

        val contentAsString = mockMvcResult.response.contentAsString
        assertThat(contentAsString).isNotEmpty()

        val createdPizzaDto = ObjectMapper().readValue(contentAsString, PizzaDto::class.java)
        assertThat(createdPizzaDto).isEqualTo(
            PizzaDto(
                id = createdPizzaDto.id,
                name = pizzaName,
                type = pizzaType
            )
        )
    }
}