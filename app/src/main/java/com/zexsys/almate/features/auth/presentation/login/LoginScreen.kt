package com.zexsys.almate.features.auth.presentation.login

import android.app.Application
import android.content.Context
import android.text.style.UnderlineSpan
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zexsys.almate.R
import com.zexsys.almate.ui.theme.AlmateTheme
import kotlin.math.log

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory),
    modifier: Modifier = Modifier
) {

    when {
        loginViewModel.openLoadingDialog -> {
            com.zexsys.almate.ui.Dialog(text = "Logging you in...")
        }
    }

    when {
        loginViewModel.errorLoggingIn -> {
            val text = "Invalid credentials!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(LocalContext.current, text, duration)
            toast.show()
            loginViewModel.errorLoggingIn = false
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .imePadding()
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.vectoralmatelexend),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier.size(200.dp)
        )

        // school textfield
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
            onValueChange = { loginViewModel.onSchoolChange(it) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // username textfield
        OutlinedTextField(
            leadingIcon = { Icon(painterResource(R.drawable.rounded_person_24), null) },
            label = { Text("Username") },
            value = loginViewModel.username,
            onValueChange = { loginViewModel.onUsernameChange(it) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // password textfield
        OutlinedTextField(
            leadingIcon = { Icon(painterResource(R.drawable.rounded_password_24), null) },
            label = { Text("Password") },
            value = loginViewModel.password,
            onValueChange = { loginViewModel.onPasswordChange(it) },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = { if (loginViewModel.signInEnabled) { loginViewModel.signIn() } }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // log in button
        Button(
            enabled = loginViewModel.signInEnabled,
            onClick = { loginViewModel.signIn() },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(
                text = "Log In",
                fontWeight = FontWeight.Bold
            )
        }

        TextButton(
            onClick = {  }
        ) {
            Text(
                text = "Encountering issues?",
                color = Color.Gray,
                style = MaterialTheme.typography.labelLarge,
                fontStyle = FontStyle.Italic
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