package com.hexarch.template

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<HexagonalArchInKotlinApplication>().with(TestcontainersConfiguration::class).run(*args)
}
