package tech.purplebeen.githubapi.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import tech.purplebeen.githubapi.model.Repository
import tech.purplebeen.githubapi.model.User

interface GithubService {
    @GET("users/{username}")
    fun getUserInfo(@Path("username") userName: String): Single<User>

    @GET("users/{username}/repos")
    fun getRepoList(@Path("username") userName: String): Single<ArrayList<Repository>>
}