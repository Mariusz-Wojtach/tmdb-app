package eu.wojtach.tmdbclient.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.wojtach.tmdbclient.domain.error.DataError
import eu.wojtach.tmdbclient.domain.model.Network
import eu.wojtach.tmdbclient.domain.repository.NetworkRepository
import eu.wojtach.tmdbclient.domain.result.Result
import eu.wojtach.tmdbclient.domain.usecase.GetMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MoviesListViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    networkRepository: NetworkRepository
) : ViewModel() {

    private val networkState = networkRepository.isConnected
        .distinctUntilChanged()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            Network.UNDEFINED
        )

    private val _state = MutableStateFlow<MoviesListState>(MoviesListState.Loading)
    val state = _state
        .onStart { initLoad() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            MoviesListState.Loading
        )

    init {
        viewModelScope.launch {
            networkState.collect { networkState ->
                if (networkState == Network.AVAILABLE && _state.value is MoviesListState.Error) {
                    initLoad()
                } else if (networkState == Network.LOST && _state.value is MoviesListState.Loading) {
                    _state.value = MoviesListState.Error("No network")
                }
            }
        }
    }

    private suspend fun initLoad() {
        val newState = when (val response = getMoviesUseCase(1)) {
            is Result.Error -> when (response.error) {
                DataError.UnknownHost -> MoviesListState.Error("No network")
                DataError.Timeout -> MoviesListState.Error("Timeout")
                else -> MoviesListState.Error("Unknown")
            }

            is Result.Success -> MoviesListState.Success(response.data)
        }

        _state.value = newState

    }

    fun retry() {
        _state.value = MoviesListState.Loading
        viewModelScope.launch {
            initLoad()
        }
    }
}
