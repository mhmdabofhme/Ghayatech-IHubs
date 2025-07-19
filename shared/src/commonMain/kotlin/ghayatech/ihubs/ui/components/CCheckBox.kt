package ghayatech.ihubs.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import ghayatech.ihubs.ui.theme.AppColors
import ihubs.shared.generated.resources.Res
import ihubs.shared.generated.resources.bold
import ihubs.shared.generated.resources.normal
import ihubs.shared.generated.resources.payment_available
import org.jetbrains.compose.resources.FontResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CCheckBox(

    text: String,
    modifier: Modifier,
    checkbox: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    textColor: Color = AppColors.Primary,
    checkedColor: Color = AppColors.Primary,
    uncheckedColor: Color = AppColors.Secondary,
    fontFamily: FontResource = Res.font.bold,
    fontSize: TextUnit = 10.sp,

    ) {

    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checkbox,
            onCheckedChange  = onCheckedChange
            ,
            colors = CheckboxDefaults.colors(
                checkedColor = checkedColor,
                uncheckedColor = uncheckedColor
            )
        )
        CText(
            text = text,
            color = textColor,
            fontFamily = fontFamily,
            fontSize = fontSize
        )
//                    CText(text = stringResource(Res.string.payment),)
    }
}