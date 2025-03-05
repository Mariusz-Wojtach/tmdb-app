package eu.wojtach.tmdbclient.domain.repository

import eu.wojtach.tmdbclient.domain.model.Network
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    val isConnected: Flow<Network>
}
