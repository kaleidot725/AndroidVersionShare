import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual fun getHttpClient(): HttpClient {
    return HttpClient(Darwin)
}

