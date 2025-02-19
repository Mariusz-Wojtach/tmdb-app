package eu.wojtach.tmdbclient.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.wojtach.tmdbclient.domain.error.DataError
import eu.wojtach.tmdbclient.domain.result.Result
import eu.wojtach.tmdbclient.domain.usecase.GetMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MoviesListViewModel(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MoviesListState>(MoviesListState.Loading)
    val state = _state
        .onStart { initLoad() }
        .stateIn(viewModelScope, SharingStarted.Lazily, MoviesListState.Loading)

    private suspend fun initLoad() {
        val response = getMoviesUseCase(1)

        val newState = when (response) {
            is Result.Error -> when (response.error) {
                DataError.Timeout -> MoviesListState.Error("Timeout")
                else -> MoviesListState.Error("Unknown")
            }

            is Result.Success -> MoviesListState.Success(response.data)
        }

        _state.value = newState

    }

    fun refreshMovies() {
        _state.value = MoviesListState.Loading
        viewModelScope.launch {
            initLoad()
        }
    }
}
