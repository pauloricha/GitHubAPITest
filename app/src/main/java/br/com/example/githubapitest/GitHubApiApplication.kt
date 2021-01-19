package br.com.example.githubapitest

import android.app.Application
import br.com.example.githubapitest.di.appComponent
import org.koin.android.ext.android.startKoin

open class GitHubApiApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        configureDi()
    }

    open fun configureDi() =
        startKoin(this, provideComponent())

    open fun provideComponent() = appComponent
}