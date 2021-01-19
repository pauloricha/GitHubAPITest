package br.com.example.githubapitest.repository

import br.com.example.githubapitest.data.network.GitHubApi
import br.com.example.githubapitest.model.Repository
import br.com.example.guiabolsoapptest.internal.Q
import br.com.example.guiabolsoapptest.internal.SORT

class RepoRepository(private val service: GitHubApi) {

    suspend fun fetchRepositories(page: Int, perPage: Int) =
        service.fetchRepositories(Q, SORT, page, perPage)

    suspend fun searchRepositoriesWithPagination(page: Int, perPage: Int):
            List<Repository> {
        val request = fetchRepositories(page, perPage)
        return request.body()!!.items
    }
}