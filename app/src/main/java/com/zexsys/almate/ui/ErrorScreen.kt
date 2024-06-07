package com.zexsys.almate.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zexsys.almate.R
import com.zexsys.almate.ui.theme.AlmateTheme

@Composable
fun ErrorScreen(
    onRetry: () -> Unit,
    additionalContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {

            Text(
                text = "Something went wrong",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onRetry
            ) {
                Text(
                    text = "Try again",
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                content = additionalContent
            )

            Spacer(modifier = Modifier.height(48.dp))


        }

        Image(
            painter = painterResource(id = R.drawable.vectoralmatelexend),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .alpha(0.5f)
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        )

    }
}

@Preview
@Composable
fun ErrorPreview() {
    AlmateTheme(darkTheme = true) {
        ErrorScreen(onRetry = {}, {})
    }
}
