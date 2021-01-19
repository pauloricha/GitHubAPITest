package br.com.example.githubapitest.repository

import br.com.example.githubapitest.base.BaseUT
import br.com.example.githubapitest.di.configureAppComponent
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import org.koin.test.KoinTest
import java.io.File
import java.net.HttpURLConnection

class RepoRepositoryTest: BaseUT() {

    private val repository by inject<RepoRepository>()

    override fun setUp() {
        super.setUp()
        StandAloneContext.startKoin(configureAppComponent(getMockUrl()))
    }

    override fun isMockServerEnabled() = true

    @Test
    fun fetchRepositories() {
        mockHttpResponse(HttpURLConnection.HTTP_OK)
        runBlocking {
            val repositories = repository.searchRepositoriesWithPagination(1, 1)
            val repository = repositories[0]
            assertEquals("51148780", repository.id)
            assertEquals("architecture-samples", repository.name)
            assertEquals("https://github.com/android/architecture-samples", repository.htmlUrl)
            assertEquals(38068, repository.stargazersCount)
            assertEquals(10514, repository.forksCount)
        }
    }
}