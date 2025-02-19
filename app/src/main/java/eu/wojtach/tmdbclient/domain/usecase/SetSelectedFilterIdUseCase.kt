package eu.wojtach.tmdbclient.domain.usecase

import eu.wojtach.tmdbclient.domain.repository.FilterRepository
import org.koin.core.annotation.Factory

@Factory
class SetSelectedFilterIdUseCase(
    private val repository: FilterRepository
) {
    operator fun invoke(id: Long) = repository.setSelectedId(id)
}
