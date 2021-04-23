package tech.purplebeen.githubapi.model

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("name")
    val name: String,
    @SerializedName("stargazers_count")
    val startCount: Int,
    @SerializedName("description")
    val description: String
)