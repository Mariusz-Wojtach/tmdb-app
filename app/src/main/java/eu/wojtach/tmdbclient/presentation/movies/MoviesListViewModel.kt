package eu.wojtach.tmdbclient.presentation.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.wojtach.tmdbclient.data.remote.DataSource
import eu.wojtach.tmdbclient.domain.model.Movie
import eu.wojtach.tmdbclient.domain.repository.MovieRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MoviesListViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MoviesListState.Empty)
    val state = _state
        .onStart { initLoad() }
        .stateIn(viewModelScope, SharingStarted.Lazily, MoviesListState.Empty)

    private suspend fun initLoad() {
        val response = repository.getMovies(1)

        _state.value = state.value.copy(
            isLoading = false,
            movies = response
        )
    }
}
