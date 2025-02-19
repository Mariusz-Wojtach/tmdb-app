package eu.wojtach.tmdbclient.data.repository

import eu.wojtach.tmdbclient.domain.model.Filter
import eu.wojtach.tmdbclient.domain.repository.FilterRepository
import eu.wojtach.tmdbclient.domain.usecase.GetAllFiltersUseCase
import org.koin.core.annotation.Single

@Single(binds = [GetAllFiltersUseCase::class])
class FakeMovieRepository : FilterRepository {

    override suspend fun getAll(): List<Filter> {
        return listOf(
            Filter(id = 28, name = "Action"),
            Filter(id = 12, name = "Adventure"),
            Filter(id = 16, name = "Animation"),
            Filter(id = 35, name = "Comedy"),
            Filter(id = 80, name = "Crime"),
            Filter(id = 99, name = "Documentary"),
            Filter(id = 18, name = "Drama"),
            Filter(id = 10751, name = "Family"),
            Filter(id = 14, name = "Fantasy"),
            Filter(id = 36, name = "History"),
            Filter(id = 27, name = "Horror"),
            Filter(id = 10402, name = "Music"),
            Filter(id = 9648, name = "Mystery"),
            Filter(id = 10749, name = "Romance"),
            Filter(id = 878, name = "Science Fiction"),
            Filter(id = 10770, name = "TV Movie"),
            Filter(id = 53, name = "Thriller"),
            Filter(id = 10752, name = "War"),
            Filter(id = 37, name = "Western")
        )
    }
}
