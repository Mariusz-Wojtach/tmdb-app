package eu.wojtach.tmdbclient.presentation.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
        onFilterClick = { navigator.navigate(FiltersListScreenDestination) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MoviesListScreen(
    state: MoviesListState,
    onFilterClick: () -> Unit
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
        LazyVerticalGrid(
            modifier = Modifier.padding(innerPadding),
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

@Preview
@Composable
private fun MoviesListScreenPreview() {
    MaterialTheme {
        MoviesListScreen(
            state = MoviesListState(
                isLoading = false,
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
            onFilterClick = {}
        )
    }
}
