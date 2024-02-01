import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource


@Composable
fun App() {
    MaterialTheme {
        val birdsViewModel = getViewModel(Unit, viewModelFactory { BirdsViewModel() })
        val uiState by birdsViewModel.uiState.collectAsState()

        LaunchedEffect(birdsViewModel) {
            birdsViewModel.updateImages()
        }

        BirdsPage(uiState, Modifier.fillMaxSize())
    }
}

@Composable
fun BirdsPage(uiState: BirdsUiState, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        } else {
            Column {
                Row {  }
                AnimatedVisibility(visible = uiState.images.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(180.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        items(uiState.images) { image ->
                            BirdImageCell(image)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BirdImageCell(image: BirdImage) {
    KamelImage(
        resource = asyncPainterResource(data = "https://sebastianaigner.github.io/demo-image-api/${image.path}"),
        contentDescription = "${image.category} by ${image.author}",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth().aspectRatio(1.0f)
    )
}
