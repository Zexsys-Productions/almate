package com.zexsys.almate.features.top.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.protobuf.Empty
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.zexsys.almate.features.top.domain.OverallInfo
import com.zexsys.almate.ui.ErrorScreen
import com.zexsys.almate.ui.LoadingScreen
import com.zexsys.almate.ui.theme.AlmateTheme
import com.zexsys.almate.ui.theme.backgroundDark

@Composable
fun TopScreen(
    topViewModel: TopViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    if (topViewModel.listOfUsers == null) {
        LoadingScreen(loadingText = "Fetching the academically gifted...")
    } else {
        TopScreenResult(
            topViewModel = topViewModel,
            users = topViewModel.listOfUsers
        )
    }

}

@Composable
fun TopScreenResult(
    topViewModel: TopViewModel,
    users: List<OverallInfo>?,
    modifier: Modifier = Modifier
) {

    Scaffold {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = modifier
                .padding(it)
                .padding(16.dp, 8.dp)
                .fillMaxSize()
        ) {

            Text(
                text = "Leaderboard",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                topViewModel.options.forEachIndexed { index, _ ->
                    SegmentedButton(
                        selected = topViewModel.selectedIndex.intValue == index,
                        onClick = { topViewModel.selectedIndex.intValue = index },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = topViewModel.options.size
                        )
                    ) {
                        Text(topViewModel.options[index])
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (topViewModel.selectedIndex.intValue) {
                0 -> { LeaderboardSection(users = users) }
                else -> { Text("Coming soon!") }
            }

        }
    }

}

@Composable
fun LeaderboardSection(
    users: List<OverallInfo>?,
    modifier: Modifier = Modifier
) {
    Column {
        users?.forEachIndexed { index, user ->

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = (index + 1).toString())
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    ) {

                        Text(
                            text = user.username,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = user.rankedRating.toString(),
                            style = MaterialTheme.typography.labelLarge
                        )

                    }
                }
            }

        }
    }
}

@Composable
fun Podium(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Spacer(modifier = Modifier.height(288.dp))
            PodiumUser(
                rank = "2",
                rankedRating = "938",
                username = "clayton.2025",
                modifier = Modifier.weight(1f)
            )
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Spacer(modifier = Modifier.height(240.dp))
            PodiumUser(
                rank = "1",
                rankedRating = "938",
                username = "ethan.2025",
                modifier = Modifier.weight(1f)
            )
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Spacer(modifier = Modifier.height(336.dp))
            PodiumUser(
                rank = "3",
                rankedRating = "938",
                username = "steven.2025",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun PodiumUser(
    rank: String,
    rankedRating: String,
    username: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp, 12.dp))
            .background(Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("hi")
        }
    }
}

@Preview
@Composable
fun PodiumPreview() {
    Podium()
}