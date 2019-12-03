package bold.client.exercice.DataTransferObjects

data class UserInfo(
    val user: User,
    val stat: String
)

data class User(
    val id: String,
    val nsid: String,
    val username: Content
)

data class Content(
    val _content: String
)