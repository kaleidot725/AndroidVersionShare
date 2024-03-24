package ui.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.darkColors
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ui.app.component.VersionTable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun App() {
    MaterialTheme {
        val viewModel by remember { mutableStateOf(AppViewModel()) }
        val uiState by viewModel.state.collectAsState()
        val isRefreshing by remember(uiState) {
            mutableStateOf(uiState == AppViewModel.State.Loading)
        }
        val pullRefreshState = rememberPullRefreshState(
            refreshing = isRefreshing,
            onRefresh = { viewModel.loadVersions(isRefreshing = true) }
        )

        LaunchedEffect(viewModel) { viewModel.loadVersions(isRefreshing = false) }

        MaterialTheme(colors = darkColors()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
                when (val state = uiState) {
                    AppViewModel.State.Loading -> {
                        Text(
                            text = "NOW LOADING...",
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    is AppViewModel.State.Success -> {
                        VersionTable(
                            versions = state.versions,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .pullRefresh(pullRefreshState)
                                .verticalScroll(rememberScrollState())
                        ) {}
                    }

                    AppViewModel.State.Failed -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Text(
                                text = "LOADING ERROR",
                                color = Color.White,
                            )

                            TextButton(
                                onClick = { viewModel.loadVersions(isRefreshing = false) },
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text("RETRY")
                            }
                        }

                    }
                }

                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                )
            }
        }
    }
}