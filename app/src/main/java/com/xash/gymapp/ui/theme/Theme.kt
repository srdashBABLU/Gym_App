package com.xash.gymapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Purple,
    onPrimary = Color.White,
    primaryContainer = LightPurple,
    onPrimaryContainer = DarkPurple,
    secondary = Teal,
    onSecondary = Color.White,
    secondaryContainer = LightTeal,
    onSecondaryContainer = DarkTeal,
    tertiary = Orange,
    onTertiary = Color.White,
    tertiaryContainer = LightOrange,
    onTertiaryContainer = DarkOrange,
    background = Color.White,
    onBackground = DarkGray,
    surface = Color(0xFFF8F8F8),
    onSurface = DarkGray
)

private val DarkColorScheme = darkColorScheme(
    primary = LightPurple,
    onPrimary = DarkPurple,
    primaryContainer = Purple,
    onPrimaryContainer = Color.White,
    secondary = LightTeal,
    onSecondary = DarkTeal,
    secondaryContainer = Teal,
    onSecondaryContainer = Color.White,
    tertiary = LightOrange,
    onTertiary = DarkOrange,
    tertiaryContainer = Orange,
    onTertiaryContainer = Color.White,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White
)


@Composable
fun GymAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}