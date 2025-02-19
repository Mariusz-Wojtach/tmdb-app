package eu.wojtach.tmdbclient.domain.usecase

import eu.wojtach.tmdbclient.domain.repository.FilterRepository
import org.koin.core.annotation.Factory

@Factory
class GetSelectedFilterIdUseCase(
    private val repository: FilterRepository
) {
    operator fun invoke(): Long? = repository.getSelectedId()
}
