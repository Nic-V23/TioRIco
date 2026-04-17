package com.tiorico.app.ui.theme



import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.darkColorScheme

import androidx.compose.runtime.Composable

import androidx.compose.ui.graphics.Color



val Gold = Color(0xFFFFD700)

val DarkBg = Color(0xFF0D1B2A)

val CardBg = Color(0xFF1E2D3D)

val Green = Color(0xFF4CAF50)

val Red = Color(0xFFF44336)

val Blue = Color(0xFF1565C0)

val Orange = Color(0xFFF57F17)



private val DarkColors = darkColorScheme(

    primary = Blue,

    onPrimary = Color.White,

    background = DarkBg,

    surface = CardBg,

    onBackground = Color.White,

    onSurface = Color.White,

    secondary = Gold

)



@Composable

fun TioRicoTheme(content: @Composable () -> Unit) {

    MaterialTheme(colorScheme = DarkColors, content = content)

}