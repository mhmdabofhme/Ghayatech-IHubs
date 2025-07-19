package ghayatech.ihubs.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ghayatech.ihubs.networking.models.Service
import ihubs.shared.generated.resources.Res
import ihubs.shared.generated.resources.our_services
import ihubs.shared.generated.resources.search_on_services
import ghayatech.ihubs.networking.models.Workspace
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.components.CustomTopBar
import ghayatech.ihubs.ui.components.DescriptionDialog
import ghayatech.ihubs.ui.components.GridContent
import ghayatech.ihubs.ui.components.SearchBar
import ghayatech.ihubs.ui.components.ServiceItem
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject


class OurServicesScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings: Settings = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()

        var showDialog by rememberSaveable { mutableStateOf(false) }
        var searchInput by rememberSaveable { mutableStateOf("") }
        val servicesList = remember { mutableStateListOf<Service>() }
        val servicesState by viewModel.workspaceServicesState.collectAsState()



//        LaunchedEffect(id) {
//            viewModel.getWorkspaceServices(id)
//        }

        Column(
            modifier = Modifier.background(AppColors.White).fillMaxSize()
                .padding(start = 14.dp, end = 14.dp, top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomTopBar(title = stringResource(Res.string.our_services), onBackClick = {
                navigator.pop()
            })
            Spacer(modifier = Modifier.size(24.dp))

            // Search bar and profile icon
            Row(
                modifier = Modifier.fillMaxWidth().height(56.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    placeholder = stringResource(Res.string.search_on_services),
                    value = searchInput,
                    onValueChange = { searchInput = it },
                    modifier = Modifier.height(58.dp).padding(horizontal = 10.dp),
                    hasTrailing = false
                )

            }

            Spacer(modifier = Modifier.size(14.dp))

            ServiceItem(list = servicesList) {

            }

        }

        if (showDialog) {
            DescriptionDialog(
                onDismiss = { showDialog = false },
                onItemClick = {
                    showDialog = false

                }
            )
        }


    }
}