package ghayatech.ihubs.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ihubs.shared.generated.resources.Res
import ihubs.shared.generated.resources.chat
import ghayatech.ihubs.ui.components.ChatBubble
import ghayatech.ihubs.ui.components.ChatInput
import ghayatech.ihubs.ui.components.CustomTopBar
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject


class ChatScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings: Settings = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()

        var messages by remember { mutableStateOf(listOf<Message>()) }

//        Scaffold(
//            topBar = {
//                // Top Bar سيكون ثابتًا في الأعلى
//                CustomTopBar(
//                    modifier = Modifier
//                        .background(AppColors.itemBackground)
//                        .fillMaxWidth()
//                        .padding(top = 60.dp, bottom = 26.dp, start = 22.dp, end = 22.dp),
//                    title = stringResource(Res.string.chat),
//                    onBackClick = { navigator.pop() }
//                )
//            },
////            bottomBar = {
////                Column(
////                    modifier = Modifier
////                        .fillMaxWidth()
//////                        .imePadding()
//////                        .windowInsetsPadding(WindowInsets.ime), // هذا سيضمن أن الـ input field يتحرك مع لوحة المفاتيح
////                    ,horizontalAlignment = Alignment.CenterHorizontally
////                ) {
////                    ChatInput(onSend = {
////                        messages = messages + Message(it, isUser = true) + Message(
////                            "تم استلام الرسالة",
////                            isUser = false
////                        )
////                    })
////                }
////            }
//        ) { paddingValues ->
//            // المحتوى الرئيسي (LazyColumn)
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .imePadding()
//                    .padding(paddingValues) // هذا يطبق الحشوة من Scaffold (بما في ذلك حشوة لوحة المفاتيح)
//                    .background(AppColors.White),
//                horizontalAlignment = Alignment.CenterHorizontally,
//            ) {
//                LazyColumn(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 8.dp)
////                        .imePadding()
//                        .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
//                    reverseLayout = true,
//                ) {
//                    items(messages.reversed()) { message ->
//                        ChatBubble(message)
//                        Spacer(modifier = Modifier.height(4.dp))
//                    }
//                }
//
//                ChatInput(onSend = {
//                    messages = messages + Message(it, isUser = true) + Message(
//                        "تم استلام الرسالة",
//                        isUser = false
//                    )
//                })
//            }
//        }
//    }

        Box(Modifier.fillMaxSize()){
            Column {

                CustomTopBar(
                    modifier = Modifier.background(AppColors.itemBackground)
                        .fillMaxWidth()
                        .padding(top = 60.dp, bottom = 26.dp, start = 22.dp, end = 22.dp),
                    title = stringResource(Res.string.chat),

                    onBackClick = { navigator.pop() }
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding() // this is all you need to handle keyboard
                        .background(AppColors.White),

                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
                        reverseLayout = true,
                    ) {
                        items(messages.reversed()) { message ->
                            ChatBubble(message)
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }

                    ChatInput(onSend = {
                        messages = messages + Message(it, isUser = true) + Message(
                            "تم استلام الرسالة",
                            isUser = false
                        )
                    })
                }

            }
        }
        }


//        Box(Modifier.fillMaxSize()) {
//
//            Column {
//
//                CustomTopBar(
//                    modifier = Modifier.background(AppColors.itemBackground)
//                        .fillMaxWidth()
//                        .wrapContentHeight()
//                        .padding(top = 60.dp, bottom = 26.dp, start = 22.dp, end = 22.dp),
//                    title = stringResource(Res.string.chat),
//
//                    onBackClick = { navigator.pop() }
//                )
//
//                Column(
//                    modifier = Modifier
//                        .background(AppColors.White),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//
//                    LazyColumn(
//                        modifier = Modifier
//                            .weight(1f)
////                        .imePadding()
//                            .padding(horizontal = 8.dp)
//                            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
//                        reverseLayout = true,
//                    ) {
//                        items(messages.reversed()) { message ->
//                            ChatBubble(message)
//                            Spacer(modifier = Modifier.height(4.dp))
//                        }
//                    }
//
//                    ChatInput(onSend = {
//                        messages = messages + Message(it, isUser = true) + Message(
//                            "تم استلام الرسالة",
//                            isUser = false
//                        )
//                    })
//                }
//
//            }


//        }

//    }
}

data class Message(val text: String, val isUser: Boolean)

