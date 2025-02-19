package eu.wojtach.tmdbclient.presentation.movies

import eu.wojtach.tmdbclient.domain.model.Movie
import eu.wojtach.tmdbclient.presentation.filters.FiltersListState

sealed interface MoviesListState {
    data object Loading : MoviesListState
    data class Success(val movies: List<Movie>) : MoviesListState
    data class Error(val message: String): MoviesListState
}
