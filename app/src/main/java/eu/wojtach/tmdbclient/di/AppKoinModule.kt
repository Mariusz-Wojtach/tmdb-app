package eu.wojtach.tmdbclient.di

import android.content.Context
import android.net.ConnectivityManager
import eu.wojtach.tmdbclient.data.remote.di.DataRemoteKoinModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module(includes = [DataRemoteKoinModule::class])
@ComponentScan("eu.wojtach.tmdbclient")
class AppKoinModule {

    @Factory
    fun connectivityManager(context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}
