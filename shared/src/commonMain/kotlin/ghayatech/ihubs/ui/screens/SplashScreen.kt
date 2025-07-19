package ghayatech.ihubs.ui.screens

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ihubs.shared.generated.resources.Res
import ihubs.shared.generated.resources.ghayatech
import ihubs.shared.generated.resources.logo
import ihubs.shared.generated.resources.powered_by
import ihubs.shared.generated.resources.white_logo
import kotlinx.coroutines.delay
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.utils.Constants
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject


class SplashScreen() : Screen {
    @Composable
    override fun Content() {
        val settings: Settings = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()

        val navigator = LocalNavigator.currentOrThrow
        val backgroundColor = AppColors.White
        var showLogo by remember { mutableStateOf(false) }
        var showDeveloperInfo by remember { mutableStateOf(false) }


        val alphaLogo = remember { Animatable(0f) }
        val alphaContent = remember { Animatable(0f) }


        LaunchedEffect(true) {
            delay(300)
            showLogo = true
            alphaLogo.animateTo(1f, tween(1500))

            delay(200) // تأخير قبل ظهور باقي المحتوى
            showDeveloperInfo = true
            alphaContent.animateTo(1f, tween(1000))
            Log.d("TAG", "Content: ${settings.getStringOrNull(Constants.TOKEN)}")
            delay(1000)
            if (settings.getBooleanOrNull(Constants.IS_ONBOARDING) != null) {
                if (settings.getStringOrNull(Constants.TOKEN) != null) {
                    navigator.push(HubsScreen())
                } else {
                    navigator.push(LoginScreen())
                }
            } else {
                navigator.push(OnBoardingScreen())
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            if (showLogo) {
                val logo : Painter =if (isSystemInDarkTheme()) painterResource(Res.drawable.white_logo) else painterResource(Res.drawable.logo)
                Image(
                    painter = logo,
                    contentDescription = "Logo",
                    modifier = Modifier
                        .graphicsLayer { alpha = alphaLogo.value }
                        .size(150.dp)
                )

            }

            if (showDeveloperInfo) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 32.dp)
                        .graphicsLayer { alpha = alphaContent.value },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val color  = if (isSystemInDarkTheme()) AppColors.Black else AppColors.Primary
                    CText(
                        text = stringResource(Res.string.powered_by),
                        color = color
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(Res.drawable.ghayatech),
                        modifier = Modifier.size(56.dp),
                        contentDescription = "Developer Logo"
                    )
                }
            }
        }
    }
}


