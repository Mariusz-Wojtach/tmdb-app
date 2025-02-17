package eu.wojtach.tmdbclient.presentation.movies

import eu.wojtach.tmdbclient.domain.model.Movie

data class MoviesListState(
    val isLoading: Boolean,
    val movies: List<Movie>
) {
    companion object {
        val Empty = MoviesListState(isLoading = false, movies = emptyList())
    }
}
