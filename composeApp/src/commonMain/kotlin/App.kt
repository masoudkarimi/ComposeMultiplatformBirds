import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun BirdAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(primary = Color.Black),
        shapes = MaterialTheme.shapes.copy(
            small = RoundedCornerShape(0.dp),
            medium = RoundedCornerShape(0.dp),
            large = RoundedCornerShape(0.dp),
        )
    ) {
        content()
    }
}

@Composable
fun App() {
    BirdAppTheme {
        val birdsViewModel = getViewModel(Unit, viewModelFactory { BirdsViewModel() })
        val uiState by birdsViewModel.uiState.collectAsState()

        LaunchedEffect(birdsViewModel) {
            birdsViewModel.updateImages()
        }

        BirdsPage(uiState, birdsViewModel::selectCategory, Modifier.fillMaxSize())
    }
}

@Composable
fun BirdsPage(
    uiState: BirdsUiState,
    onSelectCategory: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth().padding(4.dp)
                ) {
                    for (category in uiState.categories) {
                        Button(
                            onClick = {
                                onSelectCategory(category)
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (uiState.selectedCategory == category) {
                                    Color.Green
                                } else {
                                    MaterialTheme.colors.primary
                                }
                            ),
                            modifier = Modifier
                                .aspectRatio(1.4f).weight(1f)
                        ) {
                            Text(category)
                        }
                    }
                }
                AnimatedVisibility(visible = uiState.selectedImages.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(180.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        items(uiState.selectedImages) { image ->
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
