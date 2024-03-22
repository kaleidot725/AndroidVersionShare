package ui

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class AppViewModel {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val client = getHttpClient()

    private val _versions: MutableStateFlow<List<Version>> = MutableStateFlow(emptyList())
    val versions: StateFlow<List<Version>> = _versions.asStateFlow()

    fun load() {
        scope.launch {
            withContext(Dispatchers.IO) {
                val url = "https://dl.google.com/android/studio/metadata/distributions.json"
                val json = client.get(url).bodyAsText()
                _versions.value = Json.decodeFromString<List<Version>>(json)
            }
        }
    }
}