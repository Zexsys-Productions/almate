package com.zexsys.almate

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.zexsys.almate.ui.theme.AlmateTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zexsys.almate.features.auth.presentation.login.LoginScreen
import com.zexsys.almate.features.dashboard.presentation.DashboardScreen
import com.zexsys.almate.features.fgc.presentation.FGCScreen
import com.zexsys.almate.features.profile.presentation.ProfileScreen
import com.zexsys.almate.ui.LoadingScreen
import com.zexsys.almate.ui.WorkInProgressScreen

@Composable
fun AlmateApp(
    viewModel: AlmateViewModel = viewModel(factory = AlmateViewModel.Factory),
    modifier: Modifier = Modifier
) {
    AlmateTheme {
        Scaffold() { innerPadding ->

            when (viewModel.appUiState) {
                is AppUiState.Loading -> LoadingScreen(
                    "Mating...",
                    Modifier.padding(innerPadding)
                )
                is AppUiState.LoggedIn -> MainScreen(
                    modifier = Modifier
                        .padding(innerPadding)
                )
                is AppUiState.LoggedOut -> LoginScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }

        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {

    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.DASHBOARD) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = { Icon(painterResource(it.icon), null) },
                    label = { Text(stringResource(it.label)) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        when (currentDestination) {
            AppDestinations.DASHBOARD -> { DashboardScreen() }
            AppDestinations.TOP -> { WorkInProgressScreen() }
            AppDestinations.CALCULATOR -> { FGCScreen() }
            AppDestinations.PROFILE -> { ProfileScreen() }
        }
    }

}

enum class AppDestinations(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    @StringRes val contentDescription: Int
) {
    DASHBOARD(R.string.home, R.drawable.rounded_home_24, R.string.home),
    TOP(R.string.top, R.drawable.rounded_leaderboard_24, R.string.top),
    CALCULATOR(R.string.fgc, R.drawable.rounded_calculate_24, R.string.fgc),
    PROFILE(R.string.profile, R.drawable.rounded_person_24, R.string.profile)
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
