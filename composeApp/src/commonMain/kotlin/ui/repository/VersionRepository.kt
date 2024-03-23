package ui.repository

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import ui.getHttpClient
import ui.model.Version

class VersionRepository {
    private val client = getHttpClient()
    suspend fun getVersions(): List<Version>? {
        return withContext(Dispatchers.IO) {
            try {
                val url = "https://dl.google.com/android/studio/metadata/distributions.json"
                val response = client.get(url)
                val json = response.bodyAsText()
                Json.decodeFromString<List<Version>>(json)
            } catch (e: Exception) {
                null
            }
        }
    }
}