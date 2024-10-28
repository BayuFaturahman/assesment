package com.example.assesment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.example.assesment.utils.preference.PreferencesManager


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Manually handle all the insets
        WindowCompat.setDecorFitsSystemWindows(window, false)


        setContent {
            val preferencesManager = PreferencesManager(LocalContext.current)

            val token = preferencesManager.getData(R.string.key_token.toString(),"")

            Box(Modifier.safeDrawingPadding()) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    if (token.isEmpty()) {
                        AssesmentTest(routeStart = "login")
                    } else {
                        AssesmentTest(routeStart = "product_screen")
                    }
                }
            }


        }
    }
}