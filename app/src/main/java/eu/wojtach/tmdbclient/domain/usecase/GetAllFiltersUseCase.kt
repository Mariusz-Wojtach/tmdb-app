package eu.wojtach.tmdbclient.domain.usecase

import eu.wojtach.tmdbclient.domain.error.DataError
import eu.wojtach.tmdbclient.domain.model.Filter
import eu.wojtach.tmdbclient.domain.repository.FilterRepository
import eu.wojtach.tmdbclient.domain.result.Result
import org.koin.core.annotation.Factory

@Factory
class GetAllFiltersUseCase(
    private val repository: FilterRepository
) {
    suspend operator fun invoke(): Result<List<Filter>, DataError> = repository.getAll()
}
