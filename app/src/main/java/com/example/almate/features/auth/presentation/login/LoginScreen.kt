package com.example.almate.features.auth.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.almate.R
import com.example.almate.ui.theme.AlmateTheme

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory),
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .imePadding()
            .fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.almate),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        if (loginViewModel.isLoading) {
            CircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            leadingIcon = { Icon(painterResource(R.drawable.rounded_school_24), null) },
            label = { Text("School") },
            placeholder = {
                Text(
                    text = "i.e. cbcs",
                    fontStyle = FontStyle.Italic,
                    color = Color.White.copy(alpha = 0.5f)
                    )
            },
            value = loginViewModel.school,
            onValueChange = {loginViewModel.school = it},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            leadingIcon = { Icon(painterResource(R.drawable.rounded_person_24), null) },
            label = { Text("Username") },
            value = loginViewModel.username,
            onValueChange = { loginViewModel.username = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            leadingIcon = { Icon(painterResource(R.drawable.rounded_password_24), null) },
            label = { Text("Password") },
            value = loginViewModel.password,
            onValueChange = { loginViewModel.password = it },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Button(
            onClick = { loginViewModel.signIn() }
        ) {
            Text(
                text = "Log In",
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    AlmateTheme {
        LoginScreen()
    }
}