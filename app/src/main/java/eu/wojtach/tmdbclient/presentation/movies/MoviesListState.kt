package eu.wojtach.tmdbclient.presentation.movies

import eu.wojtach.tmdbclient.domain.model.Movie

sealed interface MoviesListState {
    data object Loading : MoviesListState
    data class Success(val movies: List<Movie>) : MoviesListState
}
