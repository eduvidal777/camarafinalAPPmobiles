package com.example.huertoavance8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.huertoavance8.ui.navigation.AppNavigation
import com.example.huertoavance8.ui.theme.HuertoAvance8Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HuertoAvance8Theme {
                AppNavigation()
            }
        }
    }
}
