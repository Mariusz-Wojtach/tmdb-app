package eu.wojtach.tmdbclient.domain.usecase

import eu.wojtach.tmdbclient.domain.model.Filter
import eu.wojtach.tmdbclient.domain.repository.FilterRepository
import org.koin.core.annotation.Factory

@Factory
class ClearSelectedFilterIdUseCase(
    private val repository: FilterRepository
) {
    operator fun invoke() = repository.clearSelectedId()
}
