package ghayatech.ihubs.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import com.seiko.imageloader.rememberAsyncImagePainter
import ihubs.shared.generated.resources.Res
import ihubs.shared.generated.resources.resource_default
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.painterResource

@Composable
fun NetworkImage(
    url: String?, modifier: Modifier = Modifier,
    placeholder: Painter = painterResource(Res.drawable.resource_default),
) {
    val painter = if (url != null) {
        rememberAsyncImagePainter(url)
    } else placeholder

    Image(
        painter = painter,
        contentDescription = "image",
//        modifier = Modifier
//            .clip(RoundedCornerShape(12.dp)),
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}
