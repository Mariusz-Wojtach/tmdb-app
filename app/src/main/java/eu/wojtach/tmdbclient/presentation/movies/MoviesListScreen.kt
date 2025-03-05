package eu.wojtach.tmdbclient.presentation.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.FiltersListScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import eu.wojtach.tmdbclient.domain.model.Movie
import eu.wojtach.tmdbclient.presentation.movies.ui.Poster
import org.koin.compose.koinInject

@Destination<RootGraph>(start = true)
@Composable
fun MoviesListScreen(
    navigator: DestinationsNavigator,
    viewModel: MoviesListViewModel = koinInject()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MoviesListScreen(
        state,
        onFilterClick = { navigator.navigate(FiltersListScreenDestination) },
        onRetryClick = viewModel::retry
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MoviesListScreen(
    state: MoviesListState,
    onFilterClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Movies") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFilterClick
            ) {
                Icon(Icons.Filled.Edit, contentDescription = "Filter")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (state) {
                is MoviesListState.Error -> Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(state.message)
                    Button(onClick = onRetryClick) { Text("Retry") }
                }
                MoviesListState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )

                is MoviesListState.Success -> LazyVerticalGrid(
                    columns = GridCells.Fixed(3)
                ) {
                    items(items = state.movies, key = { movie -> movie.id }) { movie ->
                        Column {
                            Poster(posterPath = movie.posterPath)
                            Text(movie.title)
                            Text(movie.rating.toString())
                            if (movie.details != null) {
                                Text("Budget: ${movie.details.budget}")
                                Text("Revenue: ${movie.details.revenue}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MoviesListScreenPreview() {
    MaterialTheme {
        MoviesListScreen(
            state = MoviesListState.Success(
                movies = (1..10)
                    .map {
                        Movie(
                            id = it * 10L,
                            title = "Movie $it",
                            posterPath = "posterPath",
                            rating = 5.0f,
                            details = null
                        )
                    }
            ),
            onFilterClick = {},
            onRetryClick = {}
        )
    }
}

@Preview
@Composable
private fun MoviesListScreenLoadingPreview() {
    MaterialTheme {
        MoviesListScreen(
            state = MoviesListState.Loading,
            onFilterClick = {},
            onRetryClick = {}
        )
    }
}

@Preview
@Composable
private fun MoviesListScreenErrorPreview() {
    MaterialTheme {
        MoviesListScreen(
            state = MoviesListState.Error("Error"),
            onFilterClick = {},
            onRetryClick = {}
        )
    }
}
