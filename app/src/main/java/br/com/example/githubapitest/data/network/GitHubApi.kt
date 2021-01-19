package br.com.example.githubapitest.data.network

import br.com.example.githubapitest.model.SearchRepository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {

    @GET("search/repositories?")
    suspend fun fetchRepositories(
        @Query("q") q: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int): Response<SearchRepository>
}