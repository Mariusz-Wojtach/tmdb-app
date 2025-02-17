package eu.wojtach.tmdbclient

import android.app.Application
import eu.wojtach.tmdbclient.di.AppKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.module

class TMDBApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TMDBApp)
            modules(AppKoinModule().module)
        }
    }
}
