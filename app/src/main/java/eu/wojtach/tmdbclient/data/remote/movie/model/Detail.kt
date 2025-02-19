package eu.wojtach.tmdbclient.data.remote.movie.model

import eu.wojtach.tmdbclient.domain.model.Movie
import kotlinx.serialization.Serializable

@Serializable
data class Detail(
    val id: Long,
    val budget: Long,
    val revenue: Long
) {
    fun toDomain() = Movie.Details(
        budget = budget,
        revenue = revenue
    )
}
