package ui

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Version(
    @SerialName("name") val name: String,
    @SerialName("version") val version: Double,
    @SerialName("apiLevel") val apiLevel: Int,
    @SerialName("distributionPercentage") val distributionPercentage: Double,
    @SerialName("url") val url: String,
    @SerialName("descriptionBlocks") val descriptionBlocksBlocks: List<DescriptionBlocks>
)

@Serializable
data class DescriptionBlocks(
    @SerialName("title") val title: String,
    @SerialName("body") val body: String
)