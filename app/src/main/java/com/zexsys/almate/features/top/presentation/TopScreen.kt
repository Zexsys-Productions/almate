package com.zexsys.almate.features.top.presentation

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.protobuf.Empty
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.zexsys.almate.R
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
                text = "Top",
                style = MaterialTheme.typography.titleLarge,
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
fun TopCard() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "4"
                )

                Spacer(modifier = Modifier.width(16.dp))

                Image(
                    painter = painterResource(id = R.drawable._a40e577c1a5af89edfd5c89d0279f11),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "skyzeki",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "steven.2025",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.alpha(0.5f)
                    )
                }

            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "998 RR",
                    style = MaterialTheme.typography.titleLarge
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "7/11",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Icon(
                            painter = painterResource(R.drawable.filled_kid_star_24),
                            contentDescription = null,
                            modifier = Modifier.size(12.dp)
                            
                        )
                    }
                    VerticalDivider(modifier = Modifier.height(8.dp))
                    Text(
                        text = "4.0 GPA",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

        }
    }
}

@Preview()
@Composable
fun PodiumPreview() {
    TopCard()
}