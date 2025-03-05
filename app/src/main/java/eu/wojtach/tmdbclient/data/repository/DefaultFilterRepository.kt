package eu.wojtach.tmdbclient.data.repository

import eu.wojtach.tmdbclient.domain.error.DataError
import eu.wojtach.tmdbclient.domain.model.Filter
import eu.wojtach.tmdbclient.domain.repository.FilterRepository
import eu.wojtach.tmdbclient.domain.result.Result
import io.ktor.client.plugins.HttpRequestTimeoutException
import org.koin.core.annotation.Single
import java.net.SocketTimeoutException
import java.net.UnknownHostException
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

    override suspend fun getAll(): Result<List<Filter>, DataError> {
        try {
            val genres = remoteDataSource.genres().genres
            return Result.Success(genres.map { it.toDomain() })
        } catch (e: SocketTimeoutException) {
            return Result.Error(DataError.Timeout)
        } catch (e: HttpRequestTimeoutException) {
            return Result.Error(DataError.Timeout)
        } catch (e: UnknownHostException) {
            return Result.Error(DataError.UnknownHost)
        } catch (e: Exception) {
            return Result.Error(DataError.Unknown)
        }
    }
}
