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
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ihubs.shared.generated.resources.back
import ihubs.shared.generated.resources.bold
import ihubs.shared.generated.resources.check_now
import ihubs.shared.generated.resources.missing_fields
import ihubs.shared.generated.resources.receive_code
import ihubs.shared.generated.resources.resend_code
import ihubs.shared.generated.resources.s
import ihubs.shared.generated.resources.verification
import ihubs.shared.generated.resources.verification_code
import ihubs.shared.generated.resources.verification_message
import kotlinx.coroutines.delay
import ghayatech.ihubs.networking.models.VerifyPhoneRequest
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.components.CButton
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.components.CustomTopBar
import ghayatech.ihubs.ui.components.OtpCodeInput
//import ghayatech.ihubs.components.OtpInputField
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.utils.Constants
import ghayatech.ihubs.utils.saveUser
import ihubs.shared.generated.resources.Res
import ihubs.shared.generated.resources.bold
import ihubs.shared.generated.resources.start_experience
import ihubs.shared.generated.resources.verification_success
import ihubs.shared.generated.resources.verification_success_message
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject


class ResetPasswordScreen(
    var code: String? = null,
    var phoneNumber: String? = null
) : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val settings: Settings = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()

        val openBottomSheet = remember { mutableStateOf(false) }
        BottomSheetScreen(
            openBottomSheet,
            title = stringResource(Res.string.verification_success),
            description = stringResource(Res.string.verification_success_message),
            buttonText = stringResource(Res.string.start_experience),
        ) {

        }


        var number by remember { mutableStateOf(0) }
        val imgBack = painterResource(Res.drawable.back)
        var totalTimeMillis = 60000L
        var remainingTimeMillis by remember { mutableStateOf(totalTimeMillis) }

        var snackbarMessage by remember { mutableStateOf<String?>(null) }
//
//        val snackbarHostState = remember { SnackbarHostState() }
//        val coroutineScope = rememberCoroutineScope()
        val verifyPhoneState by viewModel.verifyPhoneState.collectAsState()
        val keyboardController = LocalSoftwareKeyboardController.current

        LaunchedEffect(remainingTimeMillis) {
            if (remainingTimeMillis > 0L) {
                delay(1000)
                remainingTimeMillis -= 1000
            } else {
                // TODO ON FINISH TIME VERIFICATION CODE

            }
        }
        val totalSeconds = remainingTimeMillis / 1000


        HandleUiState(
            state = verifyPhoneState,
            onMessage = { snackbarMessage = it },
            onSuccess = { data ->
                val token = data.token
                settings.putString(Constants.TOKEN, token)
                saveUser(settings, user = data.user)

                navigator.push(HubsScreen())
            }
        )


        Box(
            Modifier.fillMaxSize()
        ) {
            val errorMessage = stringResource(Res.string.missing_fields)

            snackbarMessage = code
            print("Code is: $code")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.White)
                    .padding(top = 60.dp, start = 22.dp, end = 22.dp)

                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CustomTopBar(title = stringResource(Res.string.verification_code), onBackClick = {
                    navigator.pop()
                })

                Spacer(modifier = Modifier.size(22.dp))

                Image(painterResource(Res.drawable.verification), "Verification Code")

                CText(
                    stringResource(Res.string.verification_message),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(10.dp)
                )


//        Column {
                Spacer(modifier = Modifier.size(30.dp))

                OtpCodeInput(4, onCodeComplete = {
                    if (it == code && !phoneNumber.isNullOrEmpty()) {
                        viewModel.verifyPhone(VerifyPhoneRequest(phoneNumber!!, it))
                        keyboardController?.hide()
                    } else {
                        snackbarMessage = errorMessage
                    }
                })
                Spacer(modifier = Modifier.size(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    CText(
                        stringResource(Res.string.receive_code),
                        fontSize = 16.sp,
                        fontFamily = Res.font.bold,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    CText(
                        stringResource(Res.string.resend_code),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextDecoration.Underline
                    )
                    CText(
                        "$totalSeconds ${stringResource(Res.string.s)}",
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        color = AppColors.Error,
                        style = TextDecoration.Underline
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))

                CButton(text = stringResource(Res.string.check_now), onClick = {
                    openBottomSheet.value = true
                })

            }
        }

    }
}