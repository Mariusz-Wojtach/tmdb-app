package eu.wojtach.tmdbclient.presentation.filters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.wojtach.tmdbclient.domain.error.DataError
import eu.wojtach.tmdbclient.domain.model.Network
import eu.wojtach.tmdbclient.domain.repository.NetworkRepository
import eu.wojtach.tmdbclient.domain.result.Result
import eu.wojtach.tmdbclient.domain.usecase.ClearSelectedFilterIdUseCase
import eu.wojtach.tmdbclient.domain.usecase.GetAllFiltersUseCase
import eu.wojtach.tmdbclient.domain.usecase.GetSelectedFilterIdUseCase
import eu.wojtach.tmdbclient.domain.usecase.SetSelectedFilterIdUseCase
import eu.wojtach.tmdbclient.presentation.movies.MoviesListState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class FiltersListViewModel(
    private val getSelectedFilterIdUseCase: GetSelectedFilterIdUseCase,
    private val setSelectedFilterIdUseCase: SetSelectedFilterIdUseCase,
    private val clearSelectedFilterIdUseCase: ClearSelectedFilterIdUseCase,
    private val getAllFiltersUseCase: GetAllFiltersUseCase,
    networkRepository: NetworkRepository
) : ViewModel() {

    private val networkState = networkRepository.isConnected
        .distinctUntilChanged()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            Network.UNDEFINED
        )

    private val _state = MutableStateFlow<FiltersListState>(FiltersListState.Loading)
    val state = _state
        .onStart { initLoad() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            FiltersListState.Loading
        )

    private val _sideEffect = MutableSharedFlow<FiltersListSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        viewModelScope.launch {
            networkState.collect { networkState ->
                if (networkState == Network.AVAILABLE && _state.value is FiltersListState.Error) {
                    initLoad()
                } else if (networkState == Network.LOST && _state.value is FiltersListState.Loading) {
                    _state.value = FiltersListState.Error("No network")
                }
            }
        }
    }

    private suspend fun initLoad() {
        _state.value = FiltersListState.Loading
        val response = getAllFiltersUseCase()
        val selectedFilterId = getSelectedFilterIdUseCase()

        val newState = when (response) {
            is Result.Error -> {
                when (response.error) {
                    DataError.UnknownHost -> FiltersListState.Error("No network")
                    DataError.Timeout -> FiltersListState.Error("Timeout")
                    else -> FiltersListState.Error("Unknown")
                }
            }

            is Result.Success -> FiltersListState.Success(
                selectedFilterId = selectedFilterId,
                filters = response.data
            )
        }

        _state.value = newState
    }

    fun onFilterSelected(id: Long) {
        val currentState = _state.value
        if (currentState is FiltersListState.Success) {
            if (currentState.selectedFilterId == id) {
                clearSelectedFilterIdUseCase()
                _state.value = currentState.copy(selectedFilterId = null)
            } else {
                setSelectedFilterIdUseCase(id)
                _state.value = currentState.copy(selectedFilterId = id)
            }
            viewModelScope.launch {
                _sideEffect.emit(FiltersListSideEffect.NavigateUp)
            }
        }
    }

    fun retry() {
        viewModelScope.launch {
            initLoad()
        }
    }
}
