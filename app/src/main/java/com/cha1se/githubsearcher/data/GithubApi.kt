package com.cha1se.githubsearcher.data

import Content
import com.cha1se.githubsearcher.domain.models.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories?")
    suspend fun getRepositoriesByQuery(
        @Query("q") q: String,
        @Query("sort") sort: String,
        @Query("per_page") perPage: String,
        @Query("page") page: String,
    ): Repo

    @GET("repos/{username}/{repo}/contents/{path}")
    suspend fun getContentInRepository(
        @Path(value = "username") username: String,
        @Path(value = "repo") repo: String,
        @Path(value = "path") path: String,
        ): List<Content>
}