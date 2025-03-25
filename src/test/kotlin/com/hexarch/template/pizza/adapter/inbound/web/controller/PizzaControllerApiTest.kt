package com.hexarch.template.pizza.adapter.inbound.web.controller

import com.hexarch.template.pizza.application.dto.PizzaDto
import com.hexarch.template.pizza.domain.model.entity.Pizza
import com.hexarch.template.pizza.domain.model.value.PizzaType.NEAPOLITAN
import com.hexarch.template.pizza.port.outbound.PizzaPersistencePort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullSource
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito.`when`
import org.mockito.kotlin.eq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper
import java.util.*
import kotlin.test.Test

@SpringBootTest
@AutoConfigureMockMvc
class PizzaControllerApiTest(
    @Autowired val mockMvc: MockMvc,
) {

    // TODO remove it once database is set
    @MockitoBean
    private lateinit var pizzaPersistencePort: PizzaPersistencePort

    @Nested
    inner class CreatePizzaApi {

        @Test
        fun `SHOULD create a pizza WHEN parameters are valid`() {
            val pizzaName = "Mama mia"
            val pizzaType = NEAPOLITAN

            // TODO remove it once database is set
            val newPizzaId = UUID.randomUUID()
            `when`(pizzaPersistencePort.persistPizza(eq(Pizza(name = pizzaName, type = NEAPOLITAN)))).thenReturn(
                Pizza(newPizzaId, pizzaName, NEAPOLITAN)
            )

            val mockMvcResult = mockMvc.perform(
                post("/v1/pizzas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                    {
                        "name": "$pizzaName",
                        "type": "${pizzaType.name}"
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

        @Test
        fun `SHOULD try to create a pizza and return BAD REQUEST WHEN pizza name is not given`() {
            mockMvc.perform(
                post("/v1/pizzas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                    {
                        "type": "${NEAPOLITAN.name}"
                    }
                """.trimIndent()
                    )
            )
                .andExpect(status().isBadRequest)
                .andReturn()
        }

        @ParameterizedTest
        @ValueSource(strings = ["", "rubbish"])
        @NullSource
        fun `SHOULD try to create a pizza and return BAD REQUEST WHEN pizza type is invalid`(type: String?) {
            mockMvc.perform(
                post("/v1/pizzas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                    {
                        ${if (type != null) """"type": "$type",""" else ""}
                        "name": "My great pizza"
                    }
                    """.trimIndent()
                    )
            )
                .andExpect(status().isBadRequest)
        }
    }
}