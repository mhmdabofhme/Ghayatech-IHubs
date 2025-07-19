package ghayatech.ihubs

import androidx.compose.ui.window.ComposeUIViewController
import ghayatech.ihubs.utils.appModule
import org.koin.core.context.startKoin


fun MainViewController() = ComposeUIViewController {
    startKoin {
        modules(appModule)
    }
    App()
}