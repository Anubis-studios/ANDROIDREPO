package com.creatorsurge.megasystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

enum class Screen {
    DASHBOARD,
    SANDBOX
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CreatorSurgeTheme {
                var currentScreen by remember { mutableStateOf(Screen.DASHBOARD) }
                var currentMode by remember { mutableStateOf(MegasystemMode.ORCHESTRATOR) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF121212)
                ) {
                    when (currentScreen) {
                        Screen.DASHBOARD -> MegasystemDashboard(
                            currentMode = currentMode,
                            onModeSelected = { selected ->
                                currentMode = selected
                                if (selected == MegasystemMode.CREATOR) {
                                    currentScreen = Screen.SANDBOX
                                }
                            },
                            onOpenSandboxClicked = {
                                currentScreen = Screen.SANDBOX
                            }
                        )

                        Screen.SANDBOX -> IsometricSandboxView(
                            onBackToDashboard = {
                                currentScreen = Screen.DASHBOARD
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CreatorSurgeTheme(content: @Composable () -> Unit) {
    val darkColors = darkColorScheme(
        primary = Color(0xFF00E676),
        secondary = Color(0xFF6200EE),
        background = Color(0xFF121212),
        surface = Color(0xFF1E1E1E)
    )

    MaterialTheme(
        colorScheme = darkColors,
        content = content
    )
}
