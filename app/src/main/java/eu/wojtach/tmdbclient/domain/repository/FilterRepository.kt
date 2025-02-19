package eu.wojtach.tmdbclient.domain.repository

import eu.wojtach.tmdbclient.domain.error.DataError
import eu.wojtach.tmdbclient.domain.model.Filter
import eu.wojtach.tmdbclient.domain.result.Result

interface FilterRepository {
    fun getSelectedId(): Long?
    fun setSelectedId(id: Long)
    fun clearSelectedId()

    suspend fun getAll(): Result<List<Filter>, DataError>
}
