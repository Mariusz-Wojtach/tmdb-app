package eu.wojtach.tmdbclient.presentation.filters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.wojtach.tmdbclient.domain.repository.MovieRepository
import eu.wojtach.tmdbclient.domain.usecase.GetAllFiltersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class FiltersListViewModel(
    private val getAllFiltersUseCase: GetAllFiltersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FiltersListState.Empty)
    val state = _state
        .onStart { initLoad() }
        .stateIn(viewModelScope, SharingStarted.Lazily, FiltersListState.Empty)

    private suspend fun initLoad() {
        val response = getAllFiltersUseCase.getAll()

        _state.value = state.value.copy(
            isLoading = false,
            filters = response
        )
    }
}
