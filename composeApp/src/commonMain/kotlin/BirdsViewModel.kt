import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class BirdsUiState(
    val isLoading: Boolean = true,
    val images: List<BirdImage> = emptyList()
)

class BirdsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BirdsUiState())
    val uiState = _uiState.asStateFlow()
    
    
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }


    fun updateImages() {
        viewModelScope.launch {
            val images = getImages()
            _uiState.update { it.copy(isLoading = false, images = images) }
        }
    }
    private suspend fun getImages(): List<BirdImage> = httpClient
        .get(urlString = "https://sebi.io/demo-image-api/pictures.json")
        .body<List<BirdImage>>()

    override fun onCleared() {
        httpClient.close()
    }
}