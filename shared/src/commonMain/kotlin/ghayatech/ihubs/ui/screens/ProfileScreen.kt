package ghayatech.ihubs.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ihubs.shared.generated.resources.Res
import ihubs.shared.generated.resources.about_us
import ihubs.shared.generated.resources.bold
import ihubs.shared.generated.resources.bookings
import ihubs.shared.generated.resources.camera
import ihubs.shared.generated.resources.camera_add
import ihubs.shared.generated.resources.correct
import ihubs.shared.generated.resources.dark_theme
import ihubs.shared.generated.resources.done
import ihubs.shared.generated.resources.edit
import ihubs.shared.generated.resources.file
import ihubs.shared.generated.resources.fullname
import ihubs.shared.generated.resources.info
import ihubs.shared.generated.resources.logout
import ihubs.shared.generated.resources.major
import ihubs.shared.generated.resources.mobile_number
import ihubs.shared.generated.resources.notification
import ihubs.shared.generated.resources.notifications
import ihubs.shared.generated.resources.page
import ihubs.shared.generated.resources.phone
import ihubs.shared.generated.resources.privacy_policy
import ihubs.shared.generated.resources.profile
import ihubs.shared.generated.resources.theme
import ihubs.shared.generated.resources.user
import ghayatech.ihubs.networking.models.UpdateProfileRequest
import ghayatech.ihubs.networking.models.User
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.components.CTextField
import ghayatech.ihubs.ui.components.CustomTopBar
import ghayatech.ihubs.ui.components.NetworkImage
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject

class ProfileScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings: Settings = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()

        var isEditing by rememberSaveable { mutableStateOf(false) }
        var username by rememberSaveable { mutableStateOf("") }
        var changedUsername by rememberSaveable { mutableStateOf("") }
        var mobileNumber by rememberSaveable { mutableStateOf("") }
        var changedMobileNumber by rememberSaveable { mutableStateOf("") }
        var major by rememberSaveable { mutableStateOf("") }
        var changedMajor by rememberSaveable { mutableStateOf("") }
        var isDarkMode by rememberSaveable { mutableStateOf(false) }
        var img = painterResource(Res.drawable.camera)


        var snackbarMessage by rememberSaveable { mutableStateOf<String?>(null) }
        val profileState by viewModel.profileState.collectAsState()
        var user by remember { mutableStateOf<User?>(null) }

//        val updateProfileState by viewModel.updateProfileState.collectAsState()


        LaunchedEffect(Unit) {
            viewModel.getProfile()
//            if (isEditing){
//                viewModel.updateProfileState
//            }
        }
//
//        LaunchedEffect(profileState) {
//
//                        // حفظ التغييرات
//                        username = changedUsername
//                        mobileNumber = changedMobileNumber
//                        major = changedMajor
//                        // هنا يمكنك إضافة الكود لحفظ البيانات في الخادم أو قاعدة البيانات
//
//        }

        Box(Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier.fillMaxSize().background(AppColors.White)
                    .padding(top = 60.dp, start = 22.dp, end = 22.dp)
                    .verticalScroll(
                        rememberScrollState()
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CustomTopBar(
                    title = stringResource(Res.string.profile),
                    onBackClick = {navigator.pop()},
                    endContent = {
                        if (isEditing) {
                            Row(
                                modifier = Modifier.clickable {
                                    viewModel.updateProfile(
                                        UpdateProfileRequest(
                                            name = username,
                                            phone = mobileNumber,
                                            specialty = major
                                        )
                                    )
                                    isEditing = !isEditing

                                },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painterResource(Res.drawable.correct),
                                    contentDescription = "Done"
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                                CText(
                                    text = stringResource(Res.string.done),
                                    color = AppColors.Primary
                                )
                                img = painterResource(Res.drawable.camera_add)
                            }
                        } else {
                            Column(
                                modifier = Modifier.clickable { isEditing = !isEditing },
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painterResource(Res.drawable.edit),
                                    contentDescription = "Edit"
                                )
                                CText(
                                    text = stringResource(Res.string.edit),
                                    color = AppColors.Primary
                                )
                                img = painterResource(Res.drawable.camera)

                            }
                        }
                    })

                ProfileImageSection(
                    imageUrl = null,
                    isEditing = isEditing,
                    onImageClick = {
                        /* افتح المعرض أو الكاميرا */
                    }
                )

                // الحقول القابلة للتعديل
                EditableField(
                    label = stringResource(Res.string.fullname),
                    value = username,
                    isEditing = isEditing,
                    icon = painterResource(Res.drawable.user),
                    onValueChange = { username = it }
                )

                EditableField(
                    label = stringResource(Res.string.mobile_number),
                    value = mobileNumber,
                    isEditing = isEditing,
                    onValueChange = { mobileNumber = it },
                    icon = painterResource(Res.drawable.phone),

                    keyboardType = KeyboardType.Phone
                )

                EditableField(
                    label = stringResource(Res.string.major),
                    value = major,
                    isEditing = isEditing,
                    icon = painterResource(Res.drawable.major),

                    onValueChange = { major = it }

                )

//        // العناصر الثابتة
                ProfileItem(
                    label = stringResource(Res.string.bookings),
                    icon = painterResource(Res.drawable.file)
                ) {
                    navigator.push(BookingsScreen())
                }

                ProfileItem(
                    label = stringResource(Res.string.notifications),
                    icon = painterResource(Res.drawable.notification)
                ) {
                    navigator.push(NotificationsScreen())
//            onNavigate("notifications")
                }
//
//                ProfileItem(
//                    label = stringResource(Res.string.add_fingerprint),
//                    icon = painterResource(Res.drawable.small_fingerprint)
//                ) {
////            onNavigate("fingerprint")
//                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Image(
                            painterResource(Res.drawable.theme),
                            contentDescription = null,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        CText(
                            stringResource(Res.string.dark_theme),
                            modifier = Modifier.weight(1f),
                            fontFamily = Res.font.bold
                        )
                        Switch(checked = isDarkMode, onCheckedChange = { isDarkMode = it })
                    }
                    Box(
                        modifier = Modifier.height(2.dp).fillMaxWidth()
                            .background(AppColors.itemBackground)

                    )
                }

                ProfileItem(
                    label = stringResource(Res.string.about_us),
                    icon = painterResource(Res.drawable.info)
                ) {

//            onNavigate("about")
                }

                ProfileItem(
                    label = stringResource(Res.string.privacy_policy),
                    icon = painterResource(Res.drawable.page)
                ) {
                    navigator.push(PrivacyScreen())
                }

                ProfileItem(
                    label = stringResource(Res.string.logout),
                    icon = painterResource(Res.drawable.logout)
                ) {
                    settings.clear()
                    navigator.push(LoginScreen())
                }


            }


            HandleUiState(
                state = profileState,
                onMessage =
                    {
                        snackbarMessage = it
                    },
                onSuccess =
                    { data ->
                        user = data
                        username = data.name
                        mobileNumber = data.phone
                        major = data.specialty.toString()
                    }
            )

        }
    }


    @Composable
    fun EditableField(
        label: String,
        value: String,
        isEditing: Boolean,
        onValueChange: (String) -> Unit,
        icon: Painter? = null,
        keyboardType: KeyboardType = KeyboardType.Text
    ) {
        Column {

            if (isEditing) {
                CTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )

            } else {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (icon != null) Image(
                        painter = icon,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CText(value, fontFamily = Res.font.bold)
                }
            }
            Box(
                modifier = Modifier.height(2.dp).fillMaxWidth()
                    .background(AppColors.itemBackground)

            )
        }
    }

    @Composable
    fun ProfileItem(label: String, icon: Painter, onClick: () -> Unit) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() }
                    .padding(vertical = 12.dp)
            ) {
                Image(icon, contentDescription = null, modifier = Modifier.size(22.dp))
                Spacer(modifier = Modifier.width(8.dp))
                CText(label, fontFamily = Res.font.bold)

            }
            Box(
                modifier = Modifier.height(2.dp).fillMaxWidth()
                    .background(AppColors.itemBackground)

            )
        }
    }

    @Composable
    fun ProfileImageSection(
        imageUrl: String?,
        isEditing: Boolean,
        onImageClick: () -> Unit
    ) {
        var img = painterResource(Res.drawable.camera)

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .clickable { onImageClick() },
            contentAlignment = Alignment.Center
        ) {
            if (imageUrl != null) {
                // صورة المستخدم
//                val painterResource = asyncPainterResource(imageUrl)
                NetworkImage(
                    imageUrl, modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
//                NetworkImage(
//                    resource = painterResource,
//                    contentDescription = "صورة المستخدم",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .clip(CircleShape)
//                )
            } else {
                profileImage()
            }

            if (isEditing) {
                img = painterResource(Res.drawable.camera_add)
                profileImage()
            }
        }
    }

    @Composable
    fun profileImage() {
        var img = painterResource(Res.drawable.camera)

        Image(
            painter = img,
            contentDescription = "add photo",
            modifier = Modifier.size(100.dp)
                .border(width = 3.dp, color = AppColors.Primary, shape = CircleShape)
                .background(AppColors.coolBackground, shape = CircleShape)
                .border(width = 8.dp, color = AppColors.White, shape = CircleShape)
                .padding(30.dp),
        )
    }

}
