package dev.terrylabs.gitdoit.api

import dev.terrylabs.gitdoit.model.Repo
import dev.terrylabs.gitdoit.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GitHubApi {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String, @Query("type") type: String,
                  @Query("sort") sort: String, @Query("direction") direction: String): Call<List<Repo>>

    @GET("users/{user}")
    fun userCheck(@Path("user") user: String): Call<User>

    @GET("/repos/{owner}/{repo}/stats/punch_card")
    fun getRepoCommitPerHourEachDay(@Path("owner") owner: String, @Path("repo") repo: String): Call<List<List<Int>>>
}