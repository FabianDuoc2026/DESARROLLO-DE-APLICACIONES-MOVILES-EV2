package com.example.dsy1105_004v2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.dsy1105_004v2026.navigation.AppNavigation
import com.example.dsy1105_004v2026.ui.theme.DSY1105004V2026Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DSY1105004V2026Theme {
                AppNavigation()
            }
        }
    }
}