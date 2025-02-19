package eu.wojtach.tmdbclient.data.repository

import eu.wojtach.tmdbclient.data.remote.genre.DataSource
import eu.wojtach.tmdbclient.domain.model.Filter
import eu.wojtach.tmdbclient.domain.repository.FilterRepository
import eu.wojtach.tmdbclient.domain.usecase.GetAllFiltersUseCase
import org.koin.core.annotation.Single

@Single(binds = [GetAllFiltersUseCase::class])
class DefaultFilterRepository(
    private val dataSource: DataSource
) : FilterRepository {

    override suspend fun getAll(): List<Filter> {
        val genres = dataSource.genres().genres
        return genres.map { it.toDomain() }
    }
}
