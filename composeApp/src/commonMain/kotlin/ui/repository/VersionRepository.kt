package ui.repository

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import ui.getHttpClient
import ui.model.DescriptionBlocks
import ui.model.Version

class VersionRepository {
    private val client = getHttpClient()
    suspend fun getVersions(): List<Version>? {
        return withContext(Dispatchers.IO) {
            try {
                val url = "https://dl.google.com/android/studio/metadata/distributions.json"
                val response = client.get(url)
                val json = response.bodyAsText()
                val dtoList = Json.decodeFromString<List<VersionDto>>(json)
                dtoList.createVersions()
            } catch (e: Exception) {
                null
            }
        }
    }

    private fun List<VersionDto>.createVersions(): List<Version> {
        var cumulativeDistribution = 100.toDouble()
        return this.sortedBy { it.apiLevel }.map { versionDto ->
            val thisCumulativeDistribution = if (cumulativeDistribution != 100.toDouble()) {
                cumulativeDistribution
            } else {
                null
            }
            Version(
                name = versionDto.name,
                version = versionDto.version,
                apiLevel = versionDto.apiLevel,
                distributionPercentage = versionDto.distributionPercentage,
                cumulativeDistribution = thisCumulativeDistribution,
                url = versionDto.url,
                descriptionBlocks = versionDto.descriptionBlocks.map { blocksDto ->
                    DescriptionBlocks(
                        title = blocksDto.title,
                        body = blocksDto.body
                    )
                }
            ).apply {
                cumulativeDistribution -= versionDto.distributionPercentage * 100
            }
        }
    }

    @Serializable
    private data class VersionDto(
        @SerialName("name") val name: String,
        @SerialName("version") val version: Double,
        @SerialName("apiLevel") val apiLevel: Int,
        @SerialName("distributionPercentage") val distributionPercentage: Double,
        @SerialName("url") val url: String,
        @SerialName("descriptionBlocks") val descriptionBlocks: List<DescriptionBlocksDto>
    )

    @Serializable
    private data class DescriptionBlocksDto(
        @SerialName("title") val title: String,
        @SerialName("body") val body: String
    )
}

