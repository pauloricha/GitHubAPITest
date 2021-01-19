package br.com.example.githubapitest.di

fun configureAppComponent(baseApi: String)
        = listOf(
    configureNetworkModuleForTest(baseApi),
    viewModelModule,
    repositoryModule
)