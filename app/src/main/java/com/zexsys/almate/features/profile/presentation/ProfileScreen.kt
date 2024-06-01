package com.zexsys.almate.features.profile.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zexsys.almate.R
import com.zexsys.almate.features.profile.domain.StudentInfoResponse
import com.zexsys.almate.ui.ErrorScreen
import com.zexsys.almate.ui.LoadingScreen
import com.zexsys.almate.ui.theme.AlmateTheme

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory),
    modifier: Modifier = Modifier
) {

    val profileUiState = profileViewModel.profileUiState

    when (profileUiState) {
        is ProfileUiState.Loading -> { LoadingScreen(loadingText = "Getting your info...") }
        is ProfileUiState.Success -> {
            ProfileResultScreen(
                profileViewModel = profileViewModel,
                studentInfoResponse = profileUiState.studentInfoResponse
            )
        }
        is ProfileUiState.Error -> { ErrorScreen(
            errorText = "Failed to fetch your data!",
            onRetry = { profileViewModel.getStudentInfo() }
        ) }
    }

}

@Composable
fun ProfileResultScreen(
    profileViewModel: ProfileViewModel,
    studentInfoResponse: StudentInfoResponse,
    modifier: Modifier = Modifier
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Welcome back,",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White.copy(alpha = 0.5f)
        )
        Text(
            text = "${studentInfoResponse.name}!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)

        )

        Spacer(modifier = Modifier.height(48.dp))

        InfoBox(studentInfoResponse = studentInfoResponse)

        Button(
            onClick = { profileViewModel.logOut() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text(
                text = "Log Out",
                color = MaterialTheme.colorScheme.onError,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Composable
fun InfoBox(
    studentInfoResponse: StudentInfoResponse
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp).verticalScroll(rememberScrollState())
    ) {

        InfoSegment(
            icon = R.drawable.rounded_alternate_email_24,
            key = "Email",
            value = studentInfoResponse.email
        )

        InfoSegment(
            icon = R.drawable.rounded_location_on_24,
            key = "Home",
            value = studentInfoResponse.address
        )

        InfoSegment(
            icon = R.drawable.rounded_car_tag_24,
            key = "Family Number",
            value = studentInfoResponse.family
        )

        InfoSegment(
            icon = R.drawable.rounded_lock_24,
            key = "Locker Number",
            value = studentInfoResponse.locker
        )

    }
}

@Composable
fun InfoSegment(
    @DrawableRes icon: Int,
    key: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = key,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.5f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview(
    modifier: Modifier = Modifier
) {
    AlmateTheme {
        InfoSegment(
            icon = R.drawable.rounded_alternate_email_24,
            key = "Email",
            value = "steven.2025@student.cahayabangsa.org"
        )
    }
}
