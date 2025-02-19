package eu.wojtach.tmdbclient.domain.error

import eu.wojtach.tmdbclient.domain.result.Error

sealed interface DataError: Error {
    data object Timeout: DataError
    data object Unknown: DataError
}
