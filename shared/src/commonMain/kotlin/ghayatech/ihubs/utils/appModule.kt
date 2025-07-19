package ghayatech.ihubs.utils

import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import ghayatech.ihubs.networking.network.ApiService
import ghayatech.ihubs.networking.network.ChatService
import ghayatech.ihubs.networking.network.createPlatformHttpClient
import ghayatech.ihubs.networking.repository.ApiRepository
import ghayatech.ihubs.networking.repository.ChatRepository
import ghayatech.ihubs.networking.viewmodel.ChatViewModel
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import org.koin.dsl.module

val appModule = module {
    single<Settings> { Settings() }
    single { UserPreferences(get()) }
    single {
        val settings: Settings = get()
        createPlatformHttpClient(settings)
    }
//    single { createPlatformHttpClient() }
    single { ApiService(get()) }
    single { ApiRepository(get()) }
    single { MainViewModel(get()) }

    single { ChatService(get()) }
    single { ChatRepository(get()) }
    single { ChatViewModel(get()) }
}