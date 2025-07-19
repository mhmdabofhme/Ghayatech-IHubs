package ghayatech.ihubs.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import ghayatech.ihubs.ui.components.CButton
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.components.CTextField
import ghayatech.ihubs.ui.theme.AppColors

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.painterResource
import ihubs.shared.generated.resources.Res
import ihubs.shared.generated.resources.already_have_account
import ihubs.shared.generated.resources.back
import ihubs.shared.generated.resources.by_creating_a_free_account
import ihubs.shared.generated.resources.confirm_password
import ihubs.shared.generated.resources.create_new_account
import ihubs.shared.generated.resources.fullname
import ihubs.shared.generated.resources.get_started
import ihubs.shared.generated.resources.major
import ihubs.shared.generated.resources.missing_fields
import ihubs.shared.generated.resources.mobile_number
import ihubs.shared.generated.resources.password
import ihubs.shared.generated.resources.welcome
import ihubs.shared.generated.resources.white_welcome
import ghayatech.ihubs.networking.models.RegisterRequest
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.components.CustomSnackbar
import ghayatech.ihubs.utils.Constants
import ghayatech.ihubs.utils.isValid
import ghayatech.ihubs.utils.saveUser
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject
import com.russhwolf.settings.Settings
//import ghayatech.ihubs.app_navigation.Screen
//import ghayatech.ihubs.app_navigation.Config
//import ghayatech.ihubs.app_navigation.RootComponent

class SignupScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings: Settings = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()

        var fullName by remember { mutableStateOf("") }
        var phoneNumber by remember { mutableStateOf("") }
        var major by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        val imgBack = painterResource(Res.drawable.back)

        val imgWelcome =
            if (isSystemInDarkTheme()) painterResource(Res.drawable.welcome)
            else painterResource(Res.drawable.white_welcome)

        var snackbarMessage by remember { mutableStateOf<String?>(null) }

//        val snackbarHostState = remember { SnackbarHostState() }
//        val coroutineScope = rememberCoroutineScope()
        val registerState by viewModel.loginState.collectAsState()
        val keyboardController = LocalSoftwareKeyboardController.current


        Box(
            Modifier.fillMaxSize()
        ) {
            Box(Modifier.fillMaxSize()) {
                val errorMessage = stringResource(Res.string.missing_fields)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppColors.White)
                        .padding(top = 60.dp, start = 22.dp, end = 22.dp)

                        .verticalScroll(rememberScrollState()),
                ) {

                    Row(modifier = Modifier.wrapContentSize().clickable {
                        // TODO ON BACK
                        navigator.pop()
                    }, verticalAlignment = Alignment.CenterVertically) {
                        Icon(imgBack, contentDescription = "Back", tint = AppColors.TextSecondary)
                        CText(
                            stringResource(Res.string.back),
                            fontSize = 14.sp,
                            color = AppColors.Black,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.wrapContentSize()
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(imgWelcome, "welcome")
                        CText(
                            stringResource(Res.string.get_started),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        )
                        CText(
                            stringResource(Res.string.by_creating_a_free_account),
                            fontSize = 14.sp
                        )
                    }

//        Column {
                    Spacer(modifier = Modifier.size(48.dp))

                    CTextField(
                        placeholder = stringResource(Res.string.fullname),
                        inputType = KeyboardType.Text,
                        value = fullName,
                        onValueChange = { fullName = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CTextField(
                        placeholder = stringResource(Res.string.mobile_number),
                        inputType = KeyboardType.Number,
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it }
                    )


                    Spacer(modifier = Modifier.height(12.dp))

                    CTextField(
                        placeholder = stringResource(Res.string.major),
                        inputType = KeyboardType.Text,
                        value = major,
                        onValueChange = { major = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CTextField(
                        placeholder = stringResource(Res.string.password),
                        inputType = KeyboardType.Password,
                        isPassword = true,
                        value = password,
                        onValueChange = { password = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CTextField(
                        placeholder = stringResource(Res.string.confirm_password),
                        inputType = KeyboardType.Password,
                        isPassword = true,
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it }
                    )

                    Spacer(modifier = Modifier.height(35.dp))
                    CButton(text = stringResource(Res.string.create_new_account), onClick = {
                        if (isValid(
                                listOf(phoneNumber, password, confirmPassword, major, fullName)
                            )
                        ) {
                            val data = RegisterRequest(
                                phone = phoneNumber,
                                password = password,
                                passwordConfirmation = confirmPassword,
                                specialty = major,
                                name = fullName
                            )
                            viewModel.register(data)
                            keyboardController?.hide()

                        } else {
                            snackbarMessage = errorMessage

                        }
                    })


                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CText(
                            text = stringResource(Res.string.already_have_account),
                            style = TextDecoration.Underline,
                            color = AppColors.Secondary,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 9.dp).clickable {
                                navigator.pop()
                            }

                        )

                    }
                }

                CustomSnackbar(
                    message = snackbarMessage,
                    onDismiss = { snackbarMessage = null },
                    modifier = Modifier.align(Alignment.TopCenter)
                )

                HandleUiState(
                    state = registerState,
                    onMessage = { snackbarMessage = it },
                    onSuccess = { data ->
                        val token = data.token
                        settings.putString(Constants.TOKEN, token)
                        saveUser(settings, user = data.user)
                        navigator.push(HubsScreen())
                    }
                )

            }
        }


    }
}
