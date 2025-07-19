package ghayatech.ihubs.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ihubs.shared.generated.resources.Res
import ihubs.shared.generated.resources.bold
import ihubs.shared.generated.resources.bookings
import ihubs.shared.generated.resources.books_date
import ihubs.shared.generated.resources.books_time
import ihubs.shared.generated.resources.date
import ihubs.shared.generated.resources.ghayatech
import ihubs.shared.generated.resources.`package`
import ihubs.shared.generated.resources.password
import ihubs.shared.generated.resources.profile
import ihubs.shared.generated.resources.seat_free
import ihubs.shared.generated.resources.seat_number
import ihubs.shared.generated.resources.time
import ihubs.shared.generated.resources.username
import ghayatech.ihubs.networking.models.CreateBookingResponse
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.MainViewModel
//import ghayatech.ihubs.app_navigation.Screen
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.components.CustomTopBar
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject


class BookingsScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings: Settings = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()


        var snackbarMessage by rememberSaveable { mutableStateOf<String?>(null) }
        val bookingState by viewModel.bookingState.collectAsState()
        val bookingsList = remember { mutableStateListOf<CreateBookingResponse>() }

        LaunchedEffect(Unit) {
            viewModel.getBookings()
        }
        Box(Modifier.fillMaxSize()) {


            Column(
                modifier = Modifier.fillMaxSize()
                    .background(AppColors.White)
                    .padding(top = 60.dp, start = 16.dp, end = 16.dp)

            ) {
                CustomTopBar(title = stringResource(Res.string.bookings), onBackClick = {
                    navigator.pop()
                })

                Spacer(modifier = Modifier.size(24.dp))


                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(bookingsList) { item ->

                        Column(
                            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                .padding(vertical = 7.dp)

                                .shadow(
                                    elevation = 5.dp, // قوة الظل
                                    shape = RoundedCornerShape(25.dp),
                                    clip = false // اجعله false لتطبيق الظل خارج الشك
                                )
                                .background(
                                    color = AppColors.Background,
                                    shape = RoundedCornerShape(25.dp)
                                )
                                .padding(horizontal = 20.dp, vertical = 16.dp)
                        ) {

                            Row {
                                Column {
//                                    NetworkImage()
                                    Image(
                                        painterResource(Res.drawable.ghayatech),
                                        contentDescription = "hub image",
                                        modifier = Modifier
                                            .size(56.dp)
                                            .background(
                                                AppColors.White,
                                                shape = RoundedCornerShape(15.dp)
                                            )
                                            .padding(10.dp),
                                    )
                                    Spacer(modifier = Modifier.size(6.dp))
                                    CText(
                                        text = item.workspaceName.en,
                                        fontFamily = Res.font.bold,
                                        fontSize = 12.sp
                                    )
                                }
                                Spacer(modifier = Modifier.size(10.dp))
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Spacer(modifier = Modifier.size(10.dp))
                                    // Package
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        CText(
                                            text = "${stringResource(Res.string.`package`)} :",
                                            color = AppColors.Primary,
                                            fontFamily = Res.font.bold
                                        )
                                        Spacer(modifier = Modifier.size(12.dp))
                                        CText(
                                            text = item.packageName,
                                            color = AppColors.Black,
                                            fontFamily = Res.font.bold,
                                        )
                                    }
//                        Spacer(modifier = Modifier.size(12.dp))


                                    // Seat
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        CText(
                                            text = "${stringResource(Res.string.seat_number)} :",
                                            color = AppColors.Primary,
                                            fontFamily = Res.font.bold
                                        )
                                        Spacer(modifier = Modifier.size(12.dp))
                                        CText(
                                            text = item.seatNumber?: stringResource(Res.string.seat_free),
                                            color = AppColors.Black,
                                            fontFamily = Res.font.bold,
                                        )
                                    }
                                }
                            }


                            Spacer(modifier = Modifier.size(12.dp))

                            // booking date
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Image(
                                    painterResource(Res.drawable.date),
                                    contentDescription = "date",
                                )
                                CText(
                                    text = "${stringResource(Res.string.books_date)} :",
                                    color = AppColors.Primary,
                                    fontFamily = Res.font.bold
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                                CText(
                                    text = item.endAt.toString(),
                                    color = AppColors.Black,
                                    fontFamily = Res.font.bold, modifier = Modifier.weight(1F)
                                )

                            }
                            Spacer(modifier = Modifier.size(12.dp))


                            // booking time
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Image(
                                    painterResource(Res.drawable.time),
                                    contentDescription = "time",
                                )
                                CText(
                                    text = "${stringResource(Res.string.books_time)} :",
                                    color = AppColors.Primary,
                                    fontFamily = Res.font.bold
                                )
                                Spacer(modifier = Modifier.size(12.dp))
                                CText(
                                    text = item.endAt.toString(),
                                    color = AppColors.Black,
                                    fontFamily = Res.font.bold,
                                )
                            }
                            Spacer(modifier = Modifier.size(12.dp))


                            //username
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Spacer(modifier = Modifier.size(2.dp))

                                Image(
                                    painterResource(Res.drawable.profile),
                                    contentDescription = "username",
                                )
                                Spacer(modifier = Modifier.size(4.dp))

                                CText(
                                    text = "${stringResource(Res.string.username)} :",
                                    color = AppColors.Primary,
                                    fontFamily = Res.font.bold
                                )
                                Spacer(modifier = Modifier.size(12.dp))
                                CText(
                                    text = stringResource(Res.string.username),
                                    color = AppColors.Black,
                                    fontFamily = Res.font.bold,
                                )
                            }
                            Spacer(modifier = Modifier.size(12.dp))


                            //password
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Image(
                                    painterResource(Res.drawable.password),
                                    contentDescription = "password",
                                )

                                CText(
                                    text = "${stringResource(Res.string.password)} :",
                                    color = AppColors.Primary,
                                    fontFamily = Res.font.bold
                                )
                                Spacer(modifier = Modifier.size(12.dp))
                                CText(
                                    text = stringResource(Res.string.password),
                                    color = AppColors.Black,
                                    fontFamily = Res.font.bold,
                                )
                            }


                        }


                    }

                }

            }

            HandleUiState(
                state = bookingState,
                onMessage =
                    {
                        snackbarMessage = it
                    },
                onSuccess =
                    { data ->
                        bookingsList.addAll(data)
                    }
            )

        }
    }

}