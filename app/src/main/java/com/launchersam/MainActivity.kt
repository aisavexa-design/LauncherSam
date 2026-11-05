package com.launchersam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            var showDrawer by remember { mutableStateOf(false) }
            val apps by produceState(initialValue = emptyList<AppItem>(), context) {
                value = AppRepository.loadApps(context)
            }

            Surface {
                if (showDrawer) {
                    AppDrawer(apps)
                } else {
                    HomeScreen(apps)
                }
            }
        }
    }

    // Swipe up to open drawer can be added with a gesture modifier in Stage 2 polish
}
