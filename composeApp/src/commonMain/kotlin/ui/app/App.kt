package ui.app

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import ui.app.screen.VersionScreen
import ui.app.screen.VersionViewModel

@Composable
fun App() {
    MaterialTheme(darkColors()) {
        val viewModel by remember { mutableStateOf(VersionViewModel()) }
        VersionScreen(viewModel = viewModel)
    }
}