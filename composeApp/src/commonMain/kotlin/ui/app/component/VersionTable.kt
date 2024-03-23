package ui.app.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.model.Version

@Composable
fun VersionTable(
    versions: List<Version>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        items(versions) { version ->
            Text("${version.name} ${version.version} ${version.apiLevel} ${version.distributionPercentage}")
        }
    }
}