package com.example.almate.features.fgc.presentation

import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.almate.R
import com.example.almate.features.dashboard.domain.GradeInfoResponse
import com.example.almate.features.dashboard.presentation.DashboardUiState
import com.example.almate.features.dashboard.presentation.DashboardViewModel
import com.example.almate.ui.ErrorScreen
import com.example.almate.ui.LoadingScreen
import com.example.almate.ui.theme.AlmateTheme

@Composable
fun FinalGradeCalculatorScreen(
    dashboardViewModel: DashboardViewModel = viewModel(factory = DashboardViewModel.Factory),
    fgcViewModel: FGCViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {

        val dashboardUiState = dashboardViewModel.dashboardUiState

        when (dashboardUiState) {
            is DashboardUiState.Loading -> LoadingScreen(loadingText = "Fetching your latest grades...")
            is DashboardUiState.Success -> { FGCResultScreen(dashboardUiState.gradeInfoResponse) }
            is DashboardUiState.Error -> ErrorScreen(
                errorText = "Failed to fetch your latest grades.",
                onRetry = { dashboardViewModel.getGradeInfo() }
            )
        }

    }
}

@Composable
fun FGCResultScreen(
    gradeInfoResponse: GradeInfoResponse,
    modifier: Modifier = Modifier
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .imePadding()
    ) {

        Text(
            text = "You need a",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White.copy(alpha = 0.5f)
        )
        Text(
            text = "${96}%",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "to achieve your target.",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White.copy(alpha = 0.5f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(

        ) {

            DropDown(gradeInfoResponse = gradeInfoResponse)

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.rounded_percent_24), contentDescription = null) },
                value = "93",
                onValueChange = { },
                modifier = Modifier.weight(0.3f)
            )

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(
    gradeInfoResponse: GradeInfoResponse
) {

    val grades = gradeInfoResponse.grades
    var isExpanded by remember { mutableStateOf(false) }
    var selectedSubject by remember { mutableStateOf(grades[0].name) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {

            OutlinedTextField(
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.rounded_school_24),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                value = selectedSubject,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .weight(0.7f)
                ,
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                grades.forEachIndexed { index, grade ->
                    DropdownMenuItem(
                        text = { Text(text = grade.name) },
                        onClick = {
                            selectedSubject = grade.name
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }

        }
    }



}

//
//@Preview(showBackground = true)
//@Composable
//fun FGCPreview() {
//    AlmateTheme(darkTheme = true) {
//        FGCResultScreen()
//    }
//}