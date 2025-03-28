package com.hexarch.template.pizza.adapter.inbound.web.controller

import com.hexarch.template.pizza.adapter.outbound.persistence.PizzaRepository
import com.hexarch.template.pizza.application.dto.PizzaDto
import com.hexarch.template.pizza.application.error.BusinessError
import com.hexarch.template.pizza.application.error.ErrorCode.PIZZA_NOT_FOUND
import com.hexarch.template.pizza.domain.model.entity.Pizza
import com.hexarch.template.pizza.domain.model.value.PizzaType.NEAPOLITAN
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullSource
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper
import java.util.*
import kotlin.test.Test

@SpringBootTest
@AutoConfigureMockMvc
class PizzaControllerApiTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val pizzaRepository: PizzaRepository,
) {

    @Nested
    inner class CreatePizzaApi {

        @Test
        fun `SHOULD create a pizza WHEN parameters are valid`() {
            val pizzaName = "Mama mia"
            val pizzaType = NEAPOLITAN

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
        fun `SHOULD try to create a pizza and return BAD REQUEST WHEN pizza id is given`() {
            mockMvc.perform(
                post("/v1/pizzas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                    {
                      "id": "${UUID.randomUUID()}",
                      "name": "a pizza with id",
                      "type": "${NEAPOLITAN.name}"
                    }
                """.trimIndent()
                    )
            )
                .andExpect(status().isBadRequest)
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

    @Nested
    inner class GetPizzaApi {
        @Test
        fun `SHOULD get a pizza by ID WHEN it exists in db`() {
            val pizzaName = "Mama mia"
            val pizzaType = NEAPOLITAN

            val pizza = pizzaRepository.save(Pizza(name = pizzaName, type = pizzaType))

            val mockMvcResult = mockMvc.perform(
                get("/v1/pizzas/{pizzaId}", pizza.id)
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(status().isOk)
                .andReturn()

            val contentAsString = mockMvcResult.response.contentAsString
            assertThat(contentAsString).isNotEmpty()

            val createdPizzaDto = ObjectMapper().readValue(contentAsString, PizzaDto::class.java)
            assertThat(createdPizzaDto).isEqualTo(
                PizzaDto(
                    id = pizza.id,
                    name = pizzaName,
                    type = pizzaType
                )
            )
        }

        @Test
        fun `SHOULD try to get a pizza by ID and return 404 WHEN it does not exists in db`() {
            val mockMvcResult = mockMvc.perform(
                get("/v1/pizzas/{pizzaId}", UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(status().isNotFound)
                .andReturn()

            val contentAsString = mockMvcResult.response.contentAsString
            assertThat(contentAsString).isNotEmpty()

            val createdPizzaDto = ObjectMapper().readValue(contentAsString, PizzaDto::class.java)
            assertThat(createdPizzaDto).isEqualTo(
                PizzaDto(
                    errors = listOf(BusinessError(Pizza::name, PIZZA_NOT_FOUND, "Pizza not found"))
                )
            )
        }
    }
}