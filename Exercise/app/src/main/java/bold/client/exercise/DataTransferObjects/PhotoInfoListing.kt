package bold.client.exercise.DataTransferObjects


data class PhotoListing(
    val photos: Photos
)

data class Photos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: Int,
    val photo: Array<Photo>
)

data class Photo(
    val id: String,
    val owner: String,
    val server: Int,
    val title: String,
    val isPublic: Int,
    val isfriend: Int,
    val isFamily: Int
)