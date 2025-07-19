package ghayatech.ihubs.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
//import androidx.compose.material.icons.Icons
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import ihubs.shared.generated.resources.current_bookings
import ihubs.shared.generated.resources.fullname
import ihubs.shared.generated.resources.gridlayout
import ihubs.shared.generated.resources.list
import ihubs.shared.generated.resources.location
import ihubs.shared.generated.resources.payment
import ihubs.shared.generated.resources.profile
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import ghayatech.ihubs.networking.models.Workspace
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.components.CButton
import ghayatech.ihubs.ui.components.CCheckBox
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.components.Filter
import ghayatech.ihubs.ui.components.GridContent
import ghayatech.ihubs.ui.components.ListContent
import ghayatech.ihubs.ui.components.NetworkImage
import ghayatech.ihubs.ui.components.SearchBar
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.utils.Constants
import ihubs.shared.generated.resources.done
import ihubs.shared.generated.resources.evening_shift
import ihubs.shared.generated.resources.payment_available
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject


class HubsScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings: Settings = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()

        var isList by rememberSaveable { mutableStateOf(false) }
        var payment by rememberSaveable { mutableStateOf(false) }
        var shift by rememberSaveable { mutableStateOf(false) }
        var searchInput by rememberSaveable { mutableStateOf("") }
        var img =
            if (isList) painterResource(Res.drawable.gridlayout) else painterResource(Res.drawable.list)

        var snackbarMessage by rememberSaveable { mutableStateOf<String?>(null) }
        val workspacesState by viewModel.workspacesState.collectAsState()

        var selectedLocation by rememberSaveable { mutableStateOf<String?>(null) }
        var selectedPayment by rememberSaveable { mutableStateOf<String?>(null) }

//        val searchWorkspacesState by viewModel.searchWorkspacesState.collectAsState()
        val hubsList = remember { mutableStateListOf<Workspace>() }

        // إرسال البحث بعد التوقف عن الكتابة (debounce)
//        LaunchedEffect(searchInput) {
//            snapshotFlow { searchInput }
//                .debounce(400)
//                .collectLatest { text ->
//                    if (text.isNotBlank()) {
//                        viewModel.searchWorkspaces(text)
//                    } else {
//                        viewModel.getWorkspaces()
//                    }
//                }
//        }


        fun applyFiltersAndSearch() {
//                val bankSupported = when (selectedPayment) {
//                    "Bank Oo Palestine", "Jawwal Pay", "Pal Pay" -> true
//                    else -> null
//                }

            viewModel.getFilteredWorkspaces(
                search = searchInput.takeIf { it.isNotBlank() },
                location = null,
                bankPaymentSupported = null
            )
        }


        LaunchedEffect(Unit) {
            snapshotFlow { searchInput }
                .debounce(400)
                .collectLatest {
                    applyFiltersAndSearch()
                }
        }


        Box(
            Modifier.fillMaxSize()
        ) {


//            LaunchedEffect(searchInput) {
//                snapshotFlow { searchInput }
//                    .debounce(400)
//                    .collectLatest {
//                        applyFiltersAndSearch()
//                    }
//            }
//            LaunchedEffect(Unit) {
//                viewModel.getWorkspaces()
//            }

            println("TAG" + settings.getStringOrNull(Constants.TOKEN))

//            if (snackbarMessage != null) {
//                ErrorPage(snackbarMessage.toString())
//            } else
            Column(
                modifier = Modifier.background(AppColors.White).fillMaxSize()
                    .padding(top = 60.dp, start = 14.dp, end = 14.dp, bottom = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (item in hubsList) {
                    println("TAG ${item.name}")
                }

                // Search bar and profile icon
                Row(
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    SearchBar(
                        value = searchInput,
                        onValueChange = { searchInput = it },
                        modifier = Modifier.weight(1F).padding(horizontal = 10.dp),
                        trailingIcon = img,
                        onTrailingIconClick = {
                            isList = !isList
                        })

                    Box(Modifier.shadow(10.dp, shape = CircleShape, clip = true).clickable {
                        navigator.push(ProfileScreen())
                    }) {

                        Icon(
                            painter = painterResource(Res.drawable.profile),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(AppColors.Background)
                                .padding(10.dp), AppColors.TextSecondary
                        )

                    }

                }

                Spacer(modifier = Modifier.size(10.dp))
                val locations = listOf("gaza", "khanyounis", "rafah", "AL-Wusta")
                val paymentMethods = listOf("Bank Oo Palestine", "Jawwal Pay", "Pal Pay")
                // Filters
                Row(horizontalArrangement = Arrangement.Center) {
                    Filter(
                        dataList = locations,
                        placeholder = stringResource(Res.string.location),
                        onItemSelected = {
                            applyFiltersAndSearch()
                        },
                    )
                    Filter(
                        dataList = paymentMethods,
                        placeholder = stringResource(Res.string.payment),
                        onItemSelected = {
                            applyFiltersAndSearch()

                        },
                    )
                }
//                Spacer(modifier = Modifier.size(6.dp))

                Row {

                    CCheckBox(
                        modifier = Modifier.fillMaxWidth().weight(1F),
                        text = stringResource(Res.string.payment_available),
                        checkbox = payment,
                        onCheckedChange = {
                            payment = it
//                            applyFiltersAndSearch()
                        }
                    )

                    CCheckBox(
                        modifier = Modifier.fillMaxWidth().weight(1F),
                        text = stringResource(Res.string.evening_shift),
                        checkbox = shift,
                        onCheckedChange = {
                            shift = it
//                            applyFiltersAndSearch()
                        }
                    )
//                    CCheckBox()

                }

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {


                    if (hubsList.isEmpty()) {
//                        NoResult()
                    } else {
                        if (isList) {
                            ListContent(hubsList) { item ->
                                println("List $item")
                                navigator.push(
                                    HubDetailsScreen(item.id)
                                )
                            }
                        } else {
                            GridContent(hubsList, onItemClicked = { item ->
                                println("List $item")
                                navigator.push(
                                    HubDetailsScreen(item.id)
                                )
                            }
//                                , itemContent = { item ->
//                                Column {
//
//                                    NetworkImage(item.logo)
//                                    Spacer(Modifier.size(15.dp))
//                                    CText(
//                                        stringResource(Res.string.fullname),
//                                        fontFamily = Res.font.bold,
//                                    )
//
//                                }
//                            }
                            )
//                        {
//                            navigator.push(
//                                ghayatech.ihubs.ui.screens.HubDetailsScreen(
//                                    viewModel
//                                )
//                            )
//                        }
                        }
                    }
                    CButton(
                        text = stringResource(Res.string.current_bookings),
                        modifier = Modifier.align(Alignment.BottomCenter),
                        onClick = {
                            navigator.push(
                                BookingDetailsScreen(

                                )
                            )
                        })
                }


            }


            HandleUiState(
                state = workspacesState,
                onMessage = {
                    snackbarMessage = it
//                    showErrorPage(it)
                    println("onMessage $it")
                },
                onSuccess = { data ->
                    hubsList.clear()
                    hubsList.addAll(
                        data.filter { it.name.isNotEmpty() && !it.description.isNullOrEmpty() }
                    )
                    println("TAGTAG Hubs: ${data}")


                },
                hasProgressBar = true
            )


        }
    }


}


//@HotPreview
@Composable
fun PreviewHomeScreen() {
//    MaterialTheme {
//        val viewModel: MainViewModel = rememberKoinInject()
//
//        HubsScreen(viewModel)
//    }
}
