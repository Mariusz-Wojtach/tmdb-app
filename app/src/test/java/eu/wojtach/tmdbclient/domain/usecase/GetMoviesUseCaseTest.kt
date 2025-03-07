package eu.wojtach.tmdbclient.domain.usecase

import eu.wojtach.tmdbclient.domain.error.DataError
import eu.wojtach.tmdbclient.domain.model.Movie
import eu.wojtach.tmdbclient.domain.repository.FilterRepository
import eu.wojtach.tmdbclient.domain.repository.MovieRepository
import eu.wojtach.tmdbclient.domain.result.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class GetMoviesUseCaseTest {

 private lateinit var filterRepository: FilterRepository
 private lateinit var movieRepository: MovieRepository
 private lateinit var getMoviesUseCase: GetMoviesUseCase

 @Before
 fun setUp() {
  filterRepository = mockk()
  movieRepository = mockk()
  getMoviesUseCase = GetMoviesUseCase(filterRepository, movieRepository)
 }

 @Test
 fun `invoke returns success when repositories return success`() = runBlocking {
  // Given
  val filterId = 123L
  val page = 1
  val movies = listOf(Movie(1L, "Test Movie", "path", 8.5f, null))

  coEvery { filterRepository.getSelectedId() } returns filterId
  coEvery { movieRepository.getAll(page, filterId) } returns Result.Success(movies)

  // When
  val result = getMoviesUseCase(page)

  // Then
  assertTrue(result is Result.Success)
  assertEquals(movies, (result as Result.Success).data)
  coVerify { filterRepository.getSelectedId() }
  coVerify { movieRepository.getAll(page, filterId) }
 }

 @Test
 fun `invoke returns error when movie repository returns error`() = runBlocking {
  // Given
  val filterId = 123L
  val page = 1
  val error = DataError.Timeout

  coEvery { filterRepository.getSelectedId() } returns filterId
  coEvery { movieRepository.getAll(page, filterId) } returns Result.Error(error)

  // When
  val result = getMoviesUseCase(page)

  // Then
  assertTrue(result is Result.Error)
  assertEquals(error, (result as Result.Error).error)
 }

 @Test
 fun `invoke passes correct page number to repository`() = runBlocking {
  // Given
  val filterId = 123L
  val page = 5

  coEvery { filterRepository.getSelectedId() } returns filterId
  coEvery { movieRepository.getAll(page, filterId) } returns Result.Success(emptyList())

  // When
  getMoviesUseCase(page)

  // Then
  coVerify { movieRepository.getAll(page, filterId) }
 }
}
