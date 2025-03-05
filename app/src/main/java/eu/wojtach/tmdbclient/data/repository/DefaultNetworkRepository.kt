package eu.wojtach.tmdbclient.data.repository

import eu.wojtach.tmdbclient.data.util.NetworkMonitor
import eu.wojtach.tmdbclient.domain.model.Network
import eu.wojtach.tmdbclient.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class DefaultNetworkRepository(
    monitor: NetworkMonitor
) : NetworkRepository {

    override val isConnected: Flow<Network> = monitor.isConnected.map { isConnected ->
        if (isConnected) {
            Network.AVAILABLE
        } else {
            Network.LOST
        }
    }
}
