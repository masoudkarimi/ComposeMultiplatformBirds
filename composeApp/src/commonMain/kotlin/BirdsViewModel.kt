import data.BirdImage
import data.BirdsRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class BirdsUiState(
    val isLoading: Boolean = true,
    val images: List<BirdImage> = emptyList(),
    val selectedCategory: String? = null
) {
    val categories: Set<String> = images.map(BirdImage::category).toSet()

    val selectedImages: List<BirdImage> = images.filter { it.category == selectedCategory }
}

class BirdsViewModel(
    private val birdsRepository: BirdsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(BirdsUiState())
    val uiState = _uiState.asStateFlow()

    
    fun updateImages() {
        viewModelScope.launch {
            val images = birdsRepository.getImages()
            _uiState.update { it.copy(isLoading = false, images = images) }
        }
    }

    fun selectCategory(category: String) {
        _uiState.update { state ->
            if (state.selectedCategory == category) {
                state.copy(selectedCategory = null)
            } else {
                state.copy(selectedCategory = category)
            }
        }
    }
}