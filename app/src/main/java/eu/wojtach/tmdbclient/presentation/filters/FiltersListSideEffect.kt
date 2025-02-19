package eu.wojtach.tmdbclient.presentation.filters

sealed class FiltersListSideEffect {
    data object NavigateUp : FiltersListSideEffect()
}
