package com.example.almate.features.fgc.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.almate.features.dashboard.presentation.DashboardUiState
import com.example.almate.features.dashboard.presentation.DashboardViewModel
import com.example.almate.ui.ErrorScreen
import com.example.almate.ui.LoadingScreen
import com.example.almate.ui.theme.AlmateTheme

@Composable
fun FinalGradeCalculatorScreen(
    dashboardViewModel: DashboardViewModel = viewModel(factory = DashboardViewModel.Factory),
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {

        val dashboardUiState = dashboardViewModel.dashboardUiState

        when (dashboardUiState) {
            is DashboardUiState.Loading -> LoadingScreen(loadingText = "Fetching your latest grades...")
            is DashboardUiState.Success -> { Text(dashboardUiState.gradeInfoResponse.gpa) }
            is DashboardUiState.Error -> ErrorScreen(
                errorText = "Failed to fetch your latest grades.",
                onRetry = { dashboardViewModel.getGradeInfo() }
            )
        }

    }
}

@Preview
@Composable
fun FinalGradeCalculatorScreenPreview() {
    AlmateTheme {
        FinalGradeCalculatorScreen()
    }
}