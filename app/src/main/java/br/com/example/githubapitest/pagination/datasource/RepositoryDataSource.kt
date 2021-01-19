package br.com.example.githubapitest.pagination.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import br.com.example.githubapitest.data.network.ConnectionState
import br.com.example.githubapitest.model.Repository
import br.com.example.githubapitest.repository.RepoRepository
import kotlinx.coroutines.*

class RepositoryDataSource(private val repository: RepoRepository,
                           private val scope: CoroutineScope):
    PageKeyedDataSource<Int, Repository>() {

    private var supervisorJob = SupervisorJob()
    private val connectionState = MutableLiveData<ConnectionState>()
    private var retryQuery: (() -> Any)? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback:
    LoadInitialCallback<Int, Repository>) {
        retryQuery = {
            loadInitial(params, callback)
        }
        executeQuery(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {
        val page = params.key
        retryQuery = { loadAfter(params, callback) }
        executeQuery(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) { }

    private fun executeQuery(page: Int, perPage: Int, callback:(List<Repository>) -> Unit) {
        scope.launch(getJobErrorHandler() + supervisorJob) {
            delay(200)
            val repositories =
                repository.searchRepositoriesWithPagination(page, perPage)
            retryQuery = null
            connectionState.postValue(ConnectionState.SUCCESS)
            callback(repositories)
        }
    }

    private fun getJobErrorHandler()= CoroutineExceptionHandler { _, e ->
        Log.e(RepositoryDataSource::class.java.simpleName, "An error happened: $e")
        connectionState.postValue(ConnectionState.FAILED)
    }

    fun getConnectionState(): LiveData<ConnectionState> =
        connectionState

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren()
    }

    fun refresh() =
        this.invalidate()
}