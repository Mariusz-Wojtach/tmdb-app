package eu.wojtach.tmdbclient.di

import eu.wojtach.tmdbclient.data.remote.di.DataRemoteKoinModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [DataRemoteKoinModule::class])
@ComponentScan("eu.wojtach.tmdbclient")
class AppKoinModule
