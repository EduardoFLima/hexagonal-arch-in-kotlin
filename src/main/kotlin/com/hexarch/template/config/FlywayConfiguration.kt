package com.hexarch.template.config

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlywayConfiguration(
    @Value("\${spring.jpa.properties.hibernate.default_schema}") val defaultSchema: String,
) {

    @Bean
    fun flywayMigrationStrategy(): FlywayMigrationStrategy {
        return FlywayMigrationStrategy { flyway: Flyway ->
            val schemas = arrayOf(defaultSchema)

            for (schema in schemas) {
                Flyway.configure().configuration(flyway.configuration).schemas(schema).load().migrate()
            }
        }
    }
}