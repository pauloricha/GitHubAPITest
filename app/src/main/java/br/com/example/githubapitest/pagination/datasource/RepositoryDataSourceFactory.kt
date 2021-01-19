package br.com.example.githubapitest.pagination.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import br.com.example.githubapitest.model.Repository
import br.com.example.githubapitest.repository.RepoRepository
import kotlinx.coroutines.CoroutineScope

class RepositoryDataSourceFactory(private val repository: RepoRepository,
                                  private val scope: CoroutineScope):
    DataSource.Factory<Int, Repository>() {

    val source = MutableLiveData<RepositoryDataSource>()

    override fun create(): DataSource<Int, Repository> {
        val source = RepositoryDataSource(repository, scope)
        this.source.postValue(source)
        return source
    }

    fun getSource() = source.value?.refresh()
}