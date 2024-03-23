package ui.model

data class Version(
    val name: String,
    val version: Double,
    val apiLevel: Int,
    val distributionPercentage: Double,
    val cumulativeDistribution: Double?,
    val url: String,
    val descriptionBlocks: List<DescriptionBlocks>
)

data class DescriptionBlocks(
    val title: String,
    val body: String
)