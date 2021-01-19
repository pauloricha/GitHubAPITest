package br.com.example.githubapitest.di

import br.com.example.githubapitest.repository.RepoRepository
import org.koin.dsl.module.module

val repositoryModule = module {
    factory { RepoRepository(get()) }
}