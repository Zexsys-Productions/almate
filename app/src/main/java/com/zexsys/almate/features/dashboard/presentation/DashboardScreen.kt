package com.zexsys.almate.features.dashboard.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zexsys.almate.R
import com.zexsys.almate.features.dashboard.domain.Class
import com.zexsys.almate.features.dashboard.domain.GpaResponse
import com.zexsys.almate.ui.ErrorScreen
import com.zexsys.almate.ui.LoadingScreen
import com.zexsys.almate.ui.theme.AlmateTheme

@Composable
fun DashboardScreen(
    dashboardViewModel: DashboardViewModel = viewModel(factory = DashboardViewModel.Factory),
    modifier: Modifier = Modifier
) {

    when (val dashboardUiState = dashboardViewModel.dashboardUiState) {
        is DashboardUiState.Loading -> LoadingScreen(loadingText = "Fetching your latest grades...")
        is DashboardUiState.Success -> DashboardResultScreen(
            classes = dashboardUiState.classes,
            gpa = dashboardUiState.gpaResponse,
            dashboardViewModel = dashboardViewModel,
            modifier = Modifier.safeDrawingPadding()
        )
        is DashboardUiState.Error -> ErrorScreen(
            onRetry = { dashboardViewModel.getDashboardInfo() }
        )
    }

}

@Composable
fun DashboardResultScreen(
    dashboardViewModel: DashboardViewModel,
    gpa: GpaResponse,
    classes: List<Class>,
    modifier: Modifier = Modifier
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { dashboardViewModel.getDashboardInfo() }) {
                Icon(painter = painterResource(id = R.drawable.rounded_refresh_24),  contentDescription = null)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {

            Column {

                Text(
                    text = "GPA Analytics",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                GpaCard(
                    gpa = gpa.live
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            Column {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Grades",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(
                        onClick = { dashboardViewModel.switchSort() }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (dashboardViewModel.sortedAlphabetically) "Alphabetical" else "Performance",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Image(painter = painterResource(id = R.drawable.rounded_filter_24_filled), contentDescription = null, colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface))
                        }
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    classes.forEach { grade ->
                        SubjectCard(grade)
                    }
                }

            }

        }
    }

}

@Composable
fun GpaCard(
    gpa: String,
    modifier: Modifier = Modifier
) {

    val colorStops = when (gpa.toDouble()) {

        in 3.5..4.0 -> arrayOf(
            0.0f to Color(0xFF76b852),
            1f to Color(0xFF8DC26F),
        )

        in 3.0..3.4 -> arrayOf(
            0.0f to Color(0xFFFFE000),
            1f to Color(0xFF799F0C),
        )

        in 2.0..2.9 -> arrayOf(
            0.0f to Color(0xFFf46b45),
            1f to Color(0xFFeea849),
        )

        else -> arrayOf(
            0.0f to Color.Black,
            1f to Color.White
        )
    }

    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Text(text = "<graph>")

            }
            Spacer(modifier = Modifier.height(12.dp))

            Row {
                Text(
                    text = "Current: ",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "${gpa} GPA",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge.copy(
                        brush = Brush.horizontalGradient(colorStops = colorStops)
                    )
                )
            }

        }
    }
}

@Composable
fun SubjectCard(
    subject: Class,
    modifier: Modifier = Modifier
) {

    val uriHandler = LocalUriHandler.current

    val colorStops = when (subject.gradeAsLetter) {
        "A+" -> arrayOf(
            0.0f to Color(0xFF76b852),
            1f to Color(0xFFA5D889),
        )
        "A" -> arrayOf(
            0.0f to Color(0xFF76b852).copy(alpha = 0.8f),
            1f to Color(0xFF8DC26F).copy(alpha = 0.8f),
        )
        "A-" -> arrayOf(
            0.0f to Color(0xFF76b852).copy(alpha = 0.6f),
            1f to Color(0xFF8DC26F).copy(alpha = 0.6f),
        )
        "B+" -> arrayOf(
            0.0f to Color(0xFF799F0C).copy(alpha = 0.8f),
            1f to Color(0xFFFFE000).copy(alpha = 0.8f),
        )
        "B" -> arrayOf(
            0.0f to Color(0xFF799F0C).copy(alpha = 0.7f),
            1f to Color(0xFFFFE000).copy(alpha = 0.7f),
        )
        "B-" -> arrayOf(
            0.0f to Color(0xFF799F0C).copy(alpha = 0.6f),
            1f to Color(0xFFFFE000).copy(alpha = 0.6f),
        )
        "C+" -> arrayOf(
            0.0f to Color(0xFFf46b45),
            1f to Color(0xFFeea849),
        )
        "C" -> arrayOf(
            0.0f to Color(0xFFf46b45).copy(alpha = 0.8f),
            1f to Color(0xFFeea849).copy(alpha = 0.8f),
        )
        "D+" -> arrayOf(
            0.0f to Color(0xFFEB5757),
            1f to Color.Black,
        )
        "D" -> arrayOf(
            0.0f to Color(0xFFEB5757).copy(alpha = 0.8f),
            1f to Color.Black.copy(alpha = 0.8f),
        )
        "E" -> arrayOf(
            0.0f to Color.Black,
            1f to Color(0xFF434343),
        )
        else -> arrayOf(
            0.7f to MaterialTheme.colorScheme.surfaceContainerHigh,
            1f to Color.Black.copy(alpha = 0.15f)
        )
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.linearGradient(colorStops = colorStops))
            .fillMaxWidth()
            .clickable { uriHandler.openUri(subject.url) }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier.weight(0.7f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
//                    Text(text = "avg.", fontSize = 12.sp, fontWeight = FontWeight.Light)
//                    Spacer(modifier = Modifier.width(4.dp))
                    Row {
                        Text(
                            text = subject.gradeAsPercentage.toString(),
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "%",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(text = "OVR", fontSize = 12.sp, fontWeight = FontWeight.Light)
                }
                Column {
                    Text(
                        text = subject.name,
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = subject.teacher,
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.weight(0.3f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = Color.LightGray.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                        .background(
                            color = Color.White.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                        .size(36.dp)
                ) {
                    Text(
                        text = subject.gradeAsLetter,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Weight: ${subject.weight}",
                    fontSize = 12.sp,
                    lineHeight = 12.sp
                )
            }
        }
    }
}

@Preview()
@Composable
fun DashboardScreenPreview() {
    AlmateTheme {
        DashboardScreen()
    }
}
