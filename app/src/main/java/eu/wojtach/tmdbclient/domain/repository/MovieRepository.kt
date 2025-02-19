package eu.wojtach.tmdbclient.domain.repository

import eu.wojtach.tmdbclient.domain.model.Movie

interface MovieRepository {

    suspend fun getAll(page: Int): List<Movie>
}
