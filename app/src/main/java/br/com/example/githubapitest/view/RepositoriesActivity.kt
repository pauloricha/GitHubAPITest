package br.com.example.githubapitest.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.example.githubapitest.R
import br.com.example.githubapitest.data.network.ConnectionState
import br.com.example.githubapitest.databinding.ActivityRepositoriesBinding
import br.com.example.githubapitest.view.adapter.RepositoryAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class RepositoriesActivity : AppCompatActivity(), RepositoryAdapter.OnListener {

    private val viewModel: RepositoriesViewModel by viewModel()
    private val repositoryAdapter = RepositoryAdapter(this)
    private lateinit var binding: ActivityRepositoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepositoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        configureRecyclerView()
        configureObservables()
        fetchRepositories()
    }

    private fun fetchRepositories() {
        binding.loadingRepository.visibility = View.VISIBLE
        binding.rvRepositories.visibility = View.VISIBLE
        binding.tvTitleError.visibility = View.GONE
        binding.btnTryAgain.visibility = View.GONE

        viewModel.fetchRepositories()
    }

    private fun configureRecyclerView() {
        binding.rvRepositories.layoutManager = LinearLayoutManager(this)
        binding.rvRepositories.adapter = repositoryAdapter

        repositoryAdapter.onItemClick = { position ->
            if(viewModel.repositories.value != null) {
                val url = viewModel.repositories.value!![position]?.htmlUrl
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
        }
    }

    private fun configureObservables() {
        viewModel.connectionState.observe(this, Observer {
            repositoryAdapter.updateConnectionState(it)
        })
        viewModel.repositories.observe(this, Observer {
            repositoryAdapter.submitList(it)
        })
    }

    override fun listUpdated() {
        binding.loadingRepository.visibility = View.GONE
    }

    override fun setEmptyListUI(connectionState: ConnectionState?) {
        binding.loadingRepository.visibility = View.GONE
        binding.rvRepositories.visibility = View.GONE
        binding.tvTitleError.visibility = View.VISIBLE
        binding.btnTryAgain.visibility = View.VISIBLE
        binding.btnTryAgain.setOnClickListener {
            fetchRepositories()
        }

        when (connectionState) {
            ConnectionState.SUCCESS -> {
                binding.tvTitleError.text = getString(R.string.txt_no_result_found)
            }
            ConnectionState.FAILED -> {
                binding.tvTitleError.text = getString(R.string.txt_technical_error)
            }
        }
    }
}