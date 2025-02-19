package eu.wojtach.tmdbclient.presentation.movies.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage

@Composable
fun Poster(
    posterPath: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    AsyncImage(
        modifier = modifier,
        model = "https://image.tmdb.org/t/p/original$posterPath",
        contentDescription = contentDescription,
    )
}

@Preview
@Composable
private fun PosterPreview() {
    MaterialTheme {
        Poster(posterPath = "/n6bUvigpRFqSwmPp1m2YADdbRBc.jpg")
    }
}
