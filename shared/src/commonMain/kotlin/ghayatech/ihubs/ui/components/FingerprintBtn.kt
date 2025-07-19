package ghayatech.ihubs.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import ihubs.shared.generated.resources.Res
import ihubs.shared.generated.resources.fingerprint
import ihubs.shared.generated.resources.imgeye
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun FingerprintBtn(
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(10.dp)
    val imgFingerprint = painterResource(Res.drawable.fingerprint)

    Column(
        modifier = Modifier.wrapContentSize()
            .height(56.dp)

            .clip(shape)
            .clickable { onClick() }
            .background(AppColors.Primary)
            .padding(15.dp)
            ,
    ) {
        Image(imgFingerprint, contentDescription = "Fingerprint")
    }
}
