import io.ktor.application.*
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

var examplePageClicks = 0

fun main() {
    println("Starting up")

    embeddedServer(
        Netty,
        8081,
        module = Application::module
    ).start()

}

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)

    install(Routing) {
        get("/") {
            val map = createMap("Home")
            map["time"] = System.currentTimeMillis()

            call.respondHbs(HandlebarsContent("index.hbs", map))
        }

        get("/example") {
            val map = createMap("Example")
            map["oddPageClicks"] = (++examplePageClicks) % 2 == 1

            call.respondHbs(HandlebarsContent("example.hbs", map))
        }
    }
}

fun createMap(pageTitle: String) = mutableMapOf<String, Any?>("pageTitle" to pageTitle)