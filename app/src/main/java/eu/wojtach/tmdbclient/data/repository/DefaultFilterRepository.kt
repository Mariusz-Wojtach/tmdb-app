package eu.wojtach.tmdbclient.data.repository

import eu.wojtach.tmdbclient.domain.model.Filter
import eu.wojtach.tmdbclient.domain.repository.FilterRepository
import org.koin.core.annotation.Single
import eu.wojtach.tmdbclient.data.local.filter.DataSource as LocalDataSource
import eu.wojtach.tmdbclient.data.remote.genre.DataSource as RemoteDataSource

@Single
class DefaultFilterRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : FilterRepository {
    override fun getSelectedId(): Long? {
        return localDataSource.getSelectedFilterId()
    }

    override fun setSelectedId(id: Long) {
        localDataSource.setSelectedFilterId(id)
    }

    override fun clearSelectedId() {
        localDataSource.clearSelectedFilterId()
    }

    override suspend fun getAll(): List<Filter> {
        val genres = remoteDataSource.genres().genres
        return genres.map { it.toDomain() }
    }
}
