package eu.wojtach.tmdbclient.presentation.filters

import eu.wojtach.tmdbclient.domain.error.DataError
import eu.wojtach.tmdbclient.domain.model.Filter

sealed interface FiltersListState {
    data object Loading : FiltersListState
    data class Success(
        val selectedFilterId: Long?,
        val filters: List<Filter>
    ) : FiltersListState

    data class Error(val message: String): FiltersListState
}
