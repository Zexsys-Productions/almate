package com.zexsys.almate.features.top.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.zexsys.almate.features.top.domain.OverallInfo
import com.zexsys.almate.ui.ErrorScreen
import com.zexsys.almate.ui.LoadingScreen
import com.zexsys.almate.ui.theme.AlmateTheme

@Composable
fun TopScreen(
    topViewModel: TopViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    when (val topUiState = topViewModel.topUiState) {
        is TopUiState.Loading -> { LoadingScreen(loadingText = "Fetching the academically gifted...") }
        is TopUiState.Success -> { TopScreenResult(topViewModel) }
        is TopUiState.Error -> { ErrorScreen(errorText = "Error fetching the leaderboard.", onRetry = { /*TODO*/ }) }
    }

}

@Composable
fun TopScreenResult(
    topViewModel: TopViewModel,
    modifier: Modifier = Modifier
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {

        SingleChoiceSegmentedButtonRow {
            topViewModel.options.forEachIndexed { index, option ->
                SegmentedButton(
                    selected = topViewModel.selectedIndex.value == index,
                    onClick = { topViewModel.selectedIndex.value = index },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = topViewModel.options.size)
                ) {

                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun TopScreenResultPreview() {
    AlmateTheme {
        TopScreenResult(topViewModel = viewModel())
    }
}