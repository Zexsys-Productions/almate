package com.zexsys.almate.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle

import com.zexsys.almate.R

val proximaNovaFamily = FontFamily(
    Font(R.font.proximanova_thin, FontWeight.Thin),
    Font(R.font.proximanova_light, FontWeight.Light),
    Font(R.font.proximanova_regular, FontWeight.Normal),
    Font(R.font.proximanova_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.proximanova_semibold, FontWeight.SemiBold),
    Font(R.font.proximanova_bold, FontWeight.Bold),
    Font(R.font.proximanova_extrabold, FontWeight.ExtraBold),
    Font(R.font.proximanova_black, FontWeight.Black),
)

// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = proximaNovaFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = proximaNovaFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = proximaNovaFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = proximaNovaFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = proximaNovaFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = proximaNovaFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = proximaNovaFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = proximaNovaFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = proximaNovaFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = proximaNovaFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = proximaNovaFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = proximaNovaFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = proximaNovaFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = proximaNovaFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = proximaNovaFamily),
)

