package br.com.example.githubapitest.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.example.githubapitest.data.network.ConnectionState
import br.com.example.githubapitest.pagination.datasource.RepositoryDataSourceFactory
import br.com.example.githubapitest.repository.RepoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class RepositoriesViewModel(repository: RepoRepository) : ViewModel(){

    private val repositoryDataSource = RepositoryDataSourceFactory(repository = repository,
        scope = CoroutineScope(Dispatchers.Default))

    val connectionState: LiveData<ConnectionState> =
        Transformations.switchMap(repositoryDataSource.source) {
            it.getConnectionState()
        }

    val repositories = LivePagedListBuilder(repositoryDataSource, pagedListConfig()).build()

    fun fetchRepositories() {
        repositoryDataSource.getSource()
    }

    private fun pagedListConfig() = PagedList.Config.Builder()
        .setPageSize(30)
        .setEnablePlaceholders(true)
        .build()
}