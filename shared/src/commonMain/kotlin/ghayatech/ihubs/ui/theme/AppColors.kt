package ghayatech.ihubs.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color



object AppColors {

    val Primary: Color
        @Composable
        get() = if (isSystemInDarkTheme()) Color(0xFF2D6C5B) else Color(0xFF2D6C5B)

    val Secondary: Color
        @Composable
        get() = if (isSystemInDarkTheme()) Color(0xFF00CA93) else Color(0xFF316C5A)


    val Background: Color
        @Composable
        get() = if (isSystemInDarkTheme()) Color(0xFF1A1E23) else Color(0xFFF0F0F0)


    val White: Color
        @Composable
        get() = if (isSystemInDarkTheme()) Color(0xFF2A2B32) else Color( 0xFFFFFFFF)


    val Black: Color
        @Composable
        get() = if (isSystemInDarkTheme()) Color(0xFFFFFFFF) else Color(0xFF000000)


    val Black50 = Color(0xBB000000)


    val Error: Color
        @Composable
        get() = Color(0xFFFA5D3F)


    val TextSecondary: Color
        @Composable
        get() = if (isSystemInDarkTheme()) Color(0xFFFFFFFF) else Color(0xFF757575)


    val itemBackground: Color
        @Composable
        get() = if (isSystemInDarkTheme()) Color(0xFF1A1E23) else Color(0xFFD9D9D9)


    val coolBackground: Color
        @Composable
        get() = if (isSystemInDarkTheme()) Color(0xFF1A1E23) else Color(0xFFEDEDED)


    val transparent: Color
        @Composable
        get() = Color(0x00000000)


    val AdminMessage: Color
        @Composable
        get() = if (isSystemInDarkTheme()) Color(0xFFFFFFFF) else Color(0xFFCCCCCC)


    val chatText: Color
        @Composable
        get() = if (isSystemInDarkTheme()) Color(0xFFD9D9D9) else Color(0xFF585858)


    val chatBackground: Color
        @Composable
        get() = if (isSystemInDarkTheme()) Color(0xFF263238) else Color(0xFFD9D9D9)



    val purple: Color
        @Composable
        get() = Color(0xFF00A8CE)

    val hours = Color(0xFFC9792F)
    val daily = Color(0xFFB5B5B5)
    val weekly = Color(0xFFFFCB5B)
    val monthly = Color(0xFF00A8CE)
}

//object AppColors {
//    val Primary = {Color(0xFF2D6C5B)}
//    val Secondary = Color(0xFF316C5A)
//    val Background = Color(0xFFF0F0F0)
//    val White = Color(0xFFFFFFFF)
//    val Black = Color(0xFF000000)
//    val Error = Color(0xFFFA5D3F)
//    val TextSecondary = Color(0xFF757575)
//    val itemBackground = Color(0xFFD9D9D9)
//    val coolBackground = Color(0xFFEDEDED)
//    val transparent = Color(0x00000000)
//    val AdminMessage = Color(0xFFCCCCCC)
//    val chatText = Color(0xFF585858)
//    val purple = Color(0xFF00A8CE)
//}

//
//object LightColors {
//    val Primary = Color(0xFF2D6C5B)
//    val Secondary = Color(0xFF316C5A)
//    val Background = Color(0xFFF0F0F0)
//    val White = Color(0xFFFFFFFF)
//    val Black = Color(0xFF000000)
//    val Error = Color(0xFFFA5D3F)
//    val TextSecondary = Color(0xFF757575)
//    val itemBackground = Color(0xFFD9D9D9)
//    val coolBackground = Color(0xFFEDEDED)
//    val transparent = Color(0x00000000)
//    val AdminMessage = Color(0xFFCCCCCC)
//    val chatText = Color(0xFF585858)
//    val purple = Color(0xFF00A8CE)
//}
//
//object DarkColors {
//    val Primary = Color(0xFF2D6C5B)
//    val Secondary = Color(0xFF316C5A)
//    val Background = Color(0xFFF0F0F0)
//    val White = Color(0xFFFFFFFF)
//    val Black = Color(0xFF000000)
//    val Error = Color(0xFFFA5D3F)
//    val TextSecondary = Color(0xFF757575)
//    val itemBackground = Color(0xFFD9D9D9)
//    val coolBackground = Color(0xFFEDEDED)
//    val transparent = Color(0x00000000)
//    val AdminMessage = Color(0xFFCCCCCC)
//    val chatText = Color(0xFF585858)
//    val purple = Color(0xFF00A8CE)
//}
