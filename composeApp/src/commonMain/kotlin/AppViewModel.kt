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

class AppViewModel {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val client = getHttpClient()

    private val _text: MutableStateFlow<String> = MutableStateFlow("")
    val text: StateFlow<String> = _text.asStateFlow()

    fun load() {
        scope.launch {
            withContext(Dispatchers.IO) {
                val response =
                    client.get("https://dl.google.com/android/studio/metadata/distributions.json")
                _text.value = response.bodyAsText()
            }
        }
    }
}