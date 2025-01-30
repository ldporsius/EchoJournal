package nl.codingwithlinda.echojournal.ui.theme
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color



val primaryDarkMediumContrast = Color(0xFFB7CAFF)
val onPrimaryDarkMediumContrast = Color(0xFF00143B)
val primaryContainerDarkMediumContrast = Color(0xFF7A90C8)
val onPrimaryContainerDarkMediumContrast = Color(0xFF000000)
val secondaryDarkMediumContrast = Color(0xFFB6CAFF)
val onSecondaryDarkMediumContrast = Color(0xFF00143A)
val secondaryContainerDarkMediumContrast = Color(0xFF7990C7)
val onSecondaryContainerDarkMediumContrast = Color(0xFF000000)
val tertiaryDarkMediumContrast = Color(0xFFBCC8FF)
val onTertiaryDarkMediumContrast = Color(0xFF001144)
val tertiaryContainerDarkMediumContrast = Color(0xFF808EC8)
val onTertiaryContainerDarkMediumContrast = Color(0xFF000000)
val errorDarkMediumContrast = Color(0xFFFFB9B8)
val onErrorDarkMediumContrast = Color(0xFF340408)
val errorContainerDarkMediumContrast = Color(0xFFCB7A7B)
val onErrorContainerDarkMediumContrast = Color(0xFF000000)
val backgroundDarkMediumContrast = Color(0xFF121318)
val onBackgroundDarkMediumContrast = Color(0xFFE2E2E9)
val surfaceDarkMediumContrast = Color(0xFF121318)
val onSurfaceDarkMediumContrast = Color(0xFFFCFAFF)
val surfaceVariantDarkMediumContrast = Color(0xFF44464F)
val onSurfaceVariantDarkMediumContrast = Color(0xFFC9CAD4)
val outlineDarkMediumContrast = Color(0xFFA1A2AC)
val outlineVariantDarkMediumContrast = Color(0xFF81838C)
val scrimDarkMediumContrast = Color(0xFF000000)
val inverseSurfaceDarkMediumContrast = Color(0xFFE2E2E9)
val inverseOnSurfaceDarkMediumContrast = Color(0xFF282A2F)
val inversePrimaryDarkMediumContrast = Color(0xFF304679)
val surfaceDimDarkMediumContrast = Color(0xFF121318)
val surfaceBrightDarkMediumContrast = Color(0xFF38393F)
val surfaceContainerLowestDarkMediumContrast = Color(0xFF0C0E13)
val surfaceContainerLowDarkMediumContrast = Color(0xFF1A1B20)
val surfaceContainerDarkMediumContrast = Color(0xFF1E1F25)
val surfaceContainerHighDarkMediumContrast = Color(0xFF282A2F)
val surfaceContainerHighestDarkMediumContrast = Color(0xFF33343A)

//////////////////////////////////////////////////////////

val surfaceVariant = Color(0xFFE1E2EC)
val surfaceTint = Color(0xff475D92)


val primary10 = Color(0xFF001945)
val primary20 = Color(0xff00419c)
val primary30 = Color(0xFF004cb4)
val primary40 = Color(0xFF0057cc)
val primary50 = Color(0xFF1F70F5)
val primary60 = Color(0xFF578cff)
val primary90 = Color(0xFFd9e2ff)
val primary95 = Color(0xFFeef0ff)
val primary100 = Color(0xFFFFFFFF)


val secondary30 = Color(0xFF3b4663)
val secondary50 = Color(0xFF6b7796)
val secondary70 = Color(0xFF9fabcd)
val secondary80 = Color(0xFFbac6e9)
val secondary90 = Color(0xFFd9e2ff)
val secondary95 = Color(0xFFEEF0EF)


val neutralVariant10 = Color(0xFF191A20)
val neutralVariant30 = Color(0xFF404344)
val neutralVariant50 = Color(0xFF6C7085)
val neutralVariant80 = Color(0xFFc1c3ce)
val neutralVariant90 = Color(0xFFe0e1e7)
val neutralVariant99 = Color(0xFFfcfdfe)

val outlineVariant = Color(0xFFC1C3CE)

val error20 = Color(0xFF680014)
val error95 = Color(0xFFffedec)
val error100 = Color(0xFFffffff)

val gray6 = Color(0xFFf2f2f7)

val stressed80 = Color(0xFFDE3A3A)
val sad80 = Color(0xFF3A8EDE)
val neutal80 = Color(0xFF41B278)
val peaceful80 = Color(0xFFBE3294)
val exited80 = Color(0xFFDB6C0B)

val lightButtonColor = Color(0xFF578CFF)
val darkButtonColor = Color(0xFF1F70F5)

val backgroundGradient = Brush.linearGradient(
    colors = listOf(secondary90.copy(.4f), secondary95.copy(.4f)),
    start = Offset(0f, 25f),
    end = Offset(0f, 500f)
)

val backgroundGradientSaturated = Brush.linearGradient(
    colors = listOf(secondary90, secondary95),
    start = Offset(0f, 25f),
    end = Offset(0f, 500f)
)

val buttonGradient = Brush.linearGradient(
    colors = listOf(primary60, primary50),
    start = Offset(0f, 0f),
    end = Offset(0f, 50f)
)

val buttonGradientPressed = Brush.linearGradient(
    colors = listOf(primary60, primary40),
    start = Offset(0f, 25f),
    end = Offset(0f, 150f)
)


val buttonDisabledGradient = Brush.linearGradient(
    colors = listOf(Color.LightGray, Color.Gray),
    start = Offset(0f, 0f),
    end = Offset(0f, 500f)
)

val playbackBackgroundUndefined = Color(0xFFEEF0FF)
val amplitudeColorUndefined = Color(0xFFBAC6E9)
