package br.com.example.githubapitest.view

import br.com.example.githubapitest.base.BaseUT
import br.com.example.githubapitest.di.configureAppComponent
import br.com.example.guiabolsoapptest.internal.BASE_URL
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject

class RepositoriesViewModelTest: BaseUT() {

    private val viewModel by inject<RepositoriesViewModel>()

    override fun isMockServerEnabled() = false

    override fun setUp() {
        super.setUp()
        startKoin(configureAppComponent(BASE_URL))
    }

    @Test
    fun testFetchRepositories() {
        viewModel.fetchRepositories()
    }
}