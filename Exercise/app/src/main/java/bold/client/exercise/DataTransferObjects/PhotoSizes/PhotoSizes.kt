package bold.client.exercise.DataTransferObjects.PhotoSizes

data class PhotoSizes (
    val sizes : Sizes,
    val stat : String
)
data class Sizes(
    val canblog: Int,
    val canprint: Int,
    val canDownload: Int,
    val size: Array<SizeInfo>
)

data class SizeInfo(
    val label: String,
    val width: Int,
    val height: Int,
    val source: String,
    val url: String,
    val media: String
)