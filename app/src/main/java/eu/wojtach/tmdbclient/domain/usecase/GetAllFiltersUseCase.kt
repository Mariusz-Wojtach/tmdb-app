package eu.wojtach.tmdbclient.domain.usecase

import eu.wojtach.tmdbclient.domain.model.Filter

interface GetAllFiltersUseCase {
    suspend fun getAll(): List<Filter>
}
