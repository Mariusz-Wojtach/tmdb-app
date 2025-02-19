package eu.wojtach.tmdbclient.presentation.filters

import eu.wojtach.tmdbclient.domain.model.Filter

data class FiltersListState(
    val isLoading: Boolean,
    val filters: List<Filter>
) {
    companion object {
        val Empty = FiltersListState(isLoading = false, filters = emptyList())
    }
}
