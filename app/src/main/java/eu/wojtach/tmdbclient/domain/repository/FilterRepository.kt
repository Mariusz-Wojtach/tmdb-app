package eu.wojtach.tmdbclient.domain.repository

import eu.wojtach.tmdbclient.domain.model.Filter

interface FilterRepository {
    fun getSelectedId(): Long?
    fun setSelectedId(id: Long)
    fun clearSelectedId()

    suspend fun getAll(): List<Filter>
}
