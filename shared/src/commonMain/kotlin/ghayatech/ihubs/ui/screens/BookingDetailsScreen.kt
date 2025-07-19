package ghayatech.ihubs.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ihubs.shared.generated.resources.Res
import ihubs.shared.generated.resources.bold
import ihubs.shared.generated.resources.booking_details
import ihubs.shared.generated.resources.books_date
import ihubs.shared.generated.resources.books_time
import ihubs.shared.generated.resources.contactus
import ihubs.shared.generated.resources.date
import ihubs.shared.generated.resources.ghayatech
import ihubs.shared.generated.resources.our_services
import ihubs.shared.generated.resources.`package`
import ihubs.shared.generated.resources.password
import ihubs.shared.generated.resources.profile
import ihubs.shared.generated.resources.remaining_time
import ihubs.shared.generated.resources.resource_default
import ihubs.shared.generated.resources.seat_free
import ihubs.shared.generated.resources.seat_number
import ihubs.shared.generated.resources.time
import ihubs.shared.generated.resources.username
import ghayatech.ihubs.networking.models.CreateBookingResponse
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.components.CButton
import ghayatech.ihubs.ui.components.CIcon
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.components.CountdownText
import ghayatech.ihubs.ui.components.CustomTopBar
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject


class BookingDetailsScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings: Settings = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()

        val countdownDuration = 447_720_000L
        var listSize = 0
        val pagerState = rememberPagerState(pageCount = { listSize })


        var snackbarMessage by rememberSaveable { mutableStateOf<String?>(null) }
        val bookingState by viewModel.bookingState.collectAsState()
        val bookingsList = remember { mutableStateListOf<CreateBookingResponse>() }

        LaunchedEffect(Unit) {
            viewModel.getBookings()
        }

        Box(Modifier.fillMaxSize()) {
//            val screenWidth = LocalConfiguration.current.screenWidthDp.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.White)
                    .padding(top = 60.dp, start = 22.dp, end = 22.dp),
//                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                CustomTopBar(
                    showBackButton = true,
                    title = stringResource(Res.string.booking_details),
                    endContent = {
                        CIcon(
                            img = painterResource(Res.drawable.profile),
                            contentDescription = "Profile",
                            size = 44.dp,
                            background = AppColors.transparent,
                            onClick = {
//                        navigator.push(ProfileScreen)
                            })
                    },
                    onBackClick = {
                        navigator.pop()
                    })

                Spacer(modifier = Modifier.size(20.dp))

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(0.dp), // لا padding

//                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    items(bookingsList) { item ->

                        print("TAGTAG workspaceId" +item.workspaceId)

                        Column(
                            modifier = Modifier
                                .width(340.dp)
                                .wrapContentHeight()
                                .padding(5.dp)
                                .shadow(
                                    elevation = 5.dp, // قوة الظل
                                    shape = RoundedCornerShape(25.dp),
                                    clip = false // اجعله false لتطبيق الظل خارج الشك
                                )
                                .background(
                                    AppColors.Background,
                                    shape = RoundedCornerShape(25.dp)
                                )
                                .padding(vertical = 16.dp, horizontal = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painterResource(Res.drawable.resource_default),
                                contentDescription = "hub image",
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(
                                        AppColors.coolBackground,
                                        shape = RoundedCornerShape(15.dp)
                                    )
//                        .padding(10.dp),
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            CText(
                                text = stringResource(Res.string.ghayatech),
                                fontFamily = Res.font.bold
                            )
                            Spacer(modifier = Modifier.size(20.dp))

                            // Package
                            Row(Modifier.fillMaxWidth()) {
                                CText(
                                    text = "${stringResource(Res.string.`package`)} :",
                                    color = AppColors.Secondary,
                                    fontFamily = Res.font.bold

                                )
                                Spacer(modifier = Modifier.size(12.dp))
                                CText(
                                    text = item.packageName,
                                    color = AppColors.Black,
                                    fontFamily = Res.font.bold,
                                )
                            }
                            Spacer(modifier = Modifier.size(12.dp))


                            // Seat
                            Row(Modifier.fillMaxWidth()) {
                                CText(
                                    text = "${stringResource(Res.string.seat_number)} :",
                                    color = AppColors.Secondary,
                                    fontFamily = Res.font.bold
                                )
                                Spacer(modifier = Modifier.size(12.dp))
                                CText(
                                    text = item.seatNumber ?: stringResource(Res.string.seat_free),
                                    color = AppColors.Black,
                                    fontFamily =
                                        Res.font.bold,
                                )
                            }
                            Spacer(modifier = Modifier.size(12.dp))

                            // booking date
                            Row(Modifier.fillMaxWidth()) {
                                Image(
                                    painterResource(Res.drawable.date),
                                    contentDescription = "date",
                                )
                                CText(
                                    text = "${stringResource(Res.string.books_date)} :",
                                    color = AppColors.Secondary,
                                    fontFamily = Res.font.bold
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                                CText(
                                    text = item.endAt.toString(),
                                    color = AppColors.Black,
                                    fontFamily = Res.font.bold, modifier = Modifier.weight(1F)
                                )
//                                Spacer(modifier = Modifier.size(4.dp))
//                                CText(
//                                    text = "${stringResource(Res.string.left)} ${item.remainingTime}",
//                                    color = AppColors.Error,
//                                    fontFamily = FontFamily(
//                                        Font(Res.font.bold)
//                                    ),
//                                )
                            }
                            Spacer(modifier = Modifier.size(12.dp))

                            // booking time
                            Row(Modifier.fillMaxWidth()) {
                                Image(
                                    painterResource(Res.drawable.time),
                                    contentDescription = "time",
                                )
                                CText(
                                    text = "${stringResource(Res.string.books_time)} :",
                                    color = AppColors.Secondary,
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
                            Row(Modifier.fillMaxWidth()) {
                                Spacer(modifier = Modifier.size(2.dp))

                                Image(
                                    painterResource(Res.drawable.profile),
                                    contentDescription = "username",
                                )
                                Spacer(modifier = Modifier.size(4.dp))

                                CText(
                                    text = "${stringResource(Res.string.username)} :",
                                    color = AppColors.Secondary,
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
                            Row(Modifier.fillMaxWidth()) {
                                Image(
                                    painterResource(Res.drawable.password),
                                    contentDescription = "password",
                                )

                                CText(
                                    text = "${stringResource(Res.string.password)} :",
                                    color = AppColors.Secondary,
                                    fontFamily = Res.font.bold
                                )
                                Spacer(modifier = Modifier.size(12.dp))
                                CText(
                                    text = stringResource(Res.string.password),
                                    color = AppColors.Black,
                                    fontFamily = Res.font.bold,
                                )
                            }

                            Spacer(modifier = Modifier.size(26.dp))
                            CText(
                                text = "${stringResource(Res.string.remaining_time)} :",
                                color = AppColors.Secondary,
                                fontFamily = Res.font.bold
                            )

                            CountdownText(countdownDuration)

                            Spacer(modifier = Modifier.size(13.dp))


                            Row {
                                CButton(text = stringResource(Res.string.contactus), onClick = {
                                    navigator.push(ChatScreen())
                                }, modifier = Modifier.weight(1F))
                                Spacer(modifier = Modifier.size(10.dp))
                                CButton(text = stringResource(Res.string.our_services), onClick = {
                                    navigator.push(OurServicesScreen())
                                }, modifier = Modifier.weight(1F))
                            }

                        }

                    }
                }

                Spacer(Modifier.size(20.dp))

                // Indicators

                if (bookingsList.isNotEmpty()) {

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(listSize) { index ->
                            val isSelected = pagerState.currentPage == index
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .width(if (isSelected) 20.dp else 8.dp)
                                    .height(8.dp)
                                    .background(
                                        color = if (isSelected) AppColors.Secondary else AppColors.TextSecondary.copy(
                                            alpha = 0.3f
                                        ),
                                        shape = if (isSelected) RoundedCornerShape(8.dp) else CircleShape
                                    )
                            )
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