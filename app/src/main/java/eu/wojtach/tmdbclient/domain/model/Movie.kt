package eu.wojtach.tmdbclient.domain.model

data class Movie(
    val id: Long,
    val title: String,
    val posterPath: String,
    val rating: Float,
    val details: Details?
) {
    data class Details(
        val budget: Long,
        val revenue: Long
    )
}
