package data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class BirdsRepositoryImpl(
    private val httpClient: HttpClient
) : BirdsRepository {

    override suspend fun getImages(): List<BirdImage> {
        return httpClient
            .get(urlString = "pictures.json")
            .body<List<BirdImage>>()
    }
}