package eu.wojtach.tmdbclient.presentation.filters

import eu.wojtach.tmdbclient.domain.model.Filter

data class FiltersListState(
    val isLoading: Boolean,
    val selectedFilterId: Long?,
    val filters: List<Filter>
) {
    companion object {
        val Empty = FiltersListState(
            isLoading = true,
            selectedFilterId = null,
            filters = emptyList()
        )
    }
}
