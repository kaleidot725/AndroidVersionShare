package ui.app

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ui.model.Version
import ui.repository.VersionRepository

class AppViewModel {
    private val repository = VersionRepository()
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state.asStateFlow()

    fun loadVersions() {
        scope.launch {
            _state.value = State.Loading
            delay(300)
            val versions = repository.getVersions()
            if (versions != null) {
                _state.value = State.Success(versions)
            } else {
                _state.value = State.Failed
            }
        }
    }

    sealed class State {
        data object Loading : State()
        data class Success(val versions: List<Version>) : State()
        data object Failed : State()
    }
}