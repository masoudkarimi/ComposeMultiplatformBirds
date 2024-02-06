import data.BirdsRepository
import data.BirdsRepositoryImpl
import dev.icerock.moko.mvvm.compose.ViewModelFactory
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.dsl.module

fun appModule() = module {
    
    single<HttpClient> {
        HttpClient {
            defaultRequest {
                url("https://sebi.io/demo-image-api/")
            }

            install(ContentNegotiation) {
                json()
            }
        }
    }

    single<BirdsRepository> { BirdsRepositoryImpl(get()) }
    
    factory<ViewModelFactory<BirdsViewModel>> {
        viewModelFactory { BirdsViewModel(get()) }
    }
}