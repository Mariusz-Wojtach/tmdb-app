package eu.wojtach.tmdbclient.presentation.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import eu.wojtach.tmdbclient.domain.model.Filter
import org.koin.compose.koinInject

@Destination<RootGraph>
@Composable
fun FiltersListScreen(
    navigator: DestinationsNavigator,
    viewModel: FiltersListViewModel = koinInject()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                FiltersListSideEffect.NavigateUp -> navigator.navigateUp()
            }
        }
    }

    FiltersListScreen(
        state = state,
        onFilterSelected = { filter ->
            viewModel.onFilterSelected(filter.id)
        },
        onRetryClick = { viewModel.retry() },
        onBackClick = { navigator.navigateUp() }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun FiltersListScreen(
    state: FiltersListState,
    onFilterSelected: (Filter) -> Unit,
    onRetryClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Filters") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            when (state) {
                is FiltersListState.Error -> Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(state.message)
                    Button(onClick = onRetryClick) {
                        Text("Retry")
                    }
                }

                FiltersListState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )

                is FiltersListState.Success -> FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.filters.map { filter ->
                        FilterChip(
                            selected = filter.id == state.selectedFilterId,
                            label = { Text(filter.name) },
                            onClick = { onFilterSelected(filter) }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun FiltersListScreenPreview() {
    MaterialTheme {
        FiltersListScreen(
            state = FiltersListState.Success(
                selectedFilterId = 1,
                filters = (1..10)
                    .map {
                        Filter(
                            id = it.toLong(),
                            name = "Filter $it"
                        )
                    }
            ),
            onFilterSelected = {},
            onRetryClick = {},
            onBackClick = {}
        )
    }
}

@Preview
@Composable
private fun FiltersListScreenLoadingPreview() {
    MaterialTheme {
        FiltersListScreen(
            state = FiltersListState.Loading,
            onFilterSelected = {},
            onRetryClick = {},
            onBackClick = {}
        )
    }
}

@Preview
@Composable
private fun FiltersListScreenErrorPreview() {
    MaterialTheme {
        FiltersListScreen(
            state = FiltersListState.Error("Error"),
            onFilterSelected = {},
            onRetryClick = {},
            onBackClick = {}
        )
    }
}
