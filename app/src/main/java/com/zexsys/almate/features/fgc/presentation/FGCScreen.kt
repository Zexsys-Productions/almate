package com.zexsys.almate.features.fgc.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zexsys.almate.R
import com.zexsys.almate.features.dashboard.domain.Class
import com.zexsys.almate.features.dashboard.domain.Grades
import com.zexsys.almate.features.dashboard.presentation.DashboardUiState
import com.zexsys.almate.features.dashboard.presentation.DashboardViewModel
import com.zexsys.almate.ui.ErrorScreen
import com.zexsys.almate.ui.LoadingScreen

@Composable
fun FGCScreen(
    dashboardViewModel: DashboardViewModel = viewModel(factory = DashboardViewModel.Factory),
    fgcViewModel: FGCViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    val dashboardUiState = dashboardViewModel.dashboardUiState

    when (dashboardUiState) {
        is DashboardUiState.Loading -> LoadingScreen(loadingText = "The math isn't mathing...")
        is DashboardUiState.Success -> {
            FGCResultScreen(
                grades = dashboardUiState.grades,
                fgcViewModel = fgcViewModel
            )
        }
        is DashboardUiState.Error -> ErrorScreen(
            errorText = "Failed to fetch your latest grades.",
            onRetry = { dashboardViewModel.getDashboardInfo() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FGCResultScreen(
    grades: Grades,
    fgcViewModel: FGCViewModel,
    modifier: Modifier = Modifier
) {

    val grades: List<Class> = grades.classes
    var isExpanded by remember { mutableStateOf(false) }
    var selectedSubject by remember { mutableStateOf(grades[0]) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .imePadding()
    ) {

        if (fgcViewModel.targetPercentage != "") {

            Text(
                text = "You need a",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.alpha(0.5f)
            )
            Text(
                text = "${fgcViewModel.calculateNeeded(current = selectedSubject.gradeAsPercentage.toString(), goal = fgcViewModel.targetPercentage)}%",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "to achieve your target.",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(0.5f)
            )

        } else {

            Text(
                text = "Please enter a target grade.",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(0.5f)
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column() {
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
                        value = selectedSubject.name,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor(),
                    )

                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false }
                    ) {
                        grades.forEachIndexed { _, grade ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = grade.name
                                    )
                                },
                                onClick = {
                                    selectedSubject = grade
                                    isExpanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }

                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.rounded_percent_24), contentDescription = null) },
                placeholder = {
                    Text(
                        text = "i.e. 100",
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.alpha(0.5f)
                    )
                },
                value = fgcViewModel.targetPercentage,
                onValueChange = { fgcViewModel.targetPercentage = it },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.NumberPassword
                ),
            )

        }

    }

}
