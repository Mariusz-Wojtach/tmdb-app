package eu.wojtach.tmdbclient.presentation.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import eu.wojtach.tmdbclient.domain.model.Filter
import org.koin.compose.koinInject

@Destination<RootGraph>
@Composable
fun FiltersListScreen(
    viewModel: FiltersListViewModel = koinInject()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FiltersListScreen(
        state = state,
        onFilterSelected = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun FiltersListScreen(
    state: FiltersListState,
    onFilterSelected: (Filter) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Filters") })
        }
    ) { innerPadding ->
        FlowRow(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.filters.map { filter ->
                FilterChip(
                    selected = false,
                    label = { Text(filter.name) },
                    onClick = { onFilterSelected(filter) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun FiltersListScreenPreview() {
    MaterialTheme {
        FiltersListScreen(
            state = FiltersListState(
                isLoading = false,
                filters = (1..10)
                    .map {
                        Filter(
                            id = it.toLong(),
                            name = "Filter $it"
                        )
                    }
            ),
            onFilterSelected = {}
        )
    }
}
