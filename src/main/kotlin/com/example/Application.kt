package com.example

import com.example.plugins.configureRouting
import com.example.repository.DataBaseFactory
import io.ktor.serialization.gson.gson
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.*


fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(Resources)
    configureRouting()
    DataBaseFactory.connectDB()

    install(ContentNegotiation){
        gson{
            setPrettyPrinting()
        }
    }

}
