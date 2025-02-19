package eu.wojtach.tmdbclient.domain.result

import eu.wojtach.tmdbclient.domain.result.Error as RootError

sealed interface Result<D, E: RootError> {
    data class Success<D, E: RootError>(val data: D) : Result<D, E>
    data class Error<D, E: RootError>(val error: RootError) : Result<D, E>
}
