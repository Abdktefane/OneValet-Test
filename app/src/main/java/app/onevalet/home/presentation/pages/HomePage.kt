package app.onevalet.home.presentation.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.onevalet.R
import app.onevalet.core.ui.composables.OneValetTopBar
import app.onevalet.home.presentation.viewmodel.HomeViewModel
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield


@Composable
fun HomePage(
    toggleContainer: () -> Unit,
    navigateToDevices: () -> Unit,
) {
    HomePage(
        viewModel = hiltViewModel(),
        toggleContainer = toggleContainer,
        navigateToDevices = navigateToDevices,
    )
}


@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun HomePage(
    viewModel: HomeViewModel,
    toggleContainer: () -> Unit,
    navigateToDevices: () -> Unit,
) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(1000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }

    Scaffold(
        topBar = {
            OneValetTopBar(labelResId = R.string.lbl_home, onNavigationIcon = toggleContainer)
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.0.dp),

            ) {
            HorizontalPager(count = 3, itemSpacing = 8.0.dp, state = pagerState) { page ->
                // Our page content
                Image(
                    painter = rememberImagePainter(
                        "https://picsum.photos/id/22${page}/200/100",
                        builder = {
                            crossfade(true)
                            transformations(RoundedCornersTransformation(4.0f))
                        }
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .aspectRatio(16 / 9f)
                        .fillMaxWidth()
                )

            }
            Spacer(modifier = Modifier.height(24.0.dp))
            Button(onClick = navigateToDevices) {
                Text(stringResource(id = R.string.lbl_my_devices))

            }

        }

    }

}