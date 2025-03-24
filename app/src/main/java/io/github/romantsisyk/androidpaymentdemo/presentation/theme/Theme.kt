package io.github.romantsisyk.androidpaymentdemo.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Інтерфейс теми
object LuxuryTheme {
    val spacing: Spacing
        @Composable
        get() = LocalSpacing.current

    val elevations: Elevations
        @Composable
        get() = LocalElevations.current

    val shapes: Shapes
        @Composable
        get() = LocalShapes.current

    val gradients: Gradients
        @Composable
        get() = LocalGradients.current
}

// Класи з налаштуваннями
data class Spacing(
    val extraSmall: Int,
    val small: Int,
    val medium: Int,
    val large: Int,
    val extraLarge: Int
)

data class Elevations(
    val small: Int,
    val medium: Int,
    val large: Int
)

data class Shapes(
    val small: Int,
    val medium: Int,
    val large: Int
)

data class Gradients(
    val primaryGradient: Brush,
    val secondaryGradient: Brush,
    val blueGradient: Brush,
    val purpleGradient: Brush,
    val orangeGradient: Brush
)

// Локальні провайдери
val LocalSpacing = staticCompositionLocalOf {
    Spacing(
        extraSmall = 4,
        small = 8,
        medium = 16,
        large = 24,
        extraLarge = 32
    )
}

val LocalElevations = staticCompositionLocalOf {
    Elevations(
        small = 2,
        medium = 4,
        large = 8
    )
}

val LocalShapes = staticCompositionLocalOf {
    Shapes(
        small = 8,
        medium = 16,
        large = 24
    )
}

val LocalGradients = staticCompositionLocalOf {
    Gradients(
        primaryGradient = Brush.horizontalGradient(listOf(Color(0xFF6200EA), Color(0xFF9C27B0))),
        secondaryGradient = Brush.horizontalGradient(listOf(Color(0xFF1E88E5), Color(0xFF00BCD4))),
        blueGradient = Brush.horizontalGradient(listOf(Color(0xFF1976D2), Color(0xFF64B5F6))),
        purpleGradient = Brush.horizontalGradient(listOf(Color(0xFF9C27B0), Color(0xFFBA68C8))),
        orangeGradient = Brush.horizontalGradient(listOf(Color(0xFFFF9800), Color(0xFFFFB74D)))
    )
}

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EA),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEADDFF),
    onPrimaryContainer = Color(0xFF21005E),
    secondary = Color(0xFF1E88E5),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD8E6FF),
    onSecondaryContainer = Color(0xFF001A40),
    tertiary = Color(0xFFFF9800),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFDDB5),
    onTertiaryContainer = Color(0xFF2A1800),
    background = Color(0xFFFFFBFF),
    onBackground = Color(0xFF1C1B1E),
    surface = Color(0xFFFFFBFF),
    onSurface = Color(0xFF1C1B1E),
    surfaceVariant = Color(0xFFE7E0EB),
    onSurfaceVariant = Color(0xFF49454E),
    error = Color(0xFFBA1B1B),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD4),
    onErrorContainer = Color(0xFF410001)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFD0BCFF),
    onPrimary = Color(0xFF371E73),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = Color(0xFFEADDFF),
    secondary = Color(0xFF90CAF9),
    onSecondary = Color(0xFF003258),
    secondaryContainer = Color(0xFF00497D),
    onSecondaryContainer = Color(0xFFD8E6FF),
    tertiary = Color(0xFFFFB74D),
    onTertiary = Color(0xFF462A00),
    tertiaryContainer = Color(0xFF633F00),
    onTertiaryContainer = Color(0xFFFFDDB5),
    background = Color(0xFF1C1B1E),
    onBackground = Color(0xFFE6E1E6),
    surface = Color(0xFF1C1B1E),
    onSurface = Color(0xFFE6E1E6),
    surfaceVariant = Color(0xFF49454E),
    onSurfaceVariant = Color(0xFFCAC4CF),
    error = Color(0xFFFFB4A9),
    onError = Color(0xFF680003),
    errorContainer = Color(0xFF930006),
    onErrorContainer = Color(0xFFFFDAD4)
)

@Composable
fun AndroidPaymentDemoTheme(
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
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    // Постачання провайдерів
    CompositionLocalProvider(
        LocalSpacing provides Spacing(
            extraSmall = 4,
            small = 8,
            medium = 16,
            large = 24,
            extraLarge = 32
        ),
        LocalElevations provides Elevations(
            small = 2,
            medium = 4,
            large = 8
        ),
        LocalShapes provides Shapes(
            small = 8,
            medium = 16,
            large = 24
        ),
        LocalGradients provides Gradients(
            primaryGradient = Brush.horizontalGradient(
                listOf(
                    colorScheme.primary,
                    colorScheme.tertiary
                )
            ),
            secondaryGradient = Brush.horizontalGradient(
                listOf(
                    colorScheme.secondary,
                    colorScheme.tertiary
                )
            ),
            blueGradient = Brush.horizontalGradient(listOf(Color(0xFF1976D2), Color(0xFF64B5F6))),
            purpleGradient = Brush.horizontalGradient(listOf(Color(0xFF9C27B0), Color(0xFFBA68C8))),
            orangeGradient = Brush.horizontalGradient(listOf(Color(0xFFFF9800), Color(0xFFFFB74D)))
        )
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}