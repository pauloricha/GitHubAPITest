package br.com.example.githubapitest.di

import br.com.example.githubapitest.view.RepositoriesViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { RepositoriesViewModel(get()) }
}
