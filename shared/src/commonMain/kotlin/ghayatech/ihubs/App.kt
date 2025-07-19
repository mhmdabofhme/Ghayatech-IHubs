package ghayatech.ihubs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import de.drick.compose.hotpreview.HotPreview
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.screens.HubsScreen
import ghayatech.ihubs.ui.screens.NotificationsScreen
import ghayatech.ihubs.ui.screens.PrivacyScreen
import ghayatech.ihubs.ui.screens.SplashScreen
import ghayatech.ihubs.ui.theme.AppColors
import org.koin.compose.rememberKoinInject


@Composable
fun App() {
    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize().background(AppColors.White)
                .windowInsetsPadding(WindowInsets.navigationBars)

//                .padding(WindowInsets.navigationBars.asPaddingValues())
//                .padding(vertical = 27.dp)
        ) {
            val viewModel: MainViewModel = rememberKoinInject()
            Navigator(SplashScreen()) { navigator ->
                SlideTransition(navigator)
            }
        }
    }
}

//
//@HotPreview(widthDp = 411, heightDp = 891, density = 2.625f)
//@Composable
//fun PreviewScreen() {
//    CText(text = "Hello from Preview")
//}

class PlatformGreeting {
    fun greet(): String = "Hello from KMP"
}