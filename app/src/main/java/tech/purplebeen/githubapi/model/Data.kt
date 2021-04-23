package tech.purplebeen.githubapi.model

data class Data(
    val viewType: ViewType,
    val name: String,
) {
    var starCount: Int? = null
    var description: String? = null
    var profile: String? = null
}
