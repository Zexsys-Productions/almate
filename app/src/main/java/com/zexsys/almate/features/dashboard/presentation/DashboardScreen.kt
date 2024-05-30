package com.zexsys.almate.features.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zexsys.almate.features.dashboard.domain.Grade
import com.zexsys.almate.features.dashboard.domain.GradeInfoResponse
import com.zexsys.almate.ui.ErrorScreen
import com.zexsys.almate.ui.LoadingScreen
import com.zexsys.almate.ui.theme.AlmateTheme

@Composable
fun DashboardScreen(
    dashboardViewModel: DashboardViewModel = viewModel(factory = DashboardViewModel.Factory),
    modifier: Modifier = Modifier
) {

    val dashboardUiState = dashboardViewModel.dashboardUiState

    when (dashboardUiState) {
        is DashboardUiState.Loading -> LoadingScreen(loadingText = "Fetching your latest grades...")
        is DashboardUiState.Success -> DashboardResultScreen(
            gradeInfoResponse = dashboardUiState.gradeInfoResponse,
            modifier = Modifier.safeDrawingPadding()
        )
        is DashboardUiState.Error -> ErrorScreen(
            errorText = "Failed to fetch your latest grades.",
            onRetry = { dashboardViewModel.getGradeInfo() }
        )
    }

}

@Composable
fun DashboardResultScreen(
    gradeInfoResponse: GradeInfoResponse,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {

        Column {

            Text(
                text = "GPA Analytics",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            GpaCard(
                gpa = gradeInfoResponse.gpa
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Column {

            Text(
                text = "Grades",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                gradeInfoResponse.grades.forEach { grade ->
                    SubjectCard(grade)
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
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
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
                Text("<graph here>")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Current: ${gpa} GPA",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 36.sp
            )
        }
    }
}

@Composable
fun SubjectCard(
    subject: Grade,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        ),
        modifier = Modifier
            .fillMaxWidth()
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
                    Text(text = "avg.", fontSize = 12.sp, fontWeight = FontWeight.Light)
                    Spacer(modifier = Modifier.width(4.dp))
                    Row {
                        Text(
                            text = subject.gradeAsPercentage,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "%",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "OVR", fontSize = 12.sp, fontWeight = FontWeight.Light)
                }
                Column {
                    Text(
                        text = subject.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = subject.teacher,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
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
                        .size(48.dp)
                        .border(
                            width = 2.dp,
                            color = Color.LightGray,
                            shape = CircleShape
                        )
                        .background(
                            color = Color.White,
                            shape = CircleShape
                        )
                ) {
                    Text(
                        text = subject.gradeAsLetter,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Weight: ${subject.weight}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
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
