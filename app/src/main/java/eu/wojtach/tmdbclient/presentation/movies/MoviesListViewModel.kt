package eu.wojtach.tmdbclient.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.wojtach.tmdbclient.domain.model.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MoviesListViewModel : ViewModel() {

    private val _state = MutableStateFlow(MoviesListState.Empty)
    val state = _state
        .onStart { }
        .stateIn(viewModelScope, SharingStarted.Lazily, MoviesListState.Empty)

    private suspend fun initLoad() {
        delay(1000L)
        _state.value = state.value.copy(
            isLoading = false,
            listOf(
                Movie(
                    id = 1000,
                    title = "Movie 1",
                    posterPath = "posterPath",
                    rating = 5.0f,
                    details = null
                )
            )
        )
    }
}
