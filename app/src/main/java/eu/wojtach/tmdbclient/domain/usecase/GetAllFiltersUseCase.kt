package eu.wojtach.tmdbclient.domain.usecase

import eu.wojtach.tmdbclient.domain.model.Filter
import eu.wojtach.tmdbclient.domain.repository.FilterRepository
import org.koin.core.annotation.Factory

@Factory
class GetAllFiltersUseCase(
    private val repository: FilterRepository
) {
    suspend operator fun invoke(): List<Filter> = repository.getAll()
}
