package data

interface BirdsRepository {
    suspend fun getImages(): List<BirdImage>
}