package com.example.assignment.Network

import com.example.assignment.model.GitRepoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkInterface {

    @GET("/search/repositories")
    suspend fun getGitRepoList(@Query("q") query: String,
                       @Query("sort") sort: String,
                       @Query("order") order: String) : Response<GitRepoModel>

}

