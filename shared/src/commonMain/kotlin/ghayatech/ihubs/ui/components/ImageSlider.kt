package ghayatech.ihubs.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ghayatech.ihubs.ui.theme.AppColors

@Composable
fun ImageSlider(
    images: List<String>,
    modifier: Modifier = Modifier,
    autoScrollInterval: Long = 3000L // 3 seconds
) {
    val pageCount = images.size

    val pagerState = rememberPagerState(pageCount = { pageCount })

    // Auto-scroll
    LaunchedEffect(Unit) {
        while (true) {
            delay(autoScrollInterval)
            val nextPage =
                if (pagerState.currentPage == pageCount - 1) 0 else pagerState.currentPage + 1

            pagerState.animateScrollToPage(
                page = nextPage,
                animationSpec = tween(
                    durationMillis = 1000, // مدة الانتقال = 1000ms (أبطأ من القيمة الافتراضية)
                    easing = FastOutSlowInEasing // يمكنك تغييره حسب رغبتك
                )
            )
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { page ->
            NetworkImage(
                images[page], modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp)
                    .clip(RoundedCornerShape(25.dp))
//                .background(AppColors.coolBackground)
//                .padding(25.dp)
            )
//            Image(
//                painter = images[page],
//                contentDescription = "Image $page",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(125.dp)
//                    .clip(RoundedCornerShape(25.dp))
//                    .background(AppColors.coolBackground)
//                    .padding(25.dp)
//            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Indicators
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .width(if (isSelected) 20.dp else 8.dp)
                        .height(8.dp)
                        .background(
                            color = if (isSelected) AppColors.Secondary else AppColors.TextSecondary.copy(
                                alpha = 0.3f
                            ),
                            shape = if (isSelected) RoundedCornerShape(8.dp) else CircleShape
                        )
                )
            }
        }
    }
}
