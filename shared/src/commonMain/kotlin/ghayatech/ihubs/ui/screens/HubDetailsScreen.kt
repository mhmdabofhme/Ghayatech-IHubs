package ghayatech.ihubs.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ihubs.shared.generated.resources.Res
import ihubs.shared.generated.resources.bold
import ihubs.shared.generated.resources.book_now
import ihubs.shared.generated.resources.contactus
import ihubs.shared.generated.resources.hub_features
import ihubs.shared.generated.resources.location
import ihubs.shared.generated.resources.no_data
import ihubs.shared.generated.resources.place_photos
import ihubs.shared.generated.resources.resource_default
import ihubs.shared.generated.resources.star
import ghayatech.ihubs.networking.models.WorkspaceDetails
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.components.Back
import ghayatech.ihubs.ui.components.CButton
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.components.ImageSlider
import ghayatech.ihubs.ui.components.NetworkImage
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject

// HubDetailsScreen.kt
class HubDetailsScreen( private val id: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings: Settings = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()

        val img = painterResource(Res.drawable.resource_default)

        var snackbarMessage by remember { mutableStateOf<String?>(null) }
        val workspace = remember { mutableStateOf<WorkspaceDetails?>(null) }
        val workspaceState by viewModel.workspaceState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getWorkspace(id)
        }

        Box(Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier.fillMaxSize()
                    .background(AppColors.White)
                    .padding(top = 60.dp, start = 22.dp, end = 22.dp)
                    .verticalScroll(rememberScrollState())
            )
            {

//                val images = listOf(
//                    painterResource(Res.drawable.logo),
//                    painterResource(Res.drawable.logo),
//                    painterResource(Res.drawable.logo),
//                )


                Box(modifier = Modifier.fillMaxWidth()) {

                    Back(
                        modifier = Modifier.align(Alignment.CenterStart),
                        onBackClick = {
                            navigator.pop()
                        }
                    )

                    NetworkImage(
                        workspace.value?.logo,
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .size(75.dp)
                            .background(
                                AppColors.coolBackground,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .clip(RoundedCornerShape(15.dp))
                            .align(Alignment.Center),
                    )


                }
                Spacer(modifier = Modifier.size(4.dp))

                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    CText(
                        workspace.value?.name ?: "",
                        fontSize = 18.sp,
                        fontFamily = Res.font.bold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(Res.drawable.location),
                            contentDescription = "Location"
                        )
                        CText(
                            workspace.value?.location ?: "",
                            color = AppColors.purple,

                            fontSize = 12.sp,
                            fontFamily = Res.font.bold
                        )
                    }

                    Spacer(modifier = Modifier.size(4.dp))
                    CText(
                        workspace.value?.description ?: "",
                        color = AppColors.TextSecondary,
                        fontSize = 12.sp,
                        fontFamily = Res.font.bold
                    )

                    Spacer(modifier = Modifier.size(14.dp))

                    CText(
                        stringResource(Res.string.place_photos),
                        fontSize = 18.sp,
                        fontFamily = Res.font.bold
                    )

                }

                Box(
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
                ) {
                    val images = workspace.value?.images.orEmpty()

                    if (images.isNotEmpty()) {
                        ImageSlider(images = images)
                    } else {
                        CText(
                            text = stringResource(Res.string.no_data),
                            color = AppColors.TextSecondary,
                        )
                    }
                    ImageSlider(images = images)

                }
                Spacer(modifier = Modifier.size(15.dp))
                CText(
                    stringResource(Res.string.hub_features),
                    style = TextDecoration.Underline,
                    color = AppColors.Secondary,
                    fontWeight = FontWeight.Bold
                )

                val features = workspace.value?.shortServices.orEmpty()

                if (features.isNotEmpty()) {
                    features.forEach { feature ->
                        Row(
                            modifier = Modifier.padding(vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painterResource(Res.drawable.star),
                                contentDescription = "star"
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            CText(text = feature, fontWeight = FontWeight.Bold)
                        }
                    }
                } else {
                    CText(
                        text = stringResource(Res.string.no_data),
                        color = AppColors.TextSecondary,
                    )
                }

                Spacer(modifier = Modifier.size(20.dp))
                CButton(
                    text = stringResource(Res.string.book_now),
                    onClick = {
                        navigator.push(OurPackagesScreen( id))
                    })
                Spacer(modifier = Modifier.height(10.dp))
                CButton(
                    text = stringResource(Res.string.contactus),
                    isOutlined = true,
                    onClick = {
                        navigator.push(ChatScreen())
                    })


            }


            HandleUiState(
                state = workspaceState,
                onMessage = {
                    snackbarMessage = it

                },
                onSuccess = { data ->
                    workspace.value = data
                }
            )

        }

    }
}