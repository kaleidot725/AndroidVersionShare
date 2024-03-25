package ui.app.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.app.component.AutoSizableText
import ui.model.Version
import kotlin.math.round

@Composable
fun VersionTable(
    versions: List<Version>,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        TableHeader(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            versions.forEach { version ->
                TableVersionItem(
                    version = version,
                    color = version.apiLevel.toColor(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(version.distributionPercentage.compensatePercentage())
                )
                Divider()
            }
        }
    }
}

@Composable
private fun TableHeader(modifier: Modifier = Modifier) {
    Row(modifier) {
        Text(
            text = "ANDROID PLATFORM\nVERSION",
            style = TextStyle(
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier
                .wrapContentHeight()
                .weight(0.3f)
                .align(Alignment.CenterVertically)
                .alignByBaseline()
        )

        Text(
            text = "API LEVEL",
            style = TextStyle(
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier
                .wrapContentHeight()
                .weight(0.3f)
                .align(Alignment.CenterVertically)
                .alignByBaseline()
        )

        Text(
            text = "CUMULATIVE\nDISTRIBUTION",
            style = TextStyle(
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier
                .wrapContentHeight()
                .weight(0.3f)
                .align(Alignment.CenterVertically)
                .alignByBaseline()
        )
    }
}

@Composable
private fun TableVersionItem(
    version: Version,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        Spacer(
            modifier = Modifier
                .width(12.dp)
                .fillMaxHeight()
                .background(color)
        )

        Box(
            modifier = Modifier
                .weight(0.10f)
                .fillMaxHeight()
                .background(color)
        ) {
            AutoSizableText(
                text = "${version.version}",
                style = TextStyle(
                    textAlign = TextAlign.Start,
                    color = Color.Gray,
                ),
                minFontSize = 8.sp,
                maxFontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }

        Spacer(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(color)
        )

        Box(
            modifier = Modifier
                .weight(0.20f)
                .fillMaxHeight()
                .background(color)
        ) {
            AutoSizableText(
                text = version.name,
                style = TextStyle(
                    textAlign = TextAlign.Start,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                ),
                minFontSize = 8.sp,
                maxFontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }

        Box(
            modifier = Modifier
                .weight(0.30f)
                .fillMaxHeight()
                .background(color)
        ) {
            AutoSizableText(
                text = "${version.apiLevel}",
                style = TextStyle(
                    textAlign = TextAlign.Start,
                    fontSize = 100.sp,
                    color = Color.Gray,
                ),
                minFontSize = 8.sp,
                maxFontSize = 40.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        val textCumulativeDistribution = if (version.cumulativeDistribution != null) {
            "${version.cumulativeDistribution.format(1)}%"
        } else {
            ""
        }
        Text(
            text = textCumulativeDistribution,
            style = TextStyle(
                textAlign = TextAlign.End,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier
                .weight(0.3f)
                .fillMaxHeight()
                .align(Alignment.CenterVertically)
                .alignByBaseline()
        )

        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(12.dp)
        )
    }
}

private fun Double.format(decimals: Int): String {
    var multiplier = 1.0f
    repeat(decimals) { multiplier *= 10 }
    return (round(this * multiplier) / multiplier).toString()
}

private fun Double.compensatePercentage(): Float {
    return maxOf(0.05f, this.toFloat())
}

private fun Int.toColor(): Color {
    return when (this) {
        19 -> Color(0xFFc4dac4)
        21 -> Color(0xFF72bf86)
        22 -> Color(0xFF87a9ae)
        23 -> Color(0xFFd9b138)
        24 -> Color(0xFFe15354)
        25 -> Color(0xFF63b8cc)
        26 -> Color(0xFFd38258)
        27 -> Color(0xFFff8725)
        28 -> Color(0xFFe7b528)
        29 -> Color(0xFFc4dac4)
        30 -> Color(0xFF72bf86)
        31 -> Color(0xFF87a9ae)
        33 -> Color(0xFFd9b138)
        else -> Color(0xFFd9b138)
    }
}
