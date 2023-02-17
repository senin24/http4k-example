package com.github.senin24.http4k

import org.http4k.client.JavaHttpClient
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.then
import org.http4k.filter.ClientFilters
import org.http4k.filter.DebuggingFilters.PrintResponse
import org.http4k.filter.MicrometerMetrics

fun main() {
    val client: HttpHandler = JavaHttpClient()

    val printingClient: HttpHandler = PrintResponse()
            .then(ClientFilters.MicrometerMetrics.RequestCounter(registry))
            .then(ClientFilters.MicrometerMetrics.RequestTimer(registry)).then(client)

    val response: Response = printingClient(Request(GET, "http://localhost:9000/ping"))

    println(response.bodyString())
}
