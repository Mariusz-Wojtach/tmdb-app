package eu.wojtach.tmdbclient.presentation.filters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.wojtach.tmdbclient.domain.usecase.ClearSelectedFilterIdUseCase
import eu.wojtach.tmdbclient.domain.usecase.GetAllFiltersUseCase
import eu.wojtach.tmdbclient.domain.usecase.GetSelectedFilterIdUseCase
import eu.wojtach.tmdbclient.domain.usecase.SetSelectedFilterIdUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class FiltersListViewModel(
    private val getSelectedFilterIdUseCase: GetSelectedFilterIdUseCase,
    private val setSelectedFilterIdUseCase: SetSelectedFilterIdUseCase,
    private val clearSelectedFilterIdUseCase: ClearSelectedFilterIdUseCase,
    private val getAllFiltersUseCase: GetAllFiltersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FiltersListState.Empty)
    val state = _state
        .onStart { initLoad() }
        .stateIn(viewModelScope, SharingStarted.Lazily, FiltersListState.Empty)

    private val _sideEffect = MutableSharedFlow<FiltersListSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private suspend fun initLoad() {
        val response = getAllFiltersUseCase()
        val selectedFilterId = getSelectedFilterIdUseCase()

        _state.value = state.value.copy(
            isLoading = false,
            selectedFilterId = selectedFilterId,
            filters = response
        )
    }

    fun onFilterSelected(id: Long) {
        if (_state.value.selectedFilterId == id) {
            clearSelectedFilterIdUseCase()
            _state.value = state.value.copy(selectedFilterId = null)
        } else {
            setSelectedFilterIdUseCase(id)
            _state.value = state.value.copy(selectedFilterId = id)
        }
        viewModelScope.launch {
            _sideEffect.emit(FiltersListSideEffect.NavigateUp)
        }
    }
}
